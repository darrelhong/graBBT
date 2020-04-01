
package jsf.managedbean;

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

    public RetailerEntity getLoggedInRetailer() {
        return loggedInRetailer;
    }

    public void setLoggedInRetailer(RetailerEntity loggedInRetailer) {
        this.loggedInRetailer = loggedInRetailer;
    }

    public List<OutletEntity> getOutletEntities() {
        return outletEntities;
    }

    public void setOutletEntities(List<OutletEntity> outletEntities) {
        this.outletEntities = outletEntities;
    }

    @EJB(name = "RetailerSessionBeanLocal")
    private RetailerSessionBeanLocal retailerSessionBeanLocal;

    private RetailerEntity loggedInRetailer;
    private List<OutletEntity> outletEntities;
    
    public ViewMyAccountManagedBean() 
    {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        setLoggedInRetailer((RetailerEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentRetailerEntity"));
        setOutletEntities(getLoggedInRetailer().getOutletEntities());
    }
}
