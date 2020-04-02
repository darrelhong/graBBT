/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.Customer;

public class UpdatedCustomerReq {
 
    private Customer updatedCustomer;
    

    public UpdatedCustomerReq() {
    }
    
    public UpdatedCustomerReq(Customer updatedCustomer) {
        this.updatedCustomer = updatedCustomer;
    }

    public Customer getUpdatedCustomer() {
        return updatedCustomer;
    }

    public void setUpdatedCustomer(Customer updatedCustomer) {
        this.updatedCustomer = updatedCustomer;
    }

}
