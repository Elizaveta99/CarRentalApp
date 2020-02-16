package Model.Exception;

/**
 * Wrong work with requests to database using DAO
 */
public class DAOException extends Exception {

    /**
     * Constructor without parameters
     */
    public DAOException() {
        super("Unknown DAO exception.");
    }

    /**
     * Constructor with parameter
     * @param message - message with error explanation
     */
    public DAOException(String message) {
        super(message);
    }
}
