/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.OutletSessionBeanLocal;
import entity.OutletEntity;
import entity.RetailerEntity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import util.exception.InputDataValidationException;
import util.exception.OpeningAndClosingHoursOverlapException;
import util.exception.OutletNameExistsException;
import util.exception.RetailerNotFoundException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author Chloe Tanny
 */
@Named(value = "createNewOutletManagedBean")
@ViewScoped
public class CreateNewOutletManagedBean implements Serializable {

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;

    private Long currentRetailerId;
    private OutletEntity newOutletEntity;

    public CreateNewOutletManagedBean() {
        newOutletEntity = new OutletEntity();
    }

    @PostConstruct
    public void postConstruct() {
        RetailerEntity currentRetailer = (RetailerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentRetailerEntity");
        currentRetailerId = currentRetailer.getRetailerId();
        System.out.println("*********************CreateNewOutletManagedBean POST CONSTRUCT********************" + currentRetailerId);
    }

    public void createNewOutlet(ActionEvent event) {
        try {
            if (newOutletEntity.getOpeningHour() > newOutletEntity.getClosingHour()) {
                throw new OpeningAndClosingHoursOverlapException("Opening and Closing hours overlap");
            }
            System.out.println("test");

            Long newOutletEntityId = outletSessionBeanLocal.createNewOutlet(newOutletEntity, currentRetailerId);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New outlet created successfully (Outlet ID: " + newOutletEntityId + ")", null));
        } catch (OutletNameExistsException | RetailerNotFoundException | UnknownPersistenceException | InputDataValidationException | OpeningAndClosingHoursOverlapException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while creating the new outlet: " + ex.getMessage(), null));
            System.out.println(ex.getMessage());
        }

        newOutletEntity = new OutletEntity(); //reset
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + event.getFile().getFileName();

            System.err.println("********** createNewListing.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** createNewListing.handleFileUpload(): newFilePath: " + newFilePath);

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
            newOutletEntity.setImageSrc(event.getFile().getFileName());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));

        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));

        }
    }

    public Long getCurrentRetailerId() {
        return currentRetailerId;
    }

    public void setCurrentRetailerId(Long currentRetailerId) {
        this.currentRetailerId = currentRetailerId;
    }

    public OutletEntity getNewOutletEntity() {
        return newOutletEntity;
    }

    public void setNewOutletEntity(OutletEntity newOutletEntity) {
        this.newOutletEntity = newOutletEntity;
    }

}
