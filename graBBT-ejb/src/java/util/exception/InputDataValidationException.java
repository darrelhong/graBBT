package util.exception;

public class InputDataValidationException extends Exception {

    /**
     * Creates a new instance of <code>InputDataValidationException</code>
     * without detail message.
     */
    public InputDataValidationException() {
    }

    /**
     * Constructs an instance of <code>InputDataValidationException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public InputDataValidationException(String msg) {
        super(msg);
    }
}
