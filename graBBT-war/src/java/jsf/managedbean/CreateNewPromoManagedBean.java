/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PromoSessionBeanLocal;
import entity.PromoEntity;
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
 * @author Chloe Tanny
 */
@Named(value = "createNewPromoManagedBean")
@ViewScoped
public class CreateNewPromoManagedBean implements Serializable {

    @EJB
    private PromoSessionBeanLocal promoSessionBeanLocal;
    
    private PromoEntity newPromo;
    
    public CreateNewPromoManagedBean() {
        newPromo = new PromoEntity();
    }
    
    public void createNewPromo(ActionEvent event) throws UnknownPersistenceException, InputDataValidationException
    {
        try
        {
            newPromo.setIsActive(true);
            PromoEntity createdPromo = promoSessionBeanLocal.createNewPromo(newPromo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New promo created successfully (Promo ID: " + createdPromo.getPromoId() + ", Promo Code: " + createdPromo.getPromoCode() + " )", null));
        } 
        catch (UnknownPersistenceException | InputDataValidationException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured while creating the new promo: " + ex.getMessage(), null));
            System.out.println(ex.getMessage());
        }
        
        newPromo = new PromoEntity();
    }

    public PromoEntity getNewPromo() {
        return newPromo;
    }

    public void setNewPromo(PromoEntity newPromo) {
        this.newPromo = newPromo;
    }
    
    
}
