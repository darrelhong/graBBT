/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.OutletEntity;
import entity.RetailerEntity;
import java.util.List;
import java.util.Set;
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
import util.exception.InputDataValidationException;
import util.exception.OutletNameExistsException;
import util.exception.OutletNotFoundException;
import util.exception.RetailerNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Chloe Tanny
 */
@Stateless
public class OutletSessionBean implements OutletSessionBeanLocal {

    @PersistenceContext(unitName = "graBBT-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public OutletSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Override
    public Long createNewOutlet(OutletEntity newOutletEntity, Long retailerId) throws OutletNameExistsException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<OutletEntity>> constraintViolations = validator.validate(newOutletEntity);

            if(retailerId != null)
                {
                    RetailerEntity retailerEntity = em.find(RetailerEntity.class, retailerId);

                    retailerEntity.getOutletEntities().add(newOutletEntity);
                    newOutletEntity.setRetailerEntity(retailerEntity);
                }
                
            if (constraintViolations.isEmpty()) {
                em.persist(newOutletEntity);
                em.flush();

                return newOutletEntity.getOutletId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
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
        Query q = em.createQuery("SELECT o FROM OutletEntity o WHERE o.retailerEntity.retailerId = :inRetailerId");
        q.setParameter("inRetailerId", retailerId);

        return q.getResultList();
        
    }
    
    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<OutletEntity>> constraintViolations) {
        String msg = "Input data validatio for (outlet entity) error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
