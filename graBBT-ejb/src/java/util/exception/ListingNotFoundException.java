package util.exception;

public class ListingNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>ListingNotFoundException</code> without
     * detail message.
     */
    public ListingNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ListingNotFoundException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ListingNotFoundException(String msg) {
        super(msg);
    }
}
