package ntnu.idatt2105.backend.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    private Set<Item> items = new HashSet<>();

    @ManyToMany(mappedBy = "favoriteCategories")
    private Set<User> interestedUsers = new HashSet<>();

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
    }

    /**
     * Returns the id of the category.
     * 
     * @return the id of the category
     */
    public Long getId() {
        return id;
    }

    /**
     * Returns the name of the cateogory.
     * 
     * @return the name of the category
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the items in the category.
     * 
     * @return the items in the category
     */
    public Set<Item> getItems() {
        return items;
    }
}