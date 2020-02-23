package ejb.session.stateless;

import entity.RetailerEntity;
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
import util.exception.InvalidLoginCredentialException;
import util.exception.RetailerNotFoundException;
import util.exception.RetailerUsernameExistsException;
import util.exception.UnknownPersistenceException;
import util.security.CryptographicHelper;

@Stateless
public class RetailerSessionBean implements RetailerSessionBeanLocal {

    @PersistenceContext(unitName = "graBBT-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public RetailerSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public Long createNewRetailer(RetailerEntity newRetailerEntity) throws RetailerUsernameExistsException, UnknownPersistenceException, InputDataValidationException {
        try {
            Set<ConstraintViolation<RetailerEntity>> constraintViolations = validator.validate(newRetailerEntity);

            if (constraintViolations.isEmpty()) {
                em.persist(newRetailerEntity);
                em.flush();

                return newRetailerEntity.getRetailerId();
            } else {
                throw new InputDataValidationException(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
        } catch (PersistenceException ex) {
            if (ex.getCause() != null && ex.getCause().getClass().getName().equals("org.eclipse.persistence.exceptions.DatabaseException")) {
                if (ex.getCause().getCause() != null && ex.getCause().getCause().getClass().getName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                    throw new RetailerUsernameExistsException();
                } else {
                    throw new UnknownPersistenceException(ex.getMessage());
                }
            } else {
                throw new UnknownPersistenceException(ex.getMessage());
            }
        }
    }

    @Override
    public RetailerEntity retailerLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            RetailerEntity retailerEntity = retrieveRetailerByUsername(username);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing((password + retailerEntity.getSalt())));

            if (retailerEntity.getPassword().equals(passwordHash)) {
                return retailerEntity;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (RetailerNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public RetailerEntity retrieveRetailerByUsername(String username) throws RetailerNotFoundException {
        Query q = em.createQuery("SELECT r FROM RetailerEntity r WHERE r.username = :inUsername");
        q.setParameter("inUsername", username);

        try {
            return (RetailerEntity) q.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new RetailerNotFoundException("Error retrieving staff with username: " + username);
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<RetailerEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
