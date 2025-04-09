package ntnu.idatt2105.backend.dto;

/**
 * Data Transfer Object (DTO) for registering categories.
 */
public class CategoryRegisterDTO {
    private final String name;

    /**
     * Constructs a CategoryRegisterDTO
     * 
     * @param name
     */
    public CategoryRegisterDTO(String name) {
        this.name = name;
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
