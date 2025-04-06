package ntnu.idatt2105.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object (DTO) for items.
 */
public class ItemDTO {
    private long id;
    private String name;
    private double price;
    private String description;
    private String categoryName;
    private String imageName;

    /**
     * Constructor for itemDTO.
     * 
     * @param id the id of the item
     * @param name the name of the item
     * @param description a description of the item
     * @param categoryName the name of the items category
     * @param imageName the filename of an image of the item
     */
    public ItemDTO(@JsonProperty("item_id") long id, @JsonProperty("item_name") String name, @JsonProperty("price") double price, @JsonProperty("description") String description,
     @JsonProperty("category") String categoryName, @JsonProperty("imageURL") String imageName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryName = categoryName;
        this.imageName = imageName;
    }

    /**
     * Constructs an ItemDTO with no id.
     * 
     * @param name
     * @param price
     * @param description
     * @param categoryName
     * @param imageName
     */
    public ItemDTO(@JsonProperty("item_name") String name, @JsonProperty("price") double price, @JsonProperty("description") String description,
     @JsonProperty("category") String categoryName, @JsonProperty("imageURL") String imageName) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.categoryName = categoryName;
        this.imageName = imageName;
    }

    /**
     * Returns the id of the item.
     * 
     * @return the id of the item
     */
    @JsonProperty("item_id")
    public long getId() {
        return id;
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
    @JsonProperty("imageURL")
    public String getImageName() {
        return imageName;
    }
}
