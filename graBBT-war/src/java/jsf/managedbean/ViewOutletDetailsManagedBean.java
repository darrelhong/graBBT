/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.OutletSessionBeanLocal;
import entity.OutletEntity;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import util.exception.OutletNotFoundException;

/**
 *
 * @author Bryan
 */
@Named(value = "viewOutletDetailsManagedBean")
@ViewScoped
public class ViewOutletDetailsManagedBean implements Serializable
{

    @EJB(name = "OutletSessionBeanLocal")
    private OutletSessionBeanLocal outletSessionBeanLocal;

    private Long outletIdToView;
    private OutletEntity outletEntityToView;
    
    public ViewOutletDetailsManagedBean() 
    {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        outletIdToView = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("outletIdToView");
    
        try
        {
            setOutletEntityToView(outletSessionBeanLocal.retrieveOutletByOutletId(outletIdToView));
        }
        catch (OutletNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while retrieving the outlet details: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }
    
    public void back(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllOutlets.xhtml");
    }
    
    public void updateOutlet(ActionEvent event) throws IOException
    {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("outletIdToUpdate", outletIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("updateOutlet.xhtml");
    }

    /**
     * @return the outletEntityToView
     */
    public OutletEntity getOutletEntityToView() {
        return outletEntityToView;
    }

    /**
     * @param outletEntityToView the outletEntityToView to set
     */
    public void setOutletEntityToView(OutletEntity outletEntityToView) {
        this.outletEntityToView = outletEntityToView;
    }
    
}
