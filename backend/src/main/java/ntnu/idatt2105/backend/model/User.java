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
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
     * Returns the password of the user.
     * 
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }


}
