package ws.restful.model;

import entity.Customer;


public class CreateCustomerReq {

    private Customer newCustomer;

    public CreateCustomerReq() {
    }

    /**
     * @return the newCustomer
     */
    public Customer getNewCustomer() {
        return newCustomer;
    }

    /**
     * @param newCustomer the newCustomer to set
     */
    public void setNewCustomer(Customer newCustomer) {
        this.newCustomer = newCustomer;
    }

}
