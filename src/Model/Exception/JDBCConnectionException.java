package Model.Exception;

/**
 * Wrong work with database
 */
public class JDBCConnectionException extends Exception {

    /**
     * Constructor without parameters
     */
    public JDBCConnectionException() {
        super("Unknown database exception.");
    }

    /**
     * Constructor with parameter
     * @param message - message with error explanation
     */
    public JDBCConnectionException(String message) {
        super(message);
    }
}
