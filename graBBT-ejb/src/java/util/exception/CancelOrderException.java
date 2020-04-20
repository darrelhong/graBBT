package util.exception;

/**
 *
 * @author darre
 */
public class CancelOrderException extends Exception {

    /**
     * Creates a new instance of <code>CancelOrderException</code> without
     * detail message.
     */
    public CancelOrderException() {
    }

    /**
     * Constructs an instance of <code>CancelOrderException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CancelOrderException(String msg) {
        super(msg);
    }
}
