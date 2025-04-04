package ntnu.idatt2105.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    public ItemDTO(@JsonProperty("itemName") String name, @JsonProperty("description") String description, @JsonProperty("category") String categoryName, @JsonProperty("imageURL") String imageName) {
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
    @JsonProperty("itemName")
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the item.
     * 
     * @return the description of the item
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of the items category.
     * 
     * @return The name of the items category.
     */
    @JsonProperty("category")
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * The filename of the image of the item.
     * 
     * @return the filename of the image of the item.
     */
    @JsonProperty("imageURL")
    public String getImageName() {
        return imageName;
    }
}
