package ntnu.idatt2105.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        if (!selectedCategory.isPresent()) {
            throw new IllegalArgumentException("No category with the specified name exists.");
        }

        List<Item> categoryItems = itemRepository.findByCategory(selectedCategory.get());
        return categoryItems;
    }
}
