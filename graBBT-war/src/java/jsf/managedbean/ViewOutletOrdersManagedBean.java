package jsf.managedbean;

import ejb.session.stateless.OrderSessionBeanLocal;
import entity.OrderEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "viewOutletOrders")
@ViewScoped
public class ViewOutletOrdersManagedBean implements Serializable {

    @EJB
    private OrderSessionBeanLocal orderSessionBean;

    private Long outletId;
    private List orders;
    private OrderEntity orderEntityToView;

    /**
     * Creates a new instance of viewOutletOrders
     */
    public ViewOutletOrdersManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        this.orders = new ArrayList<>();
        System.out.println("viewOutletOrders.java POST CONSTRUCT");
        this.outletId = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("outletId");
        System.out.println(this.outletId);
        this.orders = orderSessionBean.retrieveOrdersByOuletId(outletId);
        System.out.println(this.orders);
    }

    /**
     * @return the orders
     */
    public List<OrderEntity> getOrders() {
        return orders;
    }

    /**
     * @param orders the orders to set
     */
    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
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
     * @return the orderEntityToView
     */
    public OrderEntity getOrderEntityToView() {
        return orderEntityToView;
    }

    /**
     * @param orderEntityToView the orderEntityToView to set
     */
    public void setOrderEntityToView(OrderEntity orderEntityToView) {
        this.orderEntityToView = orderEntityToView;
        System.out.println(this.orderEntityToView);
    }

}
