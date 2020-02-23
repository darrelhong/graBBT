package ejb.session.stateless;

import entity.RetailerEntity;
import javax.ejb.Local;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.RetailerNotFoundException;
import util.exception.RetailerUsernameExistsException;
import util.exception.UnknownPersistenceException;

@Local
public interface RetailerSessionBeanLocal {

    public RetailerEntity retailerLogin(String username, String password) throws InvalidLoginCredentialException;

    public RetailerEntity retrieveRetailerByUsername(String username) throws RetailerNotFoundException;

    public Long createNewRetailer(RetailerEntity newRetailerEntity) throws RetailerUsernameExistsException, UnknownPersistenceException, InputDataValidationException;
    
}
