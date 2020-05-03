package ejb.session.singleton;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocal;
import entity.Listing;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import ejb.session.stateless.OutletSessionBeanLocal;
import ejb.session.stateless.PromoSessionBeanLocal;
import ejb.session.stateless.RetailerSessionBeanLocal;
import entity.Customer;
import entity.OutletEntity;
import entity.PromoEntity;
import entity.RetailerEntity;
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
    @EJB
    private OrderSessionBeanLocal orderSessionBeanLocal;
    @EJB
    private PromoSessionBeanLocal promoSessionBeanLocal;

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
            // Creating retailers
            Long retailerId = retailerSessionBean
                    .createNewRetailer(new RetailerEntity("KOI Thé", "manager", "password"));
            Long koiPayaOutletId = outletSessionBean.createNewOutlet(
                    new OutletEntity("KOI Paya Lebar", 9, 20, 1.3178, 103.8924, "koi.jpeg"), retailerId);
            Long koiJurongOutletId = outletSessionBean
                    .createNewOutlet(new OutletEntity("KOI Jurong", 9, 20, 1.3329, 103.7436, "koi.jpeg"), retailerId);

            Long gongChaRetailerId = retailerSessionBean
                    .createNewRetailer(new RetailerEntity("Gong Cha", "manager2", "password"));
            Long gongChaNexOutletId = outletSessionBean.createNewOutlet(
                    new OutletEntity("Gong Cha NEX", 10, 21, 1.350690, 103.872293, "gongcha.jpeg"), gongChaRetailerId);
            Long gongChaTpyOutletId = outletSessionBean.createNewOutlet(
                    new OutletEntity("Gong Cha Toa Payoh", 10, 21, 1.332910, 103.84819, "gongcha.jpeg"),
                    gongChaRetailerId);

            Long lihoRetailerId = retailerSessionBean
                    .createNewRetailer(new RetailerEntity("LiHO Tea", "manager3", "password"));
            Long lihoStarVistaOutletId = outletSessionBean.createNewOutlet(
                    new OutletEntity("LiHO Star Vista", 8, 20, 1.306803, 103.788484, "liho.jpeg"), lihoRetailerId);
            Long lihoOrchardGatewayOutletId = outletSessionBean.createNewOutlet(
                    new OutletEntity("LiHO Orchard Gateway", 8, 20, 1.300763, 103.839079, "liho.jpeg"), lihoRetailerId);

            // Creating admin account
            Long adminRetailerId = retailerSessionBean
                    .createNewRetailer(new RetailerEntity("Admin", "admin", "password", true));
            
            // Creating options hashmaps
            Map<String, Double> sizeOptions = new HashMap<>();
            sizeOptions.put("Medium", 0.00);
            sizeOptions.put("Large", 1.10);
            Map<String, Double> sugarOptions = new HashMap<>();
            sugarOptions.put("0%", 0.00);
            sugarOptions.put("25%", 0.00);
            sugarOptions.put("50%", 0.00);
            sugarOptions.put("100%", 0.00);
            Map<String, Double> iceOptions = new HashMap<>();
            iceOptions.put("Normal Ice", 0.00);
            iceOptions.put("No Ice", 0.00);
            Map<String, Double> toppingOptions = new HashMap<>();
            toppingOptions.put("Golden Bubble", 0.60);
            toppingOptions.put("Konjac Jelly", 1.20);
            toppingOptions.put("Aloe Vera", 1.20);

            // just creating some BBTs and associating them with outlets
            OutletEntity koiPaya = outletSessionBean.retrieveOutletByOutletId(koiPayaOutletId);
            OutletEntity koiJurong = outletSessionBean.retrieveOutletByOutletId(koiJurongOutletId);
            Listing koiMilkTea = new Listing("Milk Tea", new BigDecimal(3.40), koiPaya, "koimilktea.jpeg", sizeOptions,
                    sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiMilkTea);
            koiPaya.getListings().add(koiMilkTea);
            Listing koiCacaoBarry = new Listing("Cacao Barry", new BigDecimal(4.00), koiPaya,
                    "Made with 100% pure Cacao Powder", "koicacaobarry.jpeg", sizeOptions, sugarOptions, iceOptions,
                    toppingOptions);
            listingSessionBean.createNewListing(koiCacaoBarry);
            koiPaya.getListings().add(koiCacaoBarry);
            Listing koiBlackTeaMacchiato = new Listing("Black Tea Macchiato", new BigDecimal(3.30), koiJurong,
                    "koiblackteamacchiato.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiBlackTeaMacchiato);
            koiJurong.getListings().add(koiBlackTeaMacchiato);
            Listing koiGoldenOolong = new Listing("Golden Oolong Tea", new BigDecimal(2.70), koiJurong,
                    "koigoldenoolongtea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiGoldenOolong);
            koiJurong.getListings().add(koiGoldenOolong);

            Listing koiJasmineGreenTeaPaya = new Listing("Jasmine Green Tea", new BigDecimal(2.70), koiPaya, "koigreentea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiJasmineGreenTeaPaya);
            Listing koiJasmineGreenTeaJurong = new Listing("Jasmine Green Tea", new BigDecimal(2.70), koiJurong, "koigreentea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiJasmineGreenTeaJurong);
            Listing koiAssamBlackTeaPaya = new Listing("Assam Black Tea", new BigDecimal(2.70), koiPaya, "koiassamblacktea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiAssamBlackTeaPaya);
            Listing koiAssamBlackTeaJurong = new Listing("Assam Black Tea", new BigDecimal(2.70), koiJurong, "koiassamblacktea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiAssamBlackTeaJurong);
            Listing koiYakaultGreenTeaPaya = new Listing("Yakult Green Tea", new BigDecimal(5.30), koiPaya, "koiyakultgreentea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiYakaultGreenTeaPaya);
            Listing koiYakaultGreenTeaJurong = new Listing("Yakult Green Tea", new BigDecimal(5.30), koiJurong, "koiyakultgreentea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiYakaultGreenTeaJurong);
            Listing koiFreshPassionGreenTeaPaya = new Listing("Fresh Passion Green Tea", new BigDecimal(4.00), koiPaya, "koifreshpassiongreentea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiFreshPassionGreenTeaPaya);
            Listing koiFreshPassionGreenTeaJurong = new Listing("Fresh Passion Green Tea", new BigDecimal(4.00), koiJurong, "koifreshpassiongreentea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiFreshPassionGreenTeaJurong);
            Listing koiOolongMilkTeaPaya = new Listing("Oolong Milk Tea", new BigDecimal(3.40), koiPaya, "koioolongmilktea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiOolongMilkTeaPaya);
            Listing koiOolongMilkTeaJurong = new Listing("Oolong Milk Tea", new BigDecimal(3.40), koiJurong, "koioolongmilktea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiOolongMilkTeaJurong);
            Listing koiHazelnutMilkTeaPaya = new Listing("Hazelnut Milk Tea", new BigDecimal(4.00), koiPaya, "koihazelnutmilktea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiHazelnutMilkTeaPaya);
            Listing koiHazelnutMilkTeaJurong = new Listing("Hazelnut Milk Tea", new BigDecimal(4.00), koiJurong, "koihazelnutmilktea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiHazelnutMilkTeaJurong);
            Listing koiCaramelMilkTeaPaya = new Listing("Caramel Milk Tea", new BigDecimal(4.00), koiPaya, "koicaramelmilktea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiCaramelMilkTeaPaya);
            Listing koiCaramelMilkTeaJurong = new Listing("Caramel Milk Tea", new BigDecimal(4.00), koiJurong, "koicaramelmilktea.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiCaramelMilkTeaJurong);
            Listing koiChocolateMilkPaya = new Listing("Chocolate Milk", new BigDecimal(4.00), koiPaya, "koichocolatemilk.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiChocolateMilkPaya);
            Listing koiChocolateMilkJurong = new Listing("Chocoloate Milk", new BigDecimal(4.00), koiJurong, "koichocolatemilk.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiChocolateMilkJurong);
            Listing koiOvaltinePaya = new Listing("Ovaltine", new BigDecimal(4.00), koiPaya, "koiovaltine.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiOvaltinePaya);
            Listing koiOvaltineJurong = new Listing("Ovaltine", new BigDecimal(4.00), koiJurong, "koiovaltine.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiOvaltineJurong);
            Listing koiFreshLemonJuicePaya = new Listing("Fresh Lemon Juice", new BigDecimal(3.40), koiPaya, "koifreshlemonjuice.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiFreshLemonJuicePaya);
            Listing koiFreshLemonJuiceJurong = new Listing("Fresh Lemon Juice", new BigDecimal(3.40), koiJurong, "koifreshlemonjuice.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiFreshLemonJuiceJurong);
            Listing koiIceHoneyPaya = new Listing("Ice Honey", new BigDecimal(3.40), koiPaya, "koiicehoney.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiIceHoneyPaya);
            Listing koiIceHoneyJurong = new Listing("Ice Honey", new BigDecimal(3.40), koiJurong, "koiicehoney.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiIceHoneyJurong);
            Listing koiHoneyLemonLimeJuicePaya = new Listing("Honey Lemon Lime Juice", new BigDecimal(4.00), koiPaya, "koihoneylemonlimejuice.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiHoneyLemonLimeJuicePaya);
            Listing koiHoneyLemonLimeJuiceJurong = new Listing("Honey Lemon Lime Juice", new BigDecimal(4.00), koiJurong, "koihoneylemonlimejuice.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiHoneyLemonLimeJuiceJurong);
            Listing koiCeylonBlackTeaLattePaya = new Listing("Ceylon Black Tea Latte", new BigDecimal(4.00), koiPaya, "koiceylonblacktealatte.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiCeylonBlackTeaLattePaya);
            Listing koiCeylonBlackTeaLatteJurong = new Listing("Ceylon Black Tea Latte", new BigDecimal(4.00), koiJurong, "koiceylonblacktealatte.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiCeylonBlackTeaLatteJurong);
            Listing koiGreenTeaMacchiatoPaya = new Listing("Green Tea Macchiato", new BigDecimal(3.30), koiPaya, "koigreenteamacchiato.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiGreenTeaMacchiatoPaya);
            Listing koiGreenTeaMacchiatoJurong = new Listing("Green Tea Macchiato", new BigDecimal(3.30), koiJurong, "koigreenteamacchiato.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiGreenTeaMacchiatoJurong);
            Listing koiOolongMacchiatoPaya = new Listing("Oolong Macchiato", new BigDecimal(3.30), koiPaya, "koioolongmacchiato.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiOolongMacchiatoPaya);
            Listing koiOolongMacchiatoJurong = new Listing("Oolong Macchiato", new BigDecimal(3.30), koiJurong, "koioolongmacchiato.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiOolongMacchiatoJurong);
            Listing koiPeachMacchiatoPaya = new Listing("Peach Macchiato", new BigDecimal(4.40), koiPaya, "koipeachmacchiato.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiPeachMacchiatoPaya);
            Listing koiPeachMacchiatoJurong = new Listing("Peach Macchiato", new BigDecimal(4.40), koiJurong, "koipeachmacchiato.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiPeachMacchiatoJurong);
            Listing koiOvaltineMacchiatoPaya = new Listing("Ovaltine Macchiato", new BigDecimal(4.40), koiPaya, "koiovaltinemacchiato.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiOvaltineMacchiatoPaya);
            Listing koiOvaltineMacchiatoJurong = new Listing("Ovaltine Macchiato", new BigDecimal(4.40), koiJurong, "koiovaltinemacchiato.jpeg", sizeOptions, sugarOptions, iceOptions, toppingOptions);
            listingSessionBean.createNewListing(koiOvaltineMacchiatoJurong);

            // for gong cha
            Map<String, Double> gcSizeOptions = new HashMap<>();
            gcSizeOptions.put("Medium", 0.00);
            gcSizeOptions.put("Large", 1.10);
            Map<String, Double> gcSugarOptions = new HashMap<>();
            gcSugarOptions.put("0% Sugar free", 0.00);
            gcSugarOptions.put("30% Little sugar", 0.00);
            gcSugarOptions.put("50% Half sugar", 0.00);
            gcSugarOptions.put("70% Less sugar", 0.00);
            gcSugarOptions.put("100% Full sugar", 0.00);
            Map<String, Double> gcIceOptions = new HashMap<>();
            gcIceOptions.put("Normal Ice", 0.00);
            gcIceOptions.put("No Ice", 0.00);
            Map<String, Double> gcToppingOptions = new HashMap<>();
            gcToppingOptions.put("Herbal Jelly", 0.70);
            gcToppingOptions.put("Pudding Jelly", 0.70);
            gcToppingOptions.put("Pearl", 0.40);
            gcToppingOptions.put("White Pearl", 0.70);
            gcToppingOptions.put("Coconut Jelly", 0.50);
            gcToppingOptions.put("Red Bean", 0.70);
            gcToppingOptions.put("Milk Foam (Original)", 0.80);
            gcToppingOptions.put("Aloe Vera", 0.70);
            gcToppingOptions.put("Rainbow Jelly", 0.60);
            gcToppingOptions.put("Basil Seeds", 0.40);

            OutletEntity gongChaNex = outletSessionBean.retrieveOutletByOutletId(gongChaNexOutletId);
            OutletEntity gongChaTpy = outletSessionBean.retrieveOutletByOutletId(gongChaTpyOutletId);

            Listing gcGreenMilkTea = new Listing("Green Milk Tea", new BigDecimal(3.00), gongChaNex,
                    "gcgreenmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcGreenMilkTea);
            gongChaNex.getListings().add(gcGreenMilkTea);
            Listing gcBlackMilkTea = new Listing("Black Milk Tea", new BigDecimal(3.00), gongChaNex,
                    "gcblackmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcBlackMilkTea);
            gongChaNex.getListings().add(gcBlackMilkTea);
            Listing gcOolongTea = new Listing("Oolong Tea", new BigDecimal(2.30), gongChaNex, "gcoolongtea.jpeg",
                    gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcOolongTea);
            gongChaNex.getListings().add(gcOolongTea);

            Listing gcGreenMilkTeaTpy = new Listing("Green Milk Tea", new BigDecimal(3.00), gongChaTpy,
                    "gcgreenmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcGreenMilkTeaTpy);
            gongChaTpy.getListings().add(gcGreenMilkTeaTpy);
            Listing gcBlackMilkTeaTpy = new Listing("Black Milk Tea", new BigDecimal(3.00), gongChaTpy,
                    "gcblackmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcBlackMilkTeaTpy);
            gongChaTpy.getListings().add(gcBlackMilkTeaTpy);
            Listing gcOolongTeaTpy = new Listing("Oolong Tea", new BigDecimal(2.30), gongChaTpy, "gcoolongtea.jpeg",
                    gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcOolongTeaTpy);
            gongChaTpy.getListings().add(gcOolongTeaTpy);

            Listing gcOolongMilkTeaNex = new Listing("Oolong Milk Tea", new BigDecimal(3.00), gongChaNex, "gcoolongmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcOolongMilkTeaNex);
            Listing gcOolongMilkTeaTpy = new Listing("Oolong Milk Tea", new BigDecimal(3.00), gongChaTpy, "gcoolongmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcOolongMilkTeaTpy);
            Listing gcAlisanMilkTeaNex = new Listing("Alisan Milk Tea", new BigDecimal(3.00), gongChaNex, "gcalisanmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcAlisanMilkTeaNex);
            Listing gcAlisanMilkTeaTpy = new Listing("Alisan Milk Tea", new BigDecimal(3.00), gongChaTpy, "gcalisanmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcAlisanMilkTeaTpy);
            Listing gcEarlGreyMilkTeaNex = new Listing("Earl Grey Milk Tea", new BigDecimal(3.00), gongChaNex, "gcearlgreymilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcEarlGreyMilkTeaNex);
            Listing gcEarlGreyMilkTeaTpy = new Listing("Earl Grey Milk Tea", new BigDecimal(3.00), gongChaTpy, "gcearlgreymilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcEarlGreyMilkTeaTpy);
            Listing gcCaramelMilkTeaNex = new Listing("Caramel Milk Tea", new BigDecimal(3.50), gongChaNex, "gccaramelmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcCaramelMilkTeaNex);
            Listing gcCaramelMilkTeaTpy = new Listing("Caramel Milk Tea", new BigDecimal(3.50), gongChaTpy, "gccaramelmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcCaramelMilkTeaTpy);
            Listing gcEarlGreyMilkTea3jNex = new Listing("Earl Grey Milk Tea w 3J", new BigDecimal(4.40), gongChaNex, "gcearlgreymilktea3j.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcEarlGreyMilkTea3jNex);
            Listing gcEarlGreyMilkTea3jTpy = new Listing("Earl Grey Milk Tea w 3J", new BigDecimal(4.40), gongChaTpy, "gcearlgreymilktea3j.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcEarlGreyMilkTea3jTpy);
            Listing gcChocolateDrinkNex = new Listing("Chocolate Drink", new BigDecimal(3.60), gongChaNex, "gcchocolatedrink.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcChocolateDrinkNex);
            Listing gcChocolateDrinkTpy = new Listing("Chocolate Drink", new BigDecimal(3.60), gongChaTpy, "gcchocolatedrink.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcChocolateDrinkTpy);
            Listing gcTaroDrinkNex = new Listing("Taro Drink", new BigDecimal(3.60), gongChaNex, "gctarodrink.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcTaroDrinkNex);
            Listing gcTaroDrinkTpy = new Listing("Taro Drink", new BigDecimal(3.60), gongChaTpy, "gctarodrink.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcTaroDrinkTpy);
            Listing gcBrownSugarGingerMilkTeaNex = new Listing("Brown Sugar Ginger Milk Tea", new BigDecimal(3.60), gongChaNex, "gcbrownsugargingermilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcBrownSugarGingerMilkTeaNex);
            Listing gcBrownSugarGingerMilkTeaTpy = new Listing("Brown Sugar Ginger Milk Tea", new BigDecimal(3.60), gongChaTpy, "gcbrownsugargingermilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcBrownSugarGingerMilkTeaTpy);
            Listing gcMatchaRedBeanNex = new Listing("Matcha w Read Bean", new BigDecimal(4.50), gongChaNex, "gcmatchareadbean.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcMatchaRedBeanNex);
            Listing gcMatchaRedBeanTpy = new Listing("Matcha w Read Bean", new BigDecimal(4.50), gongChaTpy, "gcmatchareadbean.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcMatchaRedBeanTpy);
            Listing gcMilkTeaReadBeanJellyNex = new Listing("Milk Tea w Red Bean & Pudding Jelly", new BigDecimal(4.50), gongChaNex, "gcmilkteareadbeanpuddingjelly.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcMilkTeaReadBeanJellyNex);
            Listing gcMilkTeaReadBeanJellyTpy = new Listing("Milk Tea w Red Bean & Pudding Jelly", new BigDecimal(4.50), gongChaTpy, "gcmilkteareadbeanpuddingjelly.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcMilkTeaReadBeanJellyTpy);
            Listing gcGreenFreshMilkTeaNex = new Listing("Green Milk Tea (Fresh Milk)", new BigDecimal(3.40), gongChaNex, "gcgreenmilkteafreshmilk.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcGreenFreshMilkTeaNex);
            Listing gcGreenFreshMilkTeaTpy = new Listing("Green Milk Tea (Fresh Milk)", new BigDecimal(3.40), gongChaTpy, "gcgreenmilkteafreshmilk.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcGreenFreshMilkTeaTpy);
            Listing gcMilkFoamBSOolongNex = new Listing("Milk Foam Brown Sugar Oolong", new BigDecimal(3.90), gongChaNex, "gcmilkfoambrownsugaroolong.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcMilkFoamBSOolongNex);
            Listing gcMilkFoamBSOolongTpy = new Listing("Milk Foam Brown Sugar Oolong", new BigDecimal(3.90), gongChaTpy, "gcmilkfoambrownsugaroolong.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcMilkFoamBSOolongTpy);
            Listing gcBrownSugarFreshMilkNex = new Listing("Brown Sugar Fresh Milk", new BigDecimal(4.50), gongChaNex, "gcbrownsugarfreshmilk.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcBrownSugarFreshMilkNex);
            Listing gcBrownSugarFreshMilkTpy = new Listing("Brown Sugar Fresh Milk", new BigDecimal(4.50), gongChaTpy, "gcbrownsugarfreshmilk.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcBrownSugarFreshMilkTpy);
            Listing gcBrownSugarFreshMilkTeaNex = new Listing("Brown Sugar Fresh Milk Tea", new BigDecimal(4.50), gongChaNex, "gcbrownsugarfreshmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcBrownSugarFreshMilkTeaNex);
            Listing gcBrownSugarFreshMilkTeaTpy = new Listing("Brown Sugar Fresh Milk Tea", new BigDecimal(4.50), gongChaTpy, "gcbrownsugarfreshmilktea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcBrownSugarFreshMilkTeaTpy);
            Listing gcHoneyLemonGreenTeaNex = new Listing("Honey Lemon Green Tea", new BigDecimal(3.50), gongChaNex, "gchoneylemongreentea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcHoneyLemonGreenTeaNex);
            Listing gcHoneyLemonGreenTeaTpy = new Listing("Honey Lemon Green Tea", new BigDecimal(3.50), gongChaTpy, "gchoneylemongreentea.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcHoneyLemonGreenTeaTpy);
            Listing gcRoselleNex = new Listing("Roselle", new BigDecimal(2.80), gongChaNex, "gcroselle.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcRoselleNex);
            Listing gcRoselleTpy = new Listing("Roselle", new BigDecimal(2.80), gongChaTpy, "gcroselle.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcRoselleTpy);
            Listing gcStrawberryTaroLatteNex = new Listing("Strawberry Taro Latte", new BigDecimal(4.50), gongChaNex, "gcstrawberrytarolatte.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcStrawberryTaroLatteNex);
            Listing gcStrawberryTaroLatteTpy = new Listing("Strawberry Taro Latte", new BigDecimal(4.50), gongChaTpy, "gcstrawberrytarolatte.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcStrawberryTaroLatteTpy);
            Listing gcMangoMatchaLatteNex = new Listing("Mango Matcha Latte", new BigDecimal(4.50), gongChaNex, "gcmangomatchalatte.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcMangoMatchaLatteNex);
            Listing gcMangoMatchaLatteTpy = new Listing("Mango Matcha Latte", new BigDecimal(4.50), gongChaTpy, "gcmangomatchalatte.jpeg", gcSizeOptions, gcSugarOptions, gcIceOptions, gcToppingOptions);
            listingSessionBean.createNewListing(gcMangoMatchaLatteTpy);

            // for liho
            Map<String, Double> lhSizeOptions = new HashMap<>();
            lhSizeOptions.put("Medium", 0.00);
            lhSizeOptions.put("Large", 1.00);
            Map<String, Double> lhSugarOptions = new HashMap<>();
            lhSugarOptions.put("0% Sugar free", 0.00);
            lhSugarOptions.put("30% Little sugar", 0.00);
            lhSugarOptions.put("50% Half sugar", 0.00);
            lhSugarOptions.put("70% Less sugar", 0.00);
            lhSugarOptions.put("100% Full sugar", 0.00);
            Map<String, Double> lhIceOptions = new HashMap<>();
            lhIceOptions.put("Normal", 0.00);
            lhIceOptions.put("Less ice", 0.00);
            lhIceOptions.put("No Ice", 0.00);
            Map<String, Double> lhToppingOptions = new HashMap<>();
            lhToppingOptions.put("Golden Pearl", 0.60);
            lhToppingOptions.put("White Pearl", 0.70);
            lhToppingOptions.put("Brown Sugar Pearl", 0.70);
            lhToppingOptions.put("Unicorn Pearl", 0.70);
            lhToppingOptions.put("Pudding Jelly", 0.70);
            lhToppingOptions.put("CheezHO", 1.20);

            OutletEntity lihoSV = outletSessionBean.retrieveOutletByOutletId(lihoStarVistaOutletId);
            OutletEntity lihoOG = outletSessionBean.retrieveOutletByOutletId(lihoOrchardGatewayOutletId);

            Listing lhStrawberryLatte = new Listing("Strawberry Latte", new BigDecimal(7.10), lihoSV,
                    "lhstrawberrylatte.png", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhStrawberryLatte);
            lihoSV.getListings().add(lhStrawberryLatte);
            Listing lhAvocadoMilkCoffee = new Listing("Avocado Milk Coffee", new BigDecimal(6.10), lihoSV,
                    "lhavocadomilkcoffee.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhAvocadoMilkCoffee);
            lihoSV.getListings().add(lhAvocadoMilkCoffee);
            Listing lhCheezHOMelonTea = new Listing("CheezHO Melon Tea", new BigDecimal(3.40), lihoSV,
                    "lhcheezhomelontea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhCheezHOMelonTea);
            lihoSV.getListings().add(lhCheezHOMelonTea);

            Listing lhStrawberryLatteOC = new Listing("Strawberry Latte", new BigDecimal(7.10), lihoOG,
                    "lhstrawberrylatte.png", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhStrawberryLatteOC);
            lihoOG.getListings().add(lhStrawberryLatteOC);
            Listing lhAvocadoMilkCoffeeOC = new Listing("Avocado Milk Coffee", new BigDecimal(6.10), lihoOG,
                    "lhavocadomilkcoffee.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhAvocadoMilkCoffeeOC);
            lihoOG.getListings().add(lhAvocadoMilkCoffeeOC);
            Listing lhCheezHOMelonTeaOC = new Listing("CheezHO Melon Tea", new BigDecimal(3.40), lihoOG,
                    "lhcheezhomelontea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhCheezHOMelonTeaOC);
            lihoOG.getListings().add(lhCheezHOMelonTeaOC);

            Listing lhCheezHoDHPMilkTeaSV = new Listing("CheezHO Da Hong Pao Milk Tea", new BigDecimal(4.70), lihoSV, "lhdhpmilktea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhCheezHoDHPMilkTeaSV);
            Listing lhBlueberryJssSV = new Listing("CheezHO Blueberry Jing Syuan Slush", new BigDecimal(6.50), lihoSV, "lhblueberryjss.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhBlueberryJssSV);
            Listing lhBSPSCFMSV = new Listing("Brown Sugar Pearl Salted Caramel Fresh Milk", new BigDecimal(4.30), lihoSV, "lhbspscfm.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhBSPSCFMSV);
            Listing lhDHPMTBSPSV = new Listing("Da Hong Pao Milk Tea + Brown Sugar Pearl", new BigDecimal(4.20), lihoSV, "lhdhpmilkteabsp.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhDHPMTBSPSV);
            Listing lhGoldenAvocadoMilkSV = new Listing("Golden Avocado Milk", new BigDecimal(4.20), lihoSV, "lhgoldenavocadomilk.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhGoldenAvocadoMilkSV);
            Listing lhTropicalAvocadoSmoothieSV = new Listing("Tropical Avocado Smoothie", new BigDecimal(6.30), lihoSV, "lhtropicalavocadosmoothie.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhTropicalAvocadoSmoothieSV);
            Listing lhRechercheCollagenBeautyTeaSV = new Listing("Recherche Collagen Beauty Tea", new BigDecimal(6.90), lihoSV, "lhcolbeautea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhRechercheCollagenBeautyTeaSV);
            Listing lhCheezHOGreenTeaSV = new Listing("CheezHO Green Tea", new BigDecimal(3.40), lihoSV, "lhchgreentea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhCheezHOGreenTeaSV);
            Listing lhCheezHOJingSyuanTeaSV = new Listing("CheezHO Jing Syuan Tea", new BigDecimal(3.40), lihoSV, "lhchjstea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhCheezHOJingSyuanTeaSV);
            Listing lhCheezHoDHPMilkTeaOG = new Listing("CheezHO Da Hong Pao Milk Tea", new BigDecimal(4.70), lihoOG, "lhdhpmilktea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhCheezHoDHPMilkTeaOG);
            Listing lhBlueberryJssOG = new Listing("CheezHO Blueberry Jing Syuan Slush", new BigDecimal(6.50), lihoOG, "lhblueberryjss.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhBlueberryJssOG);
            Listing lhBSPSCFMOG = new Listing("Brown Sugar Pearl Salted Caramel Fresh Milk", new BigDecimal(4.30), lihoOG, "lhbspscfm.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhBSPSCFMOG);
            Listing lhDHPMTBSPOG = new Listing("Da Hong Pao Milk Tea + Brown Sugar Pearl", new BigDecimal(4.20), lihoOG, "lhdhpmilkteabsp.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhDHPMTBSPOG);
            Listing lhGoldenAvocadoMilkOG = new Listing("Golden Avocado Milk", new BigDecimal(4.20), lihoOG, "lhgoldenavocadomilk.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhGoldenAvocadoMilkOG);
            Listing lhTropicalAvocadoSmoothieOG = new Listing("Tropical Avocado Smoothie", new BigDecimal(6.30), lihoOG, "lhtropicalavocadosmoothie.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhTropicalAvocadoSmoothieOG);
            Listing lhRechercheCollagenBeautyTeaOG = new Listing("Recherche Collagen Beauty Tea", new BigDecimal(6.90), lihoOG, "lhcolbeautea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhRechercheCollagenBeautyTeaOG);
            Listing lhCheezHOGreenTeaOG = new Listing("CheezHO Green Tea", new BigDecimal(3.40), lihoOG, "lhchgreentea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhCheezHOGreenTeaOG);
            Listing lhCheezHOJingSyuanTeaOG = new Listing("CheezHO Jing Syuan Tea", new BigDecimal(3.40), lihoOG, "lhchjstea.jpeg", lhSizeOptions, lhSugarOptions, lhIceOptions, lhToppingOptions);
            listingSessionBean.createNewListing(lhCheezHOJingSyuanTeaOG);

            //Creating promos
            PromoEntity newPromo = new PromoEntity("TEST001", new BigDecimal(2.00), 5);
            promoSessionBeanLocal.createNewPromo(newPromo);
            newPromo = new PromoEntity("TEST002", new BigDecimal(2.50), 10);
            promoSessionBeanLocal.createNewPromo(newPromo);

            // customer side of things
            Customer customer = new Customer("Customer 1", "customer", "password", "87654321", "address",
                    "qwerty@gmail.com");
            customer = customerSessionBeanLocal.createNewCustomer(customer);

        } catch (InputDataValidationException | RetailerUsernameExistsException | UnknownPersistenceException
                | OutletNameExistsException | RetailerNotFoundException | OutletNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
