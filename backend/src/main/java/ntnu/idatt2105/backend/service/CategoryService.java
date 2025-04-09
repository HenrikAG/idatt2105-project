package ntnu.idatt2105.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ntnu.idatt2105.backend.dto.CategoryRegisterDTO;
import ntnu.idatt2105.backend.dto.CategoryResponseDTO;
import ntnu.idatt2105.backend.exception.AlreadyExistsException;
import ntnu.idatt2105.backend.exception.NotFoundException;
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

    /**
     * Registers a new catgory.
     * 
     * @param categoryRegisterDTO a DTO with the info of the new category
     * @return DTO with info of the registered item
     * @throws AlreadyExistsException if a category with the same name already exists
     */
    public CategoryResponseDTO addCategory(CategoryRegisterDTO categoryRegisterDTO) {
        if (categoryExists(categoryRegisterDTO.getName())) {
            throw new AlreadyExistsException("Category already exists.");
        }
        Category newCategory = new Category(categoryRegisterDTO.getName());
        return new CategoryResponseDTO(categoryRepository.save(newCategory));
    }

    /**
     * Checks if a category already exists.
     * 
     * @param name the name of the category
     * @return true if a category with the name already exists, false otherwise
     */
    public boolean categoryExists(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    /**
     * Deletes the category with the given name.
     * 
     * @param name the name of the category to be deleted
     * @throws NotFoundException if no category with the specified name exists
     */
    public void deleteCategory(String name) {
        if (!categoryExists(name)) {
            throw new NotFoundException("Category with name: " + name + " not found");
        }

        categoryRepository.deleteByName(name);
    }
}
