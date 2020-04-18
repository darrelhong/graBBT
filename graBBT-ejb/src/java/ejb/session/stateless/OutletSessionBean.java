/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Listing;
import entity.OutletEntity;
import entity.RetailerEntity;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import sun.util.calendar.Gregorian;
import util.exception.DeactivateOutletException;
import util.exception.InputDataValidationException;
import util.exception.ListingNotFoundException;
import util.exception.OutletNameExistsException;
import util.exception.OutletNotFoundException;
import util.exception.RetailerNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateOutletException;

/**
 *
 * @author Chloe Tanny
 */
@Stateless
public class OutletSessionBean implements OutletSessionBeanLocal {

    @PersistenceContext(unitName = "graBBT-ejbPU")
    private EntityManager em;

    @EJB
    private ListingSessionBeanLocal listingSessionBeanLocal;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public OutletSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewOutlet(OutletEntity newOutletEntity, Long retailerId) throws OutletNameExistsException, RetailerNotFoundException, UnknownPersistenceException, InputDataValidationException {
        System.out.println("*********** OutletSessionBean.createNewOutlet()**************");
        try {
            Set<ConstraintViolation<OutletEntity>> constraintViolations = validator.validate(newOutletEntity);

            if (retailerId != null) {
                RetailerEntity retailerEntity = em.find(RetailerEntity.class, retailerId);
                if (constraintViolations.isEmpty()) {
                    
                    em.persist(newOutletEntity);
                    
                    //setting bidirectional rs
                    retailerEntity.getOutletEntities().add(newOutletEntity);
                    newOutletEntity.setRetailerEntity(retailerEntity);
                    
                    em.flush();
                    return newOutletEntity.getOutletId();
                } else {
                    throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
                }
            } else {
                throw new RetailerNotFoundException("Retailer with ID " + retailerId + " does not exist!");
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new OutletNameExistsException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public List<OutletEntity> retrieveAllOutletsByRetailerId(Long retailerId) {
        Query q = em.createQuery("SELECT o FROM OutletEntity o WHERE o.retailerEntity.retailerId = :inRetailerId ORDER BY o.isActive");
        q.setParameter("inRetailerId", retailerId);

        return q.getResultList();
    }
    
    @Override
    public List<OutletEntity> retrieveAllOutlets() {
//        Check openging hours
//        Calendar c = GregorianCalendar.getInstance();
//        int currentHour = c.get(Calendar.HOUR_OF_DAY);
//        System.out.println("currentHour: " + currentHour);
//        
//        Query q = em.createQuery("SELECT o FROM OutletEntity o WHERE o.isActive = TRUE AND o.openingHour > :currentHour AND :currentHour < o.closingHour");
//        q.setParameter("currentHour", currentHour);
        Query q = em.createQuery("SELECT o FROM OutletEntity o WHERE o.isActive = TRUE");
    
        return q.getResultList();
    }

    @Override
    public OutletEntity retrieveOutletByOutletId(Long outletId) throws OutletNotFoundException {
        OutletEntity outletEntity = em.find(OutletEntity.class, outletId);
        if (outletEntity != null) {
            return outletEntity;
        } else {
            throw new OutletNotFoundException("Outlet with ID " + outletId + " does not exist!");
        }
    }
    
    @Override
    public void updateOutlet(OutletEntity outletEntity, List<Long> listingIds) throws OutletNotFoundException, InputDataValidationException, UpdateOutletException
    {
        if(outletEntity != null && outletEntity.getOutletId() != null) 
        {
            Set<ConstraintViolation<OutletEntity>> constraintViolations = validator.validate(outletEntity);
            
            if(constraintViolations.isEmpty())
            {
                OutletEntity outletEntityToUpdate = retrieveOutletByOutletId(outletEntity.getOutletId());
                
                if (outletEntityToUpdate.getOutletId().equals(outletEntity.getOutletId()))
                {
                    
                    outletEntityToUpdate.setOutletName(outletEntity.getOutletName());
                    outletEntityToUpdate.setIsActive(outletEntity.getIsActive());
                    outletEntityToUpdate.setOpeningHour(outletEntity.getOpeningHour());
                    outletEntityToUpdate.setClosingHour(outletEntity.getClosingHour());
                    outletEntityToUpdate.setLocationLatitude(outletEntity.getLocationLatitude());
                    outletEntityToUpdate.setLocationLongitude(outletEntity.getLocationLongitude());
                }
                else
                {
                    throw new UpdateOutletException("Outlet ID of the outlet record to be updated does not match the existing record");
                }
            }
            else
            {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        }
        else
        {
            throw new OutletNotFoundException("Outlet ID not provided for outlet to be updated");
        }
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<OutletEntity>> constraintViolations) {
        String msg = "Input data validation for (outlet entity) error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }

    @Override
    public void deactivateOutlet(Long outletId) throws OutletNotFoundException, DeactivateOutletException {
        OutletEntity outletEntityToDeactivate = retrieveOutletByOutletId(outletId);

        List<Listing> listings = listingSessionBeanLocal.retrieveListingsByOutletId(outletId);
        //if no listings left, can delete
        if (listings.isEmpty()) {
            outletEntityToDeactivate.setIsActive(false);
        } else {
            throw new DeactivateOutletException("Outlet ID " + outletId + " is associated with existing listing item(s) and cannot be deleted!");
        }

    }
}
