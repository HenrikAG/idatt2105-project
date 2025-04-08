package ntnu.idatt2105.backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ntnu.idatt2105.backend.dto.CategoryRegisterDTO;
import ntnu.idatt2105.backend.exception.AlreadyExistsException;
import ntnu.idatt2105.backend.model.Category;
import ntnu.idatt2105.backend.service.CategoryService;

/**
 * Handles HTTP requests related to categories.
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Retrieves all categories.
     * 
     * @return A ResponseEntity containing a list og Category objects and the HTTP status code.
    */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        logger.info("A user is trying to get all existing categories.");
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    /**
     * Registers a new category.
     * 
     * @param registerDTO DTO with the name of the new category.
     * @return a ResponseEntity with a String describing the result, and a status code.
     */
    @PostMapping
    public ResponseEntity<String> registerCategory(@RequestBody CategoryRegisterDTO registerDTO) {
        logger.info("Trying to register new categry with name: " + registerDTO.getName());

        try {
            categoryService.addCategory(registerDTO);
            return new ResponseEntity<>("Successfully registered category", HttpStatus.CREATED);
        } catch (AlreadyExistsException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
