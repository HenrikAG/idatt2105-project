package ntnu.idatt2105.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2105.backend.dto.ItemDTO;
import ntnu.idatt2105.backend.dto.ItemRegisterDTO;
import ntnu.idatt2105.backend.exception.NotFoundException;
import ntnu.idatt2105.backend.model.Category;
import ntnu.idatt2105.backend.model.Item;
import ntnu.idatt2105.backend.model.User;
import ntnu.idatt2105.backend.repository.CategoryRepository;
import ntnu.idatt2105.backend.repository.ItemRepository;
import ntnu.idatt2105.backend.repository.UserRepository;

/**
 * Provides methods to handle items.
 */
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    /**
     * Returns all of the registered items mapped to ItemDTOs.
     * 
     * @return List of all registered Items, mapped to ItemDTOs
     */
    public List<ItemDTO> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return items.stream()
            .map(ItemDTO::new)
            .collect(Collectors.toList());        
    }

    /**
     * Returns a list of all the items with the specified category.
     * 
     * @param categoryName the name of the category you want the items of
     * @return List of all items with the specified category. Returns empty list if no items with the specified category exists.
     * @throws IllegalArgumentException if there exists no category with the specified name
     */
    public List<ItemDTO> getItemsByCategoryName(String categoryName) {
        Optional<Category> selectedCategory = categoryRepository.findByName(categoryName);

        if (selectedCategory.isEmpty()) {
            throw new IllegalArgumentException("No category with the specified name exists.");
        }

        List<Item> categoryItems = itemRepository.findByCategory(selectedCategory.get());
        List<ItemDTO> itemDTOs = categoryItems.stream()
            .map(ItemDTO::new)
            .collect(Collectors.toList());
        return itemDTOs;
    }

    /**
     * Registers a new item and returns the registered item.
     * 
     * @param itemRequest an ItemDTO object with the item to be registered
     * @return The registered Item object
     * @throws NotFoundException if there is no category with the name specified by the request object
     */
    public Item registerItem(ItemRegisterDTO itemRequest) {
        Category itemCategory = categoryRepository.findByName(itemRequest.getCategoryName())
            .orElseThrow(() -> new NotFoundException("Category not found. Categoryname: " + itemRequest.getCategoryName()));
        User seller = userRepository.findByUsername(itemRequest.getUsername())
            .orElseThrow(() -> new NotFoundException("User with username: " + itemRequest.getUsername() + " not found."));

        Item newItem = new Item(itemRequest.getName(), itemRequest.getPrice(), itemCategory, itemRequest.getDescription(), itemRequest.getImageUrl(), seller);
        return itemRepository.save(newItem);
    }

    /**
     * Updates the item with the specified id with the non-null values in the itemDTO object.
     * 
     * @param id the id of the item to be updated
     * @param updateRequest an itemDTO with the new values
     * @return the updated item
     * @throws IllegalArgumentException if there is an error with one of the new values. If the category does not exist.
     */
    public Item updateItem(Long id, ItemDTO updateRequest) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Item not found with id: " + id));

        try {
            if (updateRequest.getName() != null) {
                item.setName(updateRequest.getName());
            }

            if (updateRequest.getCategoryName() != null) {
                updateCategory(item, updateRequest.getCategoryName());
            }

            if (updateRequest.getDescription() != null) {
                item.setDescription(updateRequest.getDescription());
            }

            if (updateRequest.getImageName() != null) {
                item.setImageName(updateRequest.getImageName());
            }
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("There was an error updating the item: " + exception.getMessage());
        }

        return item;
    }

    /**
     * Updates the category of an existing item.
     * 
     * @param updateItem the item to be updated
     * @param categoryName the name of the new category
     * @throws IllegalArgumentException if there is no category with the name specified
     */
    private void updateCategory(Item updateItem, String categoryName) {
        Category newCategory = categoryRepository.findByName(categoryName).orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryName));
        updateItem.setCategory(newCategory);
    }

    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("No item found with id: " + id));
        itemRepository.delete(item);
    }
}
