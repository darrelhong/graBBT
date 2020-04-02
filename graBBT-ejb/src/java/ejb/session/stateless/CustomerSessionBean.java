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
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
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
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCustomerException;
import util.security.CryptographicHelper;

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
    public Customer createNewCustomer(Customer newCustomer) throws UnknownPersistenceException, InputDataValidationException
    {
        try {
            Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(newCustomer);

            if (constraintViolations.isEmpty()) {
                em.persist(newCustomer);
                em.flush();
                return newCustomer;
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
        Query q = em.createQuery("SELECT c FROM Customer c WHERE c.username = :inUsername");
        q.setParameter("inUsername", username);
        
        try {
            return (Customer) q.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new CustomerNotFoundException("Customer with username " + username + " not found!");
        }
        
    }    
    @Override
    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException, InputDataValidationException, UpdateCustomerException
    {
        if (customer != null && customer.getCustomerId() != null)
        {
            Customer customerToUpdate = retrieveCustomerById(customer.getCustomerId());

            if (customerToUpdate.getUsername().equals(customer.getUsername()))
            {

                customerToUpdate.setName(customer.getName());
                customerToUpdate.setUsername(customer.getUsername());
                //customerToUpdate.setPassword(customer.getPassword());
                customerToUpdate.setPhoneNumber(customer.getPhoneNumber());
                customerToUpdate.setAddress(customer.getAddress());
                customerToUpdate.setEmail(customer.getEmail());
                customerToUpdate.setBbPoints(customer.getBbPoints());
                
                System.out.println(customerToUpdate.getPhoneNumber());
                return customerToUpdate;
            }
            else
            {
                System.out.println("error2");
                throw new UpdateCustomerException("Username of customer record to be updated does not match the existing record!");
            }
        }
        else
        {
            System.out.println("error4");
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

    @Override
    public Customer customerLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            Customer cust = retrieveCustomerByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + cust.getSalt()));
            
            if (cust.getPassword().equals(passwordHash)) {
                return cust;
            } else {
                throw new InvalidLoginCredentialException("Username or password is incorrect!");
            }
        } catch (CustomerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username name does not exist!");
        }
    }
    
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
