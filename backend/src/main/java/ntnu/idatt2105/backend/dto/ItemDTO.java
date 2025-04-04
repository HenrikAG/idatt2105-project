package ntnu.idatt2105.backend.dto;

/**
 * Data Transfer Object (DTO) for items.
 */
public class ItemDTO {
    private String name;
    private String description;
    private String categoryName;
    private String imageName;

    /**
     * Constructor for itemDTO.
     * 
     * @param name the name of the item
     * @param description a description of the item
     * @param categoryName the name of the items category
     * @param imageName the filename of an image of the item
     */
    public ItemDTO(String name, String description, String categoryName, String imageName) {
        this.name = name;
        this.description = description;
        this.categoryName = categoryName;
        this.imageName = imageName;
    }

    /**
     * Returns the name of the item.
     * 
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the item.
     * 
     * @return the description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of the items category.
     * 
     * @return The name of the items category.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * The filename of the image of the item.
     * 
     * @return the filename of the image of the item.
     */
    public String getImageName() {
        return imageName;
    }
}
