/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import javax.ejb.Local;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author Bryan
 */
@Local
public interface CustomerSessionBeanLocal {

    public Customer createNewCustomer(Customer newCustomer) throws UnknownPersistenceException, InputDataValidationException;

    public List<Customer> retrieveAllCustomers();

    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException;

    public Customer retrieveCustomerByPhoneNumber(String phoneNumber) throws CustomerNotFoundException;

    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException;

    public void updateCustomer(Customer customer) throws CustomerNotFoundException, InputDataValidationException, UpdateCustomerException;

    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException;
    
}
