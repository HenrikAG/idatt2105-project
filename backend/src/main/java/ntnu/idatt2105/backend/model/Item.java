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
     * @param name the name of the item to be sold
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
}
