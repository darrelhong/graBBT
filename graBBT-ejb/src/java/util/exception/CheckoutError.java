package util.exception;

/**
 *
 * @author darre
 */
public class CheckoutError extends Exception {

    /**
     * Creates a new instance of <code>CheckoutError</code> without detail
     * message.
     */
    public CheckoutError() {
    }

    /**
     * Constructs an instance of <code>CheckoutError</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public CheckoutError(String msg) {
        super(msg);
    }
}
