package ejb.session.stateless;

import entity.Listing;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.InputDataValidationException;
import util.exception.ListingNotFoundException;
import util.exception.UnknownPersistenceException;

@Stateless
public class ListingSessionBean implements ListingSessionBeanLocal {

    @PersistenceContext(unitName = "graBBT-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public ListingSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewListing(Listing newListing) throws UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<Listing>> constraintViolations = validator.validate(newListing);

            if (constraintViolations.isEmpty()) {
                em.persist(newListing);
                em.flush();
                return newListing.getListingId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
    
    //for testing
    @Override
    public List retrieveAllListings() {
        Query q = em.createQuery("SELECT l from Listing l");
        return q.getResultList();
    }
    
    @Override
    public Listing retrieveListingById(Long listingId) throws ListingNotFoundException {
        Listing listing = em.find(Listing.class, listingId);
        
        if (listing != null) {
            return listing;
        } else {
            throw new ListingNotFoundException("Listing with ID " + listingId + " does not exist!");
        }
    }
    
    @Override
    public List retrieveListingByOutlet() {
        throw new UnsupportedOperationException();
    }
    
    public void updateListing(Listing listing) throws InputDataValidationException, ListingNotFoundException {
        if (listing != null && listing.getListingId() != null) {
            Set<ConstraintViolation<Listing>> constraintViolations = validator.validate(listing);
            if (constraintViolations.isEmpty()) {
                Listing listingToUpdate = retrieveListingById(listing.getListingId());
                
                listingToUpdate.setBasePrice(listing.getBasePrice());
                listingToUpdate.setDescription(listing.getDescription());
                listingToUpdate.setImageSrc(listing.getImageSrc());
                listingToUpdate.setName(listing.getName());
                listingToUpdate.setIceOptions(listing.getIceOptions());
                listingToUpdate.setSizeOptions(listing.getSizeOptions());
                listingToUpdate.setSugarOptions(listing.getSugarOptions());
                listingToUpdate.setToppingOptions(listing.getToppingOptions());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new ListingNotFoundException("Lisitng ID not provided!");
        }
    }
    
    @Override
    public void deleteListing(Long listingId) throws ListingNotFoundException {
        Listing listingToDelete = retrieveListingById(listingId);
        
        em.remove(listingToDelete);
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<Listing>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
