package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.Listing;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.OutletNotFoundException;

@Named(value = "viewListingsByOutletManagedBean")
@ViewScoped
public class ViewListingsByOutletManagedBean implements Serializable {

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    private Long outletId;
    private List<Listing> outletListings;
    private Listing selectedListing;

    public ViewListingsByOutletManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        this.outletId = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("outletId");
        try {
            setOutletListings(listingSessionBean.retrieveListingsByOutletId(outletId));
        } catch (OutletNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the outlet details: " + ex.getMessage(), null));

        }
    }

    /**
     * @return the outletId
     */
    public Long getOutletId() {
        return outletId;
    }

    /**
     * @param outletId the outletId to set
     */
    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    /**
     * @return the outletListings
     */
    public List<Listing> getOutletListings() {
        return outletListings;
    }

    /**
     * @param outletListings the outletListings to set
     */
    public void setOutletListings(List<Listing> outletListings) {
        this.outletListings = outletListings;
    }

    /**
     * @return the selectedListing
     */
    public Listing getSelectedListing() {
        return selectedListing;
    }

    /**
     * @param selectedListing the selectedListing to set
     */
    public void setSelectedListing(Listing selectedListing) {
        this.selectedListing = selectedListing;
    }

}
