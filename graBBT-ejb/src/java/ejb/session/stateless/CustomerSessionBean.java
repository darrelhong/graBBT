/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CustomerNotFoundException;
import util.exception.DeleteCustomerException;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCustomerException;

/**
 *
 * @author Bryan
 */
@Stateless
public class CustomerSessionBean implements CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "graBBT-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public CustomerSessionBean() 
    {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewCustomer(Customer newCustomer) throws UnknownPersistenceException, InputDataValidationException
    {
        try {
            Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(newCustomer);

            if (constraintViolations.isEmpty()) {
                em.persist(newCustomer);
                em.flush();
                return newCustomer.getCustomerId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
    
    @Override
    public List<Customer> retrieveAllCustomers()
    {
        Query query = em.createQuery("SELECT c FROM Customer c ORDER BY c.name ASC");
        List<Customer> customers = query.getResultList();
        
        return customers;
    }
    
    @Override
    public Customer retrieveCustomerById(Long customerId) throws CustomerNotFoundException
    {
        Customer customer = em.find(Customer.class, customerId);
        
        if (customer != null)
        {
            return customer;
        }
        else
        {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist!");
        }
    }
    
    @Override
    public Customer retrieveCustomerByPhoneNumber(String phoneNumber) throws CustomerNotFoundException
    {
        Customer customer = em.find(Customer.class, phoneNumber);
        
        if (customer != null)
        {
            return customer;
        }
        else
        {
            throw new CustomerNotFoundException("Customer with phone number " + phoneNumber + " does not exist!");
        }
    }
    
    @Override
    public Customer retrieveCustomerByUsername(String username) throws CustomerNotFoundException
    {
        Customer customer = em.find(Customer.class, username);
        
        if (customer != null)
        {
            return customer;
        }
        else
        {
            throw new CustomerNotFoundException("Customer with username " + username + " does not exist!");
        }
    }
    
    @Override
    public void updateCustomer(Customer customer) throws CustomerNotFoundException, InputDataValidationException, UpdateCustomerException
    {
        if (customer != null && customer.getCustomerId() != null)
        {
            Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);
            
            if (constraintViolations.isEmpty())
            {
                Customer customerToUpdate = retrieveCustomerById(customer.getCustomerId());
                
                if (customerToUpdate.getUsername().equals(customer.getUsername()))
                {
                    if (customerToUpdate.getPhoneNumber().equals(customer.getPhoneNumber()))
                    {
                        customerToUpdate.setName(customer.getName());
                        customerToUpdate.setUsername(customer.getUsername());
                        customerToUpdate.setPassword(customer.getPassword());
                        customerToUpdate.setPhoneNumber(customer.getPhoneNumber());
                        customerToUpdate.setAddress(customer.getAddress());
                        customerToUpdate.setEmail(customer.getEmail());
                    }
                    else
                    {
                        throw new UpdateCustomerException("Phone number of customer record to be updated does not match the existing record!");
                    }
                }
                else
                {
                    throw new UpdateCustomerException("Username of customer record to be updated does not match the existing record!");
                }
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new CustomerNotFoundException("Customer ID not provided!");
        }
    }
    
    /*
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException, DeleteCustomerException
    {
        Customer customerToRemove = retrieveCustomerById(customerId);
        
        if(customerToRemove.getTransaction().isEmpty())
        {
            em.remove(customerToRemove);
        }
        else
        {
            throw new DeleteCustomerException("Customer ID " + customerId + " is associated with existing transaction(s) and cannot be deleted!");
        }
    }
    */

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Customer>>constraintViolations)
    {
        String msg = "Input data validation error!:";
            
        for(ConstraintViolation constraintViolation:constraintViolations)
        {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }
        
        return msg;
    }
}
