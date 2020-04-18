package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.CategoryEntity;
import entity.Listing;
import entity.OutletEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.InputDataValidationException;
import util.exception.UnknownPersistenceException;

@Named(value = "listingManagedBean")
@ViewScoped
public class ListingManagedBean implements Serializable {

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    private List<Listing> listings;
    private Listing selectedListing;

    private List<CategoryEntity> categoryEntities;
    private Long outletId;

    private Listing newListing;
    private Long outletIdNew;
    private Long categoryIdNew;
    private String sizeNameInput;
    private Double sizePriceInput;
    private String sugarNameInput;
    private Double sugarPriceInput;
    private String iceNameInput;
    private Double icePriceInput;
    private String toppingNameInput;
    private Double toppingPriceInput;

    public ListingManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("LISTING MANAGED BEAN POST CONSTRUCT");
        listings = listingSessionBean.retrieveAllListings();
        outletId = 1L;
        newListing = new Listing();
        sizePriceInput = 0.0;
        sugarPriceInput = 0.0;
        icePriceInput = 0.0;
        toppingPriceInput = 0.0;
    }

    public void addSizeOption() {
        System.out.println("ADD SIZE OPTION!!!!!!");
        if (sizeNameInput.equals("")) {
            return;
        }
        System.out.println(sizeNameInput);
        System.out.println(sizePriceInput);
        newListing.getSizeOptions().put(sizeNameInput, sizePriceInput);
        System.out.println(getNewListing().getSizeOptions().toString());
        sizeNameInput = "";
        sizePriceInput = 0.0;
    }

    public void addSugarOption() {
        System.out.println("ADD SUGAR OPTION!!!!!!");
        if (sugarNameInput.equals("")) {
            return;
        }
        System.out.println(sugarNameInput);
        System.out.println(sugarPriceInput);
        newListing.getSugarOptions().put(sugarNameInput, sugarPriceInput);
        System.out.println(getNewListing().getSugarOptions().toString());
        sugarNameInput = "";
        sugarPriceInput = 0.0;
    }

    public void addIceOption() {
        System.out.println("ADD ICE OPTION!!!!!!");
        if (iceNameInput.equals("")) {
            return;
        }
        System.out.println(iceNameInput);
        System.out.println(icePriceInput);
        newListing.getIceOptions().put(iceNameInput, icePriceInput);
        System.out.println(getNewListing().getIceOptions().toString());
        iceNameInput = "";
        icePriceInput = 0.0;
    }

    public void addToppingOption() {
        System.out.println("ADD TOPPING OPTION!!!!!!");
        if (toppingNameInput.equals("")) {
            return;
        }
        System.out.println(toppingNameInput);
        System.out.println(toppingPriceInput);
        newListing.getToppingOptions().put(toppingNameInput, toppingPriceInput);
        System.out.println(getNewListing().getToppingOptions().toString());
        toppingNameInput = "";
        toppingPriceInput = 0.0;
    }

    public void createNewListing() {

        try {
            Listing l = listingSessionBean.createNewListing(newListing, outletId);
            listings.add(l);
            newListing = new Listing();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New listing created successfully (Listing ID: " + l.getListingId() + ")", null));

        } catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new listing: " + ex.getMessage(), null));

        }
    }

    /**
     * @return the listings
     */
    public List<Listing> getListings() {
        return listings;
    }

    /**
     * @param listings the listings to set
     */
    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    /**
     * @return the selectedListing
     */
    public Listing getSelectedListing() {
        return selectedListing;
    }

    /**
     * @param selectedListing the selectedListing to set
     */
    public void setSelectedListing(Listing selectedListing) {
        this.selectedListing = selectedListing;
    }

    /**
     * @return the newListing
     */
    public Listing getNewListing() {
        return newListing;
    }

    /**
     * @param newListing the newListing to set
     */
    public void setNewListing(Listing newListing) {
        this.newListing = newListing;
    }

    /**
     * @return the outletIdNew
     */
    public Long getOutletIdNew() {
        return outletIdNew;
    }

    /**
     * @param outletIdNew the outletIdNew to set
     */
    public void setOutletIdNew(Long outletIdNew) {
        this.outletIdNew = outletIdNew;
    }

    /**
     * @return the categoryIdNew
     */
    public Long getCategoryIdNew() {
        return categoryIdNew;
    }

    /**
     * @param categoryIdNew the categoryIdNew to set
     */
    public void setCategoryIdNew(Long categoryIdNew) {
        this.categoryIdNew = categoryIdNew;
    }

    /**
     * @return the sizeNameInput
     */
    public String getSizeNameInput() {
        return sizeNameInput;
    }

    /**
     * @param sizeNameInput the sizeNameInput to set
     */
    public void setSizeNameInput(String sizeNameInput) {
        this.sizeNameInput = sizeNameInput;
    }

    /**
     * @return the sizePriceInput
     */
    public Double getSizePriceInput() {
        return sizePriceInput;
    }

    /**
     * @param sizePriceInput the sizePriceInput to set
     */
    public void setSizePriceInput(Double sizePriceInput) {
        this.sizePriceInput = sizePriceInput;
    }

    /**
     * @return the sugarNameInput
     */
    public String getSugarNameInput() {
        return sugarNameInput;
    }

    /**
     * @param sugarNameInput the sugarNameInput to set
     */
    public void setSugarNameInput(String sugarNameInput) {
        this.sugarNameInput = sugarNameInput;
    }

    /**
     * @return the iceNameInput
     */
    public String getIceNameInput() {
        return iceNameInput;
    }

    /**
     * @param iceNameInput the iceNameInput to set
     */
    public void setIceNameInput(String iceNameInput) {
        this.iceNameInput = iceNameInput;
    }

    /**
     * @return the toppingNameInput
     */
    public String getToppingNameInput() {
        return toppingNameInput;
    }

    /**
     * @param toppingNameInput the toppingNameInput to set
     */
    public void setToppingNameInput(String toppingNameInput) {
        this.toppingNameInput = toppingNameInput;
    }

    /**
     * @return the categoryEntities
     */
    public List<CategoryEntity> getCategoryEntities() {
        return categoryEntities;
    }

    /**
     * @param categoryEntities the categoryEntities to set
     */
    public void setCategoryEntities(List<CategoryEntity> categoryEntities) {
        this.categoryEntities = categoryEntities;
    }

    /**
     * @return the sugarPriceInput
     */
    public Double getSugarPriceInput() {
        return sugarPriceInput;
    }

    /**
     * @param sugarPriceInput the sugarPriceInput to set
     */
    public void setSugarPriceInput(Double sugarPriceInput) {
        this.sugarPriceInput = sugarPriceInput;
    }

    /**
     * @return the icePriceInput
     */
    public Double getIcePriceInput() {
        return icePriceInput;
    }

    /**
     * @param icePriceInput the icePriceInput to set
     */
    public void setIcePriceInput(Double icePriceInput) {
        this.icePriceInput = icePriceInput;
    }

    /**
     * @return the toppingPriceInput
     */
    public Double getToppingPriceInput() {
        return toppingPriceInput;
    }

    /**
     * @param toppingPriceInput the toppingPriceInput to set
     */
    public void setToppingPriceInput(Double toppingPriceInput) {
        this.toppingPriceInput = toppingPriceInput;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }
}
