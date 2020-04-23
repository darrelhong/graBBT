package ws.restful.model;

import entity.Customer;
import java.io.Serializable;

/**
 *
 * @author Chloe Tanny
 */
public class RefreshCustomerResp implements Serializable {
    
    private Customer refreshedCustomer;

    public RefreshCustomerResp()
    {
        
    }
    
    public RefreshCustomerResp(Customer refreshedCustomer) {
        this.refreshedCustomer = refreshedCustomer;
    }

    public Customer getRefreshedCustomer() {
        return refreshedCustomer;
    }

    public void setRefreshedCustomer(Customer refreshedCustomer) {
        this.refreshedCustomer = refreshedCustomer;
    }
 
}
