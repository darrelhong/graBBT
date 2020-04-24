/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.PromoSessionBeanLocal;
import entity.PromoEntity;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.PromoNotFoundException;

/**
 *
 * @author Chloe Tanny
 */
@Named(value = "viewAllPromosManagedBean")
@ViewScoped
public class ViewAllPromosManagedBean implements Serializable {

    @EJB
    private PromoSessionBeanLocal promoSessionBeanLocal;
    
    private List<PromoEntity> promos;
    
    public ViewAllPromosManagedBean() {
        this.promos = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct()
    {
        List<PromoEntity> retrievedPromos = promoSessionBeanLocal.retrieveAllPromos();
        this.setPromos(retrievedPromos);
    }
    
    public void deactivatePromo(ActionEvent event) throws IOException
    {
        try {
            Long promoId = (Long) event.getComponent().getAttributes().get("promoId");
            promoSessionBeanLocal.deactivatePromo(promoId);
            
            //update promo model
            this.setPromos(promoSessionBeanLocal.retrieveAllPromos());
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Promo (Promo ID: " + promoId + ") successfully deactivated", null));
        } 
        catch (PromoNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured while creating the new promo: " + ex.getMessage(), null));
            System.out.println(ex.getMessage());
        }
    }

    public List<PromoEntity> getPromos() {
        return promos;
    }

    public void setPromos(List<PromoEntity> promos) {
        this.promos = promos;
    }
    
    
}
