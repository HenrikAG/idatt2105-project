package ntnu.idatt2105.backend.exception;

/**
 * Thrown to indicate that what is being registered collides with something that exists.
 */
public class AlreadyExistsException extends RuntimeException {
    /**
     * Constructs an AlreadyExistsException with the specified message.
     * 
     * @param message the detail message
     */
    public AlreadyExistsException(String message) {
        super(message);
    }
}
