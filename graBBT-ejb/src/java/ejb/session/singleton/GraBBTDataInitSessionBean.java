package ejb.session.singleton;

import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.RetailerSessionBeanLocal;
import entity.Listing;
import entity.RetailerEntity;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
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
    private ListingSessionBeanLocal listingSessionBean;

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
            retailerSessionBean.createNewRetailer(new RetailerEntity("KOI Thé", "manager", "password"));

            Map<String, BigDecimal> sizeOptions = new HashMap<>();
            sizeOptions.put("Medium", new BigDecimal(0.00));
            sizeOptions.put("Large", new BigDecimal(1.10));
            Map<String, BigDecimal> sugarOptions = new HashMap<>();
            sugarOptions.put("0%", new BigDecimal(0.00));
            sugarOptions.put("25%", new BigDecimal(0.00));
            sugarOptions.put("50%", new BigDecimal(0.00));
            sugarOptions.put("100%", new BigDecimal(0.00));
            Map<String, BigDecimal> iceOptions = new HashMap<>();
            iceOptions.put("Normal Ice", new BigDecimal(0.00));
            iceOptions.put("No Ice", new BigDecimal(0.00));
            Map<String, BigDecimal> toppingOptions = new HashMap<>();
            toppingOptions.put("Golden Bubble", new BigDecimal(0.60));
            toppingOptions.put("Konjac Jelly", new BigDecimal(1.20));
            toppingOptions.put("Aloe Vera", new BigDecimal(1.20));

            listingSessionBean.createNewListing(new Listing("Milk Tea", new BigDecimal(3.40), "koimilktea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions));
            listingSessionBean.createNewListing(new Listing("Cacao Barry", new BigDecimal(4.00), "Made with 100% pure Cacao Powder", "koicacaobarry.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions));
            listingSessionBean.createNewListing(new Listing("Black Tea Macchiato", new BigDecimal(3.30), "koiblackteamacchiato.jpeg"));
            listingSessionBean.createNewListing(new Listing("Golden Oolong Tea", new BigDecimal(2.70), "koigoldenoolongtea.jpeg"));

        } catch (InputDataValidationException | RetailerUsernameExistsException | UnknownPersistenceException ex) {
            ex.printStackTrace();
        }
    }

}
