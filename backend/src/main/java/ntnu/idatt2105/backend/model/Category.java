package ntnu.idatt2105.backend.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity repereseting the different categories items can be categorized as.
 */
@Entity
@Table(name = "category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<Item> items;

    /**
     * Empty constructor required for JPA.
     */
    public Category() {
    }

    /**
     * Constructor for Category with the category name.
     * 
     * @param name the name of the category
     */
    public Category(String name) {
        this.name = name;
        this.items = new HashSet<>();
    }
}