package util.exception;

public class RetailerNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>RetailerNotFoundException</code> without
     * detail message.
     */
    public RetailerNotFoundException() {
    }

    /**
     * Constructs an instance of <code>RetailerNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RetailerNotFoundException(String msg) {
        super(msg);
    }
}
