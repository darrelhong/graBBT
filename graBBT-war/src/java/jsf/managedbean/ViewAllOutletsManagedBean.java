/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.OutletSessionBeanLocal;
import entity.OutletEntity;
import entity.RetailerEntity;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.DeactivateOutletException;
import util.exception.OutletNotFoundException;

/**
 *
 * @author Chloe Tanny
 */
@Named(value = "viewAllOutletsManagedBean")
@ViewScoped

public class ViewAllOutletsManagedBean implements Serializable {

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;
    private List<OutletEntity> outletEntities;

    private Long currentRetailerId;

    public ViewAllOutletsManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {

        RetailerEntity currentRetailer = (RetailerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentRetailerEntity");
        currentRetailerId = currentRetailer.getRetailerId();
        System.out.println("*********************POST CONSTRUCT********************" + currentRetailerId);

        //populate local list of outlets
        setOutletEntities(outletSessionBeanLocal.retrieveAllOutletsByRetailerId(currentRetailerId)); //can be empty

    }

    public void viewOutletDetails(ActionEvent event) throws IOException {
        Long outletIdToView = (Long) event.getComponent().getAttributes().get("outletId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("outletIdToView", outletIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewOutletDetails.xhtml");
    }

    public void viewOutletListings(ActionEvent event) throws IOException {
        Long outletId = (Long) event.getComponent().getAttributes().get("outletId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("outletId", outletId);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewListingsByOutlet.xhtml");
    }

    public void viewOutletOrders(ActionEvent event) throws IOException {
        Long outletId = (Long) event.getComponent().getAttributes().get("outletId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("outletId", outletId);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewOutletOrders.xhtml");
    }

    public void deleteOutlet(ActionEvent event) throws IOException {
        Long outletId = (Long) event.getComponent().getAttributes().get("outletId");
        try {
            outletSessionBeanLocal.deactivateOutlet(outletId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Outlet deactivated successfully", ""));

        } catch (DeactivateOutletException | OutletNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
        }
    }

    /**
     * @return the outletEntities
     */
    public List<OutletEntity> getOutletEntities() {
        return outletEntities;
    }

    /**
     * @param outletEntities the outletEntities to set
     */
    public void setOutletEntities(List<OutletEntity> outletEntities) {
        this.outletEntities = outletEntities;
    }
}
