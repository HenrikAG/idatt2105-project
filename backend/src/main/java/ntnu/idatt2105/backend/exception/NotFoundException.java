package ntnu.idatt2105.backend.exception;

/**
 * Thrown to indicate that nothing was found given the specified info.
 */
public class NotFoundException extends RuntimeException {
    /**
     * Constructs a NotFoundException with the specified detail message.
     * 
     * @param message the detail message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
