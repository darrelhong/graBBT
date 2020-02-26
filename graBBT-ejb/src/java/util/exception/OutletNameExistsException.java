package util.exception;

public class OutletNameExistsException extends Exception {

    /**
     * Creates a new instance of <code>RetailerNotFoundException</code> without
     * detail message.
     */
    public OutletNameExistsException() {
    }

    /**
     * Constructs an instance of <code>RetailerNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public OutletNameExistsException(String msg) {
        super(msg);
    }
}
