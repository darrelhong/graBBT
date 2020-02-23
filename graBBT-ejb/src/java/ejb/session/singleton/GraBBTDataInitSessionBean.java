package ejb.session.singleton;

import ejb.session.stateless.RetailerSessionBeanLocal;
import entity.RetailerEntity;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.InputDataValidationException;
import util.exception.RetailerNotFoundException;
import util.exception.RetailerUsernameExistsException;
import util.exception.UnknownPersistenceException;

@Singleton
@LocalBean
@Startup
public class GraBBTDataInitSessionBean {

    @EJB
    private RetailerSessionBeanLocal retailerSessionBean;

    @PersistenceContext(unitName = "graBBT-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct() {
        try {
            retailerSessionBean.retrieveRetailerByUsername("manager");
        } catch (RetailerNotFoundException ex) {
            initialiseData();
        }
    }

    private void initialiseData() {
        try {
            System.out.println("test");
            retailerSessionBean.createNewRetailer(new RetailerEntity("KOI Thé", "manager", "password"));

        } catch (InputDataValidationException | RetailerUsernameExistsException | UnknownPersistenceException ex) {
            ex.printStackTrace();
        }
    }

}
