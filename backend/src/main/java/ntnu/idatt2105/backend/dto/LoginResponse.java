package ntnu.idatt2105.backend.dto;

/**
 * Data Transfer Object (DTO) used to transfer the response of a successful login request.
 */
public class LoginResponse {
    private final String token;

    /**
     * Constructs a LoginResponse with the specified parameters.
     * 
     * @param token the generated auth token
     */
    public LoginResponse(String token) {
        this.token = token;
    }

    /**
     * Returns the token
     * 
     * @return the token
     */
    public String getToken() {
        return token;
    }
}
