package ntnu.idatt2105.backend.dto;

import ntnu.idatt2105.backend.enums.Role;
import ntnu.idatt2105.backend.model.User;

/**
 * Data Transfer Object (DTO) for transfering user info between layers of the application.
 */
public class UserDTO {
    private final String username;
    private final String password;
    private final Role role;

    /**
     * Constructs a UserDTO with the specified user's info.
     * 
     * @param user the user we are transfering the info of
     */
    public UserDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
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
     * Returns the role of the user
     * 
     * @return the role of the user
     */
    public Role getRole() {
        return role;
    }
}
