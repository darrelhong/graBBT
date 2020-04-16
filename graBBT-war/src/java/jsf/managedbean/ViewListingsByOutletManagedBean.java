package jsf.managedbean;

import ejb.session.stateless.ListingSessionBeanLocal;
import entity.CategoryEntity;
import entity.Listing;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import util.exception.InputDataValidationException;
import util.exception.ListingNotFoundException;
import util.exception.OutletNotFoundException;
import util.exception.UnknownPersistenceException;

@Named(value = "viewListingsByOutletManagedBean")
@ViewScoped
public class ViewListingsByOutletManagedBean implements Serializable {

    @EJB
    private ListingSessionBeanLocal listingSessionBean;

    private Long outletId;
    private List<Listing> outletListings;
    private Listing selectedListing;

    private List<CategoryEntity> categoryEntities;

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
    private UploadedFile upImage;

    public ViewListingsByOutletManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        Long flashId = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("outletId");
        this.outletId = flashId;
        if (outletId != null) {
            try {
                setOutletListings(listingSessionBean.retrieveListingsByOutletId(outletId));
                System.out.println(outletListings);
            } catch (OutletNotFoundException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the outlet details: " + ex.getMessage(), null));

            }
        }
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

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + event.getFile().getFileName();

            System.err.println("********** createNewListing.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** createNewListing.handleFileUpload(): newFilePath: " + newFilePath);
            newListing.setImageSrc(event.getFile().getFileName());

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void createNewListing() {

        try {
            Listing l = listingSessionBean.createNewListing(newListing, outletId);
            outletListings.add(l);
            newListing = new Listing();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New listing created successfully (Listing ID: " + l.getListingId() + ")", null));

        } catch (InputDataValidationException | UnknownPersistenceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new listing: " + ex.getMessage(), null));

        }
    }

    public void deleteListing() {
        System.out.println("Delete called");
        try {
            String result = listingSessionBean.deleteListing(selectedListing.getListingId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Listing ID: " + selectedListing.getListingId() + " " + result, null));

        } catch (ListingNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting the listing: " + ex.getMessage(), null));

        }
    }

    /**
     * @return the outletId
     */
    public Long getOutletId() {
        return outletId;
    }

    /**
     * @param outletId the outletId to set
     */
    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    /**
     * @return the outletListings
     */
    public List<Listing> getOutletListings() {
        return outletListings;
    }

    /**
     * @param outletListings the outletListings to set
     */
    public void setOutletListings(List<Listing> outletListings) {
        this.outletListings = outletListings;
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

    public UploadedFile getUpImage() {
        return upImage;
    }

    public void setUpImage(UploadedFile upImage) {
        this.upImage = upImage;
    }

}
