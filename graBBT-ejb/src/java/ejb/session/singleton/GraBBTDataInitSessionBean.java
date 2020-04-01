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
            //hgjgjh
            Long retailerId = retailerSessionBean.createNewRetailer(new RetailerEntity("KOI Thé", "manager", "password"));
            Long koiPayaOutletId = outletSessionBean.createNewOutlet(new OutletEntity("KOI Paya Lebar", 9, 20, 1.3178, 103.8924, "koi.jpeg"), retailerId);
            Long koiJurongOutletId = outletSessionBean.createNewOutlet(new OutletEntity("KOI Jurong", 9, 20, 1.3329, 103.7436, "koi.jpeg"), retailerId);

            Long gongChaRetailerId = retailerSessionBean.createNewRetailer(new RetailerEntity("Gong Cha", "manager2", "password"));
            Long gongChaNexOutletId = outletSessionBean.createNewOutlet(new OutletEntity("Gong Cha NEX", 10, 21, 1.350690, 103.872293, "gongcha.jpeg"), gongChaRetailerId);
            Long gongChaTpyOutletId = outletSessionBean.createNewOutlet(new OutletEntity("Gong Cha Toa Payoh", 10, 21, 1.332910, 103.84819, "gongcha.jpeg"), gongChaRetailerId);

            Long lihoRetailerId = retailerSessionBean.createNewRetailer(new RetailerEntity("LiHO Tea", "manager3", "password"));
            Long lihoStarVistaOutletId = outletSessionBean.createNewOutlet(new OutletEntity("LiHO Star Vista", 8, 20, 1.306803, 103.788484, "liho.jpeg"), lihoRetailerId);
            Long lihoOrchardGatewayOutletId = outletSessionBean.createNewOutlet(new OutletEntity("LiHO Orchard Gateway", 8, 20, 1.300763, 103.839079, "liho.jpeg"), lihoRetailerId);

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

            // for gong cha
            Map<String, BigDecimal> gcSizeOptions = new HashMap<>();
            gcSizeOptions.put("Medium", new BigDecimal(0.00));
            gcSizeOptions.put("Large", new BigDecimal(1.10));
            Map<String, BigDecimal> gcSugarOptions = new HashMap<>();
            gcSugarOptions.put("0% Sugar free", new BigDecimal(0.00));
            gcSugarOptions.put("30% Little sugar", new BigDecimal(0.00));
            gcSugarOptions.put("50% Half sugar", new BigDecimal(0.00));
            gcSugarOptions.put("70% Less sugar", new BigDecimal(0.00));
            gcSugarOptions.put("100% Full sugar", new BigDecimal(0.00));
            Map<String, BigDecimal> gcIceOptions = new HashMap<>();
            gcIceOptions.put("Normal Ice", new BigDecimal(0.00));
            gcIceOptions.put("No Ice", new BigDecimal(0.00));
            Map<String, BigDecimal> gcToppingOptions = new HashMap<>();
            gcToppingOptions.put("Herbal Jelly", new BigDecimal(0.70));
            gcToppingOptions.put("Pudding Jelly", new BigDecimal(0.70));
            gcToppingOptions.put("Pearl", new BigDecimal(0.40));
            gcToppingOptions.put("White Pearl", new BigDecimal(0.70));
            gcToppingOptions.put("Coconut Jelly", new BigDecimal(0.50));
            gcToppingOptions.put("Red Bean", new BigDecimal(0.70));
            gcToppingOptions.put("Milk Foam (Original)", new BigDecimal(0.80));
            gcToppingOptions.put("Aloe Vera", new BigDecimal(0.70));
            gcToppingOptions.put("Rainbow Jelly", new BigDecimal(0.60));
            gcToppingOptions.put("Basil Seeds", new BigDecimal(0.40));

            OutletEntity gongChaNex = outletSessionBean.retrieveOutletByOutletId(gongChaNexOutletId);
            OutletEntity gongChaTpy = outletSessionBean.retrieveOutletByOutletId(gongChaTpyOutletId);

            Listing gcGreenMilkTea = new Listing("Green Milk Tea", new BigDecimal(3.00), gongChaNex, "gcgreenmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcGreenMilkTea);
            gongChaNex.getListings().add(gcGreenMilkTea);
            Listing gcBlackMilkTea = new Listing("Black Milk Tea", new BigDecimal(3.00), gongChaNex, "gcblackmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcBlackMilkTea);
            gongChaNex.getListings().add(gcBlackMilkTea);
            Listing gcOolongTea = new Listing("Oolong Tea", new BigDecimal(2.30), gongChaNex, "gcoolongtea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcOolongTea);
            gongChaNex.getListings().add(gcOolongTea);

            Listing gcGreenMilkTeaTpy = new Listing("Green Milk Tea", new BigDecimal(3.00), gongChaTpy, "gcgreenmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcGreenMilkTeaTpy);
            gongChaTpy.getListings().add(gcGreenMilkTeaTpy);
            Listing gcBlackMilkTeaTpy = new Listing("Black Milk Tea", new BigDecimal(3.00), gongChaTpy, "gcblackmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcBlackMilkTeaTpy);
            gongChaTpy.getListings().add(gcBlackMilkTeaTpy);
            Listing gcOolongTeaTpy = new Listing("Oolong Tea", new BigDecimal(2.30), gongChaTpy, "gcoolongtea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcOolongTeaTpy);
            gongChaTpy.getListings().add(gcOolongTeaTpy);

            // for liho
            Map<String, BigDecimal> lhSizeOptions = new HashMap<>();
            lhSizeOptions.put("Medium", new BigDecimal(0.00));
            lhSizeOptions.put("Large", new BigDecimal(1.00));
            Map<String, BigDecimal> lhSugarOptions = new HashMap<>();
            lhSugarOptions.put("0% Sugar free", new BigDecimal(0.00));
            lhSugarOptions.put("30% Little sugar", new BigDecimal(0.00));
            lhSugarOptions.put("50% Half sugar", new BigDecimal(0.00));
            lhSugarOptions.put("70% Less sugar", new BigDecimal(0.00));
            lhSugarOptions.put("100% Full sugar", new BigDecimal(0.00));
            Map<String, BigDecimal> lhIceOptions = new HashMap<>();
            lhIceOptions.put("Normal", new BigDecimal(0.00));
            lhIceOptions.put("Less ice", new BigDecimal(0.00));
            lhIceOptions.put("No Ice", new BigDecimal(0.00));
            Map<String, BigDecimal> lhToppingOptions = new HashMap<>();
            lhToppingOptions.put("Golden Pearl", new BigDecimal(0.60));
            lhToppingOptions.put("White Pearl", new BigDecimal(0.70));
            lhToppingOptions.put("Brown Sugar Pearl", new BigDecimal(0.70));
            lhToppingOptions.put("Unicorn Pearl", new BigDecimal(0.70));
            lhToppingOptions.put("Pudding Jelly", new BigDecimal(0.70));
            lhToppingOptions.put("CheezHO", new BigDecimal(1.20));

            OutletEntity lihoStarVista = outletSessionBean.retrieveOutletByOutletId(lihoStarVistaOutletId);
            OutletEntity lihoOrchardGateway = outletSessionBean.retrieveOutletByOutletId(lihoOrchardGatewayOutletId);

            Listing lhStrawberryLatte = new Listing("Strawberry Latte", new BigDecimal(7.10), lihoStarVista, "lhstrawberrylatte.png", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhStrawberryLatte);
            lihoStarVista.getListings().add(lhStrawberryLatte);
            Listing lhAvocadoMilkCoffee = new Listing("Avocado Milk Coffee", new BigDecimal(6.10), lihoStarVista, "lhavocadomilkcoffee.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhAvocadoMilkCoffee);
            lihoStarVista.getListings().add(lhAvocadoMilkCoffee);
            Listing lhCheezHOMelonTea = new Listing("CheezHO Melon Tea", new BigDecimal(3.40), lihoStarVista, "lhcheezhomelontea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhCheezHOMelonTea);
            lihoStarVista.getListings().add(lhCheezHOMelonTea);

            Listing lhStrawberryLatteOC = new Listing("Strawberry Latte", new BigDecimal(7.10), lihoOrchardGateway, "lhstrawberrylatte.png", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhStrawberryLatteOC);
            lihoOrchardGateway.getListings().add(lhStrawberryLatteOC);
            Listing lhAvocadoMilkCoffeeOC = new Listing("Avocado Milk Coffee", new BigDecimal(6.10), lihoOrchardGateway, "lhavocadomilkcoffee.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhAvocadoMilkCoffeeOC);
            lihoOrchardGateway.getListings().add(lhAvocadoMilkCoffeeOC);
            Listing lhCheezHOMelonTeaOC = new Listing("CheezHO Melon Tea", new BigDecimal(3.40), lihoOrchardGateway, "lhcheezhomelontea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhCheezHOMelonTeaOC);
            lihoOrchardGateway.getListings().add(lhCheezHOMelonTeaOC);

            //customer side of things
            Customer customer = new Customer("Customer 1", "customer", "password", "87654321", "address", "qwerty@gmail.com");
            customer = customerSessionBeanLocal.createNewCustomer(customer);

        } catch (InputDataValidationException | RetailerUsernameExistsException
                | UnknownPersistenceException | OutletNameExistsException
                | RetailerNotFoundException | OutletNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
