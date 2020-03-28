package ws.restful.model;

import entity.Customer;

public class CreateCustomerResp {
    
    private Customer customer;

    public CreateCustomerResp() {
    }

    public CreateCustomerResp(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    
    
}
