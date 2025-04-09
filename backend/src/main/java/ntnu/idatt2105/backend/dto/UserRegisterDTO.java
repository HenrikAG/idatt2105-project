package ntnu.idatt2105.backend.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) used to register new users.
 */
public class UserRegisterDTO {
    private final String username;
    private final String password;
    private final List<String> favoriteCategories;

    /**
     * Constructs a UserRegisterDTO with the specified parameters.
     * 
     * @param username the username of the new user
     * @param password the password of the new user
     * @param favoriteCategories the user's favorite categories
     */
    public UserRegisterDTO(String username, String password, List<String> favoriteCategories) {
        this.username = username;
        this.password = password;
        this.favoriteCategories = favoriteCategories;
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
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns a list of the names of the user's favorite categories.
     * 
     * @return the user's favorite categories
     */
    public List<String> getFavoriteCategories() {
        return favoriteCategories;
    }
}
