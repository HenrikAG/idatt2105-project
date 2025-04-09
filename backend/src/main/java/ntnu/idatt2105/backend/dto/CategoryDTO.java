package ntnu.idatt2105.backend.dto;

import ntnu.idatt2105.backend.model.Category;

/**
 * DTO for transfering categories.
 */
public class CategoryDTO {
    private final Long id;
    private final String name;

    /**
     * Maps a category to a CategoryDTO.
     * 
     * @param category the category that is mapped.
     */
    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

    /**
     * Returns the id of the transfered category.
     * 
     * @return the id of the category
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the name of the transfered category.
     * 
     * @return the name of the cateogry
     */
    public String getName() {
        return name;
    }
}
