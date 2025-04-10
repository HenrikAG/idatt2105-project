package ntnu.idatt2105.backend.dto;

import ntnu.idatt2105.backend.model.User;

/**
 * Data Transfer Object (DTO) for transfering user info between layers of the application.
 */
public class UserDTO {
    private final String username;

    /**
     * Constructs a UserDTO with the specified user's info.
     * 
     * @param user the user we are transfering the info of
     */
    public UserDTO(User user) {
        this.username = user.getUsername();
    }

    /**
     * Returns the username of the user.
     * 
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }
}
