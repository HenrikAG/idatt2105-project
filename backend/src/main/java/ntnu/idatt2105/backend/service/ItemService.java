package ntnu.idatt2105.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2105.backend.dto.ItemDTO;
import ntnu.idatt2105.backend.model.Category;
import ntnu.idatt2105.backend.model.Item;
import ntnu.idatt2105.backend.repository.CategoryRepository;
import ntnu.idatt2105.backend.repository.ItemRepository;

/**
 * Provides methods to handle items.
 */
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Returns a list of all the items with the specified category.
     * 
     * @param categoryName the name of the category you want the items of
     * @return List of all items with the specified category. Returns empty list if no items with the specified category exists.
     * @throws IllegalArgumentException if there exists no category with the specified name
     */
    public List<Item> getItemsByCategoryName(String categoryName) {
        Optional<Category> selectedCategory = categoryRepository.findByName(categoryName);

        if (selectedCategory.isEmpty()) {
            throw new IllegalArgumentException("No category with the specified name exists.");
        }

        List<Item> categoryItems = itemRepository.findByCategory(selectedCategory.get());
        return categoryItems;
    }

    /**
     * Registers a new item and returns the registered item.
     * 
     * @param itemRequest an ItemDTO object with the item to be registered
     * @return The registered Item object
     * @throws IllegalArgumentException if there is no category with the name specified by the request object
     */
    public Item registerItem(ItemDTO itemRequest) {
        Optional<Category> itemCategory = categoryRepository.findByName(itemRequest.getCategoryName());

        if (itemCategory.isEmpty()) {
            throw new IllegalArgumentException("Category doesent exist: " + itemRequest.getCategoryName());
        }

        Item newItem = new Item(itemRequest.getName(), itemCategory.get(), itemRequest.getDescription(), itemRequest.getImageName());
        return itemRepository.save(newItem);
    }
}
