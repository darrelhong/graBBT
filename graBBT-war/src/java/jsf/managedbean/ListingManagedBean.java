package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.Listing;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "listingManagedBean")
@RequestScoped
public class ListingManagedBean {

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    private List<Listing> listings;
    private Listing selectedListing;
    
    public ListingManagedBean() {}
    
    @PostConstruct
    public void postConstruct() {
        setListings((List<Listing>) listingSessionBean.retrieveAllListings());
    }

    /**
     * @return the listings
     */
    public List<Listing> getListings() {
        return listings;
    }

    /**
     * @param listings the listings to set
     */
    public void setListings(List<Listing> listings) {
        this.listings = listings;
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
