package ejb.session.stateless;

import entity.OrderEntity;
import entity.OrderLineItem;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import util.exception.CheckoutError;

/**
 *
 * @author darre
 */
@Local
public interface OrderSessionBeanLocal {

    public OrderEntity checkout(Long customerId, Long outletId, Integer totalLineItem, Integer totalQuantity, BigDecimal totalAmount, String address, String addressDetails, String ccNum, String deliveryNote, List<OrderLineItem> orderLineItems) throws CheckoutError;
    
}