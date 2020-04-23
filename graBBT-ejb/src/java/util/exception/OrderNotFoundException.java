package util.exception;

/**
 *
 * @author darre
 */
public class OrderNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>OrderNotFoundException</code> without
     * detail message.
     */
    public OrderNotFoundException() {
    }

    /**
     * Constructs an instance of <code>OrderNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OrderNotFoundException(String msg) {
        super(msg);
    }
}
