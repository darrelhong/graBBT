package ejb.session.singleton;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import entity.Listing;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import ejb.session.stateless.OutletSessionBeanLocal;
import ejb.session.stateless.RetailerSessionBeanLocal;
import entity.Customer;
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
import util.exception.OutletNotFoundException;
import util.exception.RetailerNotFoundException;
import util.exception.RetailerUsernameExistsException;
import util.exception.UnknownPersistenceException;

@Singleton
@LocalBean
@Startup
public class GraBBTDataInitSessionBean {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;
    @EJB
    private ListingSessionBeanLocal listingSessionBean;
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
            Long retailerId = retailerSessionBean.createNewRetailer(new RetailerEntity("KOI Thé", "manager", "password"));
            Long koiPayaOutletId = outletSessionBean.createNewOutlet(new OutletEntity("KOI Paya Lebar", 9, 20, 1.3178, 103.8924), retailerId);
            Long koiJurongOutletId = outletSessionBean.createNewOutlet(new OutletEntity("KOI Jurong", 9, 20, 1.3329, 103.7436), retailerId);

            // Creating options hashmaps
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

            //just creating some BBTs and associating them with outlets
            OutletEntity koiPaya = outletSessionBean.retrieveOutletByOutletId(koiPayaOutletId);
            OutletEntity koiJurong = outletSessionBean.retrieveOutletByOutletId(koiJurongOutletId);
            Listing koiMilkTea = new Listing("Milk Tea", new BigDecimal(3.40), koiPaya, "koimilktea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiMilkTea);
            koiPaya.getListings().add(koiMilkTea);
            Listing koiCacaoBarry = new Listing("Cacao Barry", new BigDecimal(4.00), koiPaya, "Made with 100% pure Cacao Powder", "koicacaobarry.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiCacaoBarry);
            koiPaya.getListings().add(koiCacaoBarry);
            Listing koiBlackTeaMacchiato = new Listing("Black Tea Macchiato", new BigDecimal(3.30), koiJurong, "koiblackteamacchiato.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiBlackTeaMacchiato);
            koiJurong.getListings().add(koiBlackTeaMacchiato);
            Listing koiGoldenOolong = new Listing("Golden Oolong Tea", new BigDecimal(2.70), koiJurong, "koigoldenoolongtea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiGoldenOolong);
            koiJurong.getListings().add(koiGoldenOolong);

            //customer side of things
            Customer customer = new Customer("Customer 1", "customer", "password", "87654321", "address", "qwerty@gmail.com");
            Long customerId = customerSessionBeanLocal.createNewCustomer(customer);
            
        } catch (InputDataValidationException | RetailerUsernameExistsException
                | UnknownPersistenceException | OutletNameExistsException
                | RetailerNotFoundException | OutletNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
