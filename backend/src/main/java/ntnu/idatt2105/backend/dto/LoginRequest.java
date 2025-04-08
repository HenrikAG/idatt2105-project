package ntnu.idatt2105.backend.dto;

/**
 * Data Transfer Object (DTO) used to transfer logininfo.
 */
public class LoginRequest {
    private final String username;
    private final String password;

    /**
     * Constructs a LoginRequest with the specified parameters.
     * 
     * @param username the username of the user logging in
     * @param password the password of the user logging in
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username of the user logging in.
     * 
     * @return the username of the user logging in
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user logging in
     * 
     * @return the password of the user logging in
     */
    public String getPassword() {
        return password;
    }
}
