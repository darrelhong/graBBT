package util.exception;

public class OpeningAndClosingHoursOverlapException extends Exception {

    /**
     * Creates a new instance of <code>UnknownPersistenceException</code>
     * without detail message.
     */
    public OpeningAndClosingHoursOverlapException() {
    }

    /**
     * Constructs an instance of <code>UnknownPersistenceException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public OpeningAndClosingHoursOverlapException(String msg) {
        super(msg);
    }
}
