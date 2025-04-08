package ntnu.idatt2105.backend.dto;

import ntnu.idatt2105.backend.enums.Role;

/**
 * Data Transfer Object (DTO) used to register new users.
 */
public class UserRegisterDTO {
    private final String username;
    private final String password;

    /**
     * Constructs a UserRegisterDTO with the specified parameters.
     * 
     * @param username the username of the new user
     * @param password the password of the new user
     */
    public UserRegisterDTO(String username, String password) {
        this.username = username;
        this.password = password;
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
}
