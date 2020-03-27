/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Bryan
 */
@Named(value = "createNewCustomerManagedBean")
@ViewScoped
public class CreateNewCustomerManagedBean implements Serializable {

    @EJB(name = "CustomerSessionBeanLocal")
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    private Customer newCustomer;
    /**
     * Creates a new instance of CreateNewCustomerManagedBean
     */
    public CreateNewCustomerManagedBean() {
        newCustomer = new Customer();
    }
    
    public void createNewCustomer(ActionEvent event) throws UnknownPersistenceException, InputDataValidationException
    {
        try
        {
           Long newCustomerId = customerSessionBeanLocal.createNewCustomer(newCustomer);
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New customer created successfully (Outlet ID: " + newCustomerId + " )", null));
     
        }
        
        catch (UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured while creating the new customer: " + ex.getMessage(), null));
            System.out.println(ex.getMessage());
        }
        
        newCustomer = new Customer(); //reset
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
