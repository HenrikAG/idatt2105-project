package ntnu.idatt2105.backend.dto;

import ntnu.idatt2105.backend.model.Category;

/**
 * DTO for responses with category info.
 */
public class CategoryResponseDTO {
    private final String name;

    /**
     * Constructs a CategoryRegisterDTO.
     * 
     * @param category the category to wrap
     */
    public CategoryResponseDTO(Category category) {
        this.name = category.getName();
    }

    /**
     * Returns the name of the category.
     * 
     * @return the name of the category
     */
    public String getName() {
        return name;
    }
}
