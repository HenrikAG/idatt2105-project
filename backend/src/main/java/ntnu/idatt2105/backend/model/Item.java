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

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "image_name", nullable = true)
    private String imageName;

    /**
     * Empty constructor required for JPA.
     */
    public Item() {
    }

    /**
     * Constructor for Item class.
     * 
     * @param name the name of the item
     * @param category the category of the item
     * @param description a description of the item
     * @param imageName the name of an image of the item
     */
    public Item(String name, Category category, String description, String imageName) {
        this.name = name;
        this.category = category;
        this.description = description;
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
     * Updates the name of the item.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
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
}
