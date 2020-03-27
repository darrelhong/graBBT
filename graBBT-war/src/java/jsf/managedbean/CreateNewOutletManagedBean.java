/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.OutletSessionBeanLocal;
import entity.OutletEntity;
import entity.RetailerEntity;
import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.InputDataValidationException;
import util.exception.OpeningAndClosingHoursOverlapException;
import util.exception.OutletNameExistsException;
import util.exception.RetailerNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Chloe Tanny
 */
@Named(value = "createNewOutletManagedBean")
@ViewScoped
public class CreateNewOutletManagedBean implements Serializable {

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;
    
    private Long currentRetailerId;
    private OutletEntity newOutletEntity;
    
    public CreateNewOutletManagedBean() {
        newOutletEntity = new OutletEntity();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        RetailerEntity currentRetailer = (RetailerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentRetailerEntity");
        currentRetailerId = currentRetailer.getRetailerId();
        System.out.println("*********************CreateNewOutletManagedBean POST CONSTRUCT********************" + currentRetailerId);
    }
    
    public void createNewOutlet(ActionEvent event)
    {
        try 
        {
            if (newOutletEntity.getOpeningHour() > newOutletEntity.getClosingHour())
            {
                throw new OpeningAndClosingHoursOverlapException("Opening and Closing hours overlap");
            }
            System.out.println("test");
            
            Long newOutletEntityId = outletSessionBeanLocal.createNewOutlet(newOutletEntity, currentRetailerId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New outlet created successfully (Customer ID: " + newOutletEntityId + ")", null));
        }
        catch (OutletNameExistsException | RetailerNotFoundException | UnknownPersistenceException | InputDataValidationException | OpeningAndClosingHoursOverlapException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new outlet: " + ex.getMessage(), null));
            System.out.println(ex.getMessage());
        }
        
        newOutletEntity = new OutletEntity(); //reset
    }

    public Long getCurrentRetailerId() {
        return currentRetailerId;
    }

    public void setCurrentRetailerId(Long currentRetailerId) {
        this.currentRetailerId = currentRetailerId;
    }

    public OutletEntity getNewOutletEntity() {
        return newOutletEntity;
    }

    public void setNewOutletEntity(OutletEntity newOutletEntity) {
        this.newOutletEntity = newOutletEntity;
    }
    
    
}
