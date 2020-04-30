package util.exception;


public class PromoNoLongerActiveException extends Exception {

    public PromoNoLongerActiveException() {
    }

    public PromoNoLongerActiveException(String msg) {
        super(msg);
    }
}
