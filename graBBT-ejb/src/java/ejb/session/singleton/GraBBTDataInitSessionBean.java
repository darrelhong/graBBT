package ejb.session.singleton;

import ejb.session.stateless.OutletSessionBeanLocal;
import ejb.session.stateless.RetailerSessionBeanLocal;
import entity.OutletEntity;
import entity.RetailerEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.InputDataValidationException;
import util.exception.OutletNameExistsException;
import util.exception.RetailerNotFoundException;
import util.exception.RetailerUsernameExistsException;
import util.exception.UnknownPersistenceException;

@Singleton
@LocalBean
@Startup
public class GraBBTDataInitSessionBean {

    @EJB
    private RetailerSessionBeanLocal retailerSessionBean;
    @EJB
    private OutletSessionBeanLocal outletSessionBean;

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
            Long retailerId = retailerSessionBean.createNewRetailer(new RetailerEntity("KOI Thé", "manager", "password"));
            outletSessionBean.createNewOutlet(new OutletEntity("KOI Paya Lebar", 9, 20, 1.3178, 103.8924), retailerId);
            outletSessionBean.createNewOutlet(new OutletEntity("KOI Jurong", 9, 20, 1.3329, 103.7436), retailerId);

        } catch (InputDataValidationException | RetailerUsernameExistsException | UnknownPersistenceException | OutletNameExistsException ex) {
            ex.printStackTrace();
        }
    }
}
