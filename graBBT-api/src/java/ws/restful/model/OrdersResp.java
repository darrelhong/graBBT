package ws.restful.model;

import entity.OrderEntity;
import java.beans.Transient;
import java.util.List;

/**
 *
 * @author Chloe Tanny
 */
public class OrdersResp {
   
   private List<String> outletNames; 
   private List<String> dates;
   private List<OrderEntity> orders; 
    
   public OrdersResp()
   {
   }
   
   public OrdersResp(List<OrderEntity> orders, List<String> outletNames, List<String> dates)
   {
       this.orders = orders;
       this.outletNames = outletNames;
       this.dates = dates;
   }

    public List<OrderEntity> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderEntity> orders) {
        this.orders = orders;
    }

    public List<String> getOutletNames() {
        return outletNames;
    }

    public void setOutletNames(List<String> outletNames) {
        this.outletNames = outletNames;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
   
}
