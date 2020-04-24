/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.Listing;
import entity.PromoEntity;
import java.util.List;
import java.util.Map;
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
import util.exception.PromoClaimedByCustomerAlreadyException;
import util.exception.PromoNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Chloe Tanny
 */
@Stateless
public class PromoSessionBean implements PromoSessionBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "graBBT-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public PromoSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public PromoEntity createNewPromo(PromoEntity newPromoEntity) throws UnknownPersistenceException, InputDataValidationException 
    {
        Set<ConstraintViolation<PromoEntity>> constraintViolations = validator.validate(newPromoEntity);
        try {
            if (constraintViolations.isEmpty()){
                em.persist(newPromoEntity);
                em.flush();
                return newPromoEntity;
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
            
        } catch (PersistenceException ex){
            throw new UnknownPersistenceException(ex.getMessage());
        }
    }
    
    @Override 
    public List<PromoEntity> retrieveAllActivePromos() 
    {
        //retrieve all available ones only
        Query q = em.createQuery("SELECT p FROM PromoEntity p WHERE p.isActive = TRUE");
        return q.getResultList();
    }
    
    @Override 
    public List<PromoEntity> retrieveAllPromos()
    {
        //retrieve ALL 
        Query q = em.createQuery("SELECT p FROM PromoEntity p");
        return q.getResultList();
    }
    
    @Override
    public PromoEntity retrievePromoById(Long promoId) throws PromoNotFoundException {
        PromoEntity promo = em.find(PromoEntity.class, promoId);
        if (promo != null) {
            return promo;
        } else {
            throw new PromoNotFoundException("Promo with ID " + promoId + " does not exist!");
        }
    }
    
    @Override
    public List<PromoEntity> retrievePromosInCustomerWallet(Long customerId)
    {
       System.out.println("PromoSessionBean: RetrievePromosInCustomerWallet");
       
       Query q = em.createQuery("SELECT p FROM PromoEntity p JOIN p.customerUsedStatus map WHERE KEY(map) = :inCustomerId AND p.isActive = TRUE");
       q.setParameter("inCustomerId", customerId);
       
       return q.getResultList();
    }

    @Override
    public void updatePromo(PromoEntity promo) throws InputDataValidationException, PromoNotFoundException
    {
        if (promo != null && promo.getPromoId() != null){
            Set<ConstraintViolation<PromoEntity>> constraintViolations = validator.validate(promo);
        
            if (constraintViolations.isEmpty())
            {
                PromoEntity promoToUpdate = em.find(PromoEntity.class, promo.getPromoId());
                
                promoToUpdate.setValue(promo.getValue());
                promoToUpdate.setIsActive(promo.isIsActive());
                promoToUpdate.setMaxLimit(promo.getMaxLimit());
                promoToUpdate.setPromoCode(promo.getPromoCode());
                promoToUpdate.setCustomerUsedStatus(promo.getCustomerUsedStatus());
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } else {
            throw new PromoNotFoundException("Promo ID not provided");
        }
    }
    
    //adding promo to wallet event (claimes does not mean its used yet
    @Override
    public void customerClaimsPromo (Long customerId, Long promoId) throws PromoNotFoundException, PromoClaimedByCustomerAlreadyException
    {
        PromoEntity claimedPromo = em.find(PromoEntity.class, promoId);
        Customer claimingCustomer = em.find(Customer.class, customerId);
        
        if (claimedPromo != null && claimingCustomer != null)
        {
            Map<Long, Boolean> hm = claimedPromo.getCustomerUsedStatus();
            
            if (!hm.containsKey(customerId)){
                
                hm.put(customerId, false);
                claimedPromo.setCustomerUsedStatus(hm);
                em.merge(claimedPromo);
                
                //check if no. of claims has hit max limit
                if (claimedPromo.getMaxLimit() == hm.size())
                {
                    //max limit hit
                    this.deactivatePromo(claimedPromo.getPromoId());
                }             
                
            } else {
                throw new PromoClaimedByCustomerAlreadyException("This Promo has been claimed by customer previously.");
            }
            
        } else {
            throw new PromoNotFoundException("Promo ID not provided");
        }
    }
    
    //triggered when limit is hit OR admin manually deactivates
    @Override
    public void deactivatePromo(Long promoId) throws PromoNotFoundException 
    {
        System.out.println("PromoSessionBean: deactivatePromo called");
        
        PromoEntity toDeactivate = em.find(PromoEntity.class, promoId);
        
        toDeactivate.setIsActive(false);
        em.merge(toDeactivate);
        
        System.out.println("Promo deactivated");
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<PromoEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
    
}
