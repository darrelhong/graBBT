package util.exception;

/**
 *
 * @author darre
 */
public class GiveOutletRatingException extends Exception {

    /**
     * Creates a new instance of <code>GiveOutletRatingException</code> without
     * detail message.
     */
    public GiveOutletRatingException() {
    }

    /**
     * Constructs an instance of <code>GiveOutletRatingException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public GiveOutletRatingException(String msg) {
        super(msg);
    }
}
