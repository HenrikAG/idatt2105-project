package ntnu.idatt2105.backend.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import ntnu.idatt2105.backend.enums.Role;

/**
 * Entity representing a user.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "seller")
    private Set<Item> listedItems = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "user_favorite_categories",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> favoriteCategories = new HashSet<>();

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Empty constructor required for JPA.
     */
    public User() {
    }

    /**
     * Constructor for User with username and password.
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @param role the role of the user (USER or ADMIN)
     */
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the ID of the user.
     * 
     * @return the ID of the user
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the username of the user.
     * 
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * 
     * @param username the username the user will be assigned
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     * 
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password the password the user will be assigned
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role assigned to the user
     * 
     * @return the users role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     * 
     * @param role the role the user will be assigned
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Returns the users favorite categories.
     * 
     * @return A set containing the user's favorite categories
     */
    public Set<Category> getFavoriteCategories() {
        return favoriteCategories;
    }

    /**
     * Adds a new favorite category.
     * 
     * @param category the new favorite category
     */
    public void addFavoriteCategory(Category category) {
        favoriteCategories.add(category);
    }

    /**
     * Returns the user's listed items.
     * 
     * @return the user's listed items
     */
    public Set<Item> getListedItems() {
        return listedItems;
    }
}
