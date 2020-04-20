
package jsf.managedbean;

import ejb.session.stateless.OutletSessionBeanLocal;
import ejb.session.stateless.RetailerSessionBeanLocal;
import entity.OutletEntity;
import entity.RetailerEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;


@Named(value = "viewMyAccountManagedBean")
@RequestScoped
public class ViewMyAccountManagedBean {

    @EJB(name = "OutletSessionBeanLocal")
    private OutletSessionBeanLocal outletSessionBeanLocal;

    @EJB(name = "RetailerSessionBeanLocal")
    private RetailerSessionBeanLocal retailerSessionBeanLocal;
    
    private List<OutletEntity> outletEntities;
    
    private RetailerEntity currentRetailerEntity;
    
    
    public ViewMyAccountManagedBean() 
    {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setCurrentRetailerEntity((RetailerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentRetailerEntity"));
        System.out.println("*********************POST CONSTRUCT OF VIEW RETAILER ACCOUNT********************" + getCurrentRetailerEntity().getRetailerId());
        
        setOutletEntities(outletSessionBeanLocal.retrieveAllOutletsByRetailerId(getCurrentRetailerEntity().getRetailerId()));
    }

    public List<OutletEntity> getOutletEntities() {
        return outletEntities;
    }

    public void setOutletEntities(List<OutletEntity> outletEntities) {
        this.outletEntities = outletEntities;
    }
    
    public RetailerEntity getCurrentRetailerEntity() {
        return currentRetailerEntity;
    }
    
    public void setCurrentRetailerEntity(RetailerEntity currentRetailerEntity) {
        this.currentRetailerEntity = currentRetailerEntity;
    }
}
