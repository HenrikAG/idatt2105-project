package ntnu.idatt2105.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import ntnu.idatt2105.backend.model.Item;

/**
 * Data Transfer Object (DTO) for items.
 */
public class ItemDTO {
    private String name;
    private double price;
    private String description;
    private String categoryName;
    private String imageName;

    /**
     * Constructs an ItemDTO object.
     * 
     * @param name
     * @param price
     * @param description
     * @param categoryName
     * @param imageName
     */
    public ItemDTO(@JsonProperty("item_name") String name, @JsonProperty("price") double price, @JsonProperty("description") String description,
     @JsonProperty("category") String categoryName, @JsonProperty("image_url") String imageName) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryName = categoryName;
        this.imageName = imageName;
    }

    /**
     * Returns the name of the item.
     * 
     * @return the name of the item
     */
    @JsonProperty("item_name")
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the item.
     * 
     * @return the price of the item
     */
    @JsonProperty("price")
    public double getPrice() {
        return price;
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
    @JsonProperty("image_url")
    public String getImageName() {
        return imageName;
    }
}
