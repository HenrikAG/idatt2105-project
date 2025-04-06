package ntnu.idatt2105.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2105.backend.model.Category;
import ntnu.idatt2105.backend.repository.CategoryRepository;

/**
 * Provides methods for handling categories.
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Returns all existing categories.
     * 
     * @return List of all existing categories.
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new IllegalArgumentException("This category already exists.");
        }

        return categoryRepository.save(category);
    }
}
