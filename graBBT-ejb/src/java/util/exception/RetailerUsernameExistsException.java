package util.exception;

public class RetailerUsernameExistsException extends Exception {

    /**
     * Creates a new instance of <code>RetailerUsernameExistsException</code>
     * without detail message.
     */
    public RetailerUsernameExistsException() {
    }

    /**
     * Constructs an instance of <code>RetailerUsernameExistsException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RetailerUsernameExistsException(String msg) {
        super(msg);
    }
}
