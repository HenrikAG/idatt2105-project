package ntnu.idatt2105.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object (DTO) for registering new items.
 */
public class ItemRegisterDTO {
    private long userId;
    private String name;
    private double price;
    private String description;
    private String categoryName;
    private String imageUrl;

    /**
     * Constructs a ItemRegisterDTO with the specified parameters.
     * 
     * @param userId the id of the user registering the item
     * @param name the name of the item
     * @param price the price of the item
     * @param description a description of the item
     * @param categoryName the name of the category of the item
     * @param imageUrl the url of the image of the item
     */
    public ItemRegisterDTO(@JsonProperty("user_id") long userId, @JsonProperty("item_name") String name,
    @JsonProperty("price") double price, @JsonProperty("description") String description,
    @JsonProperty("category") String categoryName, @JsonProperty("image_url") String imageUrl) {
        this.userId = userId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryName = categoryName;
        this.imageUrl = imageUrl;
    }

    /**
     * Returns the id of the user selling the item.
     * 
     * @return the id of the user selling the item
     */
    public long getUserId() {
        return userId;
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
     * Returns the price of the item.
     * 
     * @return the price of the item
     */
    public double getPrice() {
        return price;
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
     * Returns the name of the category of the item.
     * 
     * @return the name of the category of the item
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Returns the URL of the image of the item.
     * 
     * @return the URL of the image of the item
     */
    public String getImageUrl() {
        return imageUrl;
    }
}
