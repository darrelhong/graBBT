package ws.restful.model;

import entity.Customer;

public class CustomerLoginResp {
      private Customer customer;

    public CustomerLoginResp() {
    }

    public CustomerLoginResp(Customer customer) {
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
