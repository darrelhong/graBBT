package util.exception;

public class OutletNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>RetailerNotFoundException</code> without
     * detail message.
     */
    public OutletNotFoundException() {
    }

    /**
     * Constructs an instance of <code>RetailerNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OutletNotFoundException(String msg) {
        super(msg);
    }
}
