package util.exception;

public class DeactivateOutletException extends Exception {

    /**
     * Creates a new instance of <code>UnknownPersistenceException</code>
     * without detail message.
     */
    public DeactivateOutletException() {
    }

    /**
     * Constructs an instance of <code>UnknownPersistenceException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DeactivateOutletException(String msg) {
        super(msg);
    }
}
