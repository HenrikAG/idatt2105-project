package ntnu.idatt2105.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entity representing an item to be sold.
 */
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "image_name", nullable = true)
    private String imageName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User seller;

    /**
     * Empty constructor required for JPA.
     */
    public Item() {
    }

    /**
     * Constructor for Item class.
     * 
     * @param name the name of the item
     * @param price the price the item is being listed for
     * @param category the category of the item
     * @param description a description of the item
     * @param imageName the name of an image of the item
     * @param seller the user selling the item
     */
    public Item(String name, double price, Category category, String description, String imageName, User seller) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.imageName = imageName;
        this.seller = seller;
    }

    /**
     * Returns the id of the item
     * 
     * @return the id of the item
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the id of the item
     * 
     * @return the id of the item
     */
    public long getId() {
        return id;
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
     * Updates the name of the item.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of the item.
     * 
     * @return the price of the item.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Updates the price of the item.
     * 
     * @param price the new price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the category of the item.
     * 
     * @return the category of the item
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Updates the category of the item.
     * 
     * @param category the new category.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Returns the description of the item.
     * 
     * @return the description of the item.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description of the item.
     * 
     * @param description the new description.,
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the name of the imagefile of the item.
     * 
     * @return the name of an image of the item.
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * Updates the url of the image of the item.
     * 
     * @param imageName the new image URL
     */
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    /**
     * Returns the user selling the item.
     * 
     * @return the user selling the item.
     */
    public User getSeller() {
        return seller;
    }
}
