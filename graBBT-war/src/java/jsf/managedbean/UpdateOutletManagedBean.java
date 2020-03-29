/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OutletSessionBeanLocal;
import entity.Listing;
import entity.OutletEntity;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import util.exception.OutletNotFoundException;

/**
 *
 * @author Bryan
 */
@Named(value = "updateOutletManagedBean")
@ViewScoped

public class UpdateOutletManagedBean implements Serializable {

    @EJB(name = "ListingSessionBeanLocal")
    private ListingSessionBeanLocal listingSessionBeanLocal;

    @EJB(name = "OutletSessionBeanLocal")
    private OutletSessionBeanLocal outletSessionBeanLocal;

    
    private Long currentOutletId;
    private OutletEntity outletEntityToUpdate;
    private List<Long> listingIds;
    private List<Listing> listingEntities;
    
    /**
     * Creates a new instance of UpdateOutletManagedBean
     */
    public UpdateOutletManagedBean() 
    {
        
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setCurrentOutletId((Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("currentOutletId"));
        
        try
        {
            setOutletEntityToUpdate(outletSessionBeanLocal.retrieveOutletByOutletId(getCurrentOutletId()));
            setListingIds(new ArrayList<>());
            
            for(Listing listing : getOutletEntityToUpdate().getListings())
            {
                getListingIds().add(listing.getListingId());
            }
            
            setListingEntities((List<Listing>) listingSessionBeanLocal.retrieveAllListings());
        }
        catch (OutletNotFoundException ex) 
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the outlet details: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void updateOutlet()
    {
        try
        {
            outletSessionBeanLocal.updateOutlet(getOutletEntityToUpdate(), getListingIds());
        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Outlet updated successfully", null));
        }
        catch(OutletNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating outlet: " + ex.getMessage(), null));
        }
        catch(Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
        
    }
    
    public void back(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("currentOutletId", outletEntityToUpdate);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewOutletDetails.xhtml");
    }

    /**
     * @return the currentOutletId
     */
    public Long getCurrentOutletId() {
        return currentOutletId;
    }

    /**
     * @param currentOutletId the currentOutletId to set
     */
    public void setCurrentOutletId(Long currentOutletId) {
        this.currentOutletId = currentOutletId;
    }

    /**
     * @return the outletEntityToUpdate
     */
    public OutletEntity getOutletEntityToUpdate() {
        return outletEntityToUpdate;
    }

    /**
     * @param outletEntityToUpdate the outletEntityToUpdate to set
     */
    public void setOutletEntityToUpdate(OutletEntity outletEntityToUpdate) {
        this.outletEntityToUpdate = outletEntityToUpdate;
    }

    /**
     * @return the listingIds
     */
    public List<Long> getListingIds() {
        return listingIds;
    }

    /**
     * @param listingIds the listingIds to set
     */
    public void setListingIds(List<Long> listingIds) {
        this.listingIds = listingIds;
    }

    /**
     * @return the listingEntities
     */
    public List<Listing> getListingEntities() {
        return listingEntities;
    }

    /**
     * @param listingEntities the listingEntities to set
     */
    public void setListingEntities(List<Listing> listingEntities) {
        this.listingEntities = listingEntities;
    }
    
}
