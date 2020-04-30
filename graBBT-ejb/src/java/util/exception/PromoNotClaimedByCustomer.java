package util.exception;


public class PromoNotClaimedByCustomer extends Exception {

    public PromoNotClaimedByCustomer() {
    }

    public PromoNotClaimedByCustomer(String msg) {
        super(msg);
    }
}
