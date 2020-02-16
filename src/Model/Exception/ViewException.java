package Model.Exception;

/**
 * Errors in view
 */
public class ViewException extends Exception {

    /**
     * Constructor without parameters
     */
    public ViewException() {
        super("Unknown view exception.");
    }

    /**
     * Constructor with parameter
     * @param message - message with error explanation
     */
    public ViewException(String message) {
        super(message);
    }
}
