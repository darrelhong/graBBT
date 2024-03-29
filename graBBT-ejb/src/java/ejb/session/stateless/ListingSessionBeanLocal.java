package ejb.session.stateless;

import entity.Listing;
import java.util.List;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.ListingNotFoundException;
import util.exception.OutletNotFoundException;
import util.exception.UnknownPersistenceException;

@Local
public interface ListingSessionBeanLocal {

    public Listing createNewListing(Listing newListing) throws UnknownPersistenceException, InputDataValidationException;

    public List retrieveAllListings();

    public Listing retrieveListingById(Long listingId) throws ListingNotFoundException;

    public List retrieveListingsByOutletId(Long outletId) throws OutletNotFoundException;

    public Listing createNewListing(Listing newListing, Long outletId) throws UnknownPersistenceException, InputDataValidationException;

    public String deleteListing(Long listingId) throws ListingNotFoundException;

}
