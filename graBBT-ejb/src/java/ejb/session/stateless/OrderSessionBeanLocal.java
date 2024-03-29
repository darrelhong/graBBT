package ejb.session.stateless;

import entity.OrderEntity;
import entity.OrderLineItem;
import entity.PromoEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;
import util.exception.CancelOrderException;
import util.exception.CheckoutError;
import util.exception.GiveOutletRatingException;
import util.exception.OrderNotFoundException;

/**
 *
 * @author darre
 */
@Local
public interface OrderSessionBeanLocal {

    public List<OrderEntity> retrieveOrderHistoryByCustomerId(Long customerId);

    public List<OrderEntity> retrieveOrdersByOuletId(Long outletId);

    public OrderEntity cancelOrder(Long orderId) throws CancelOrderException;

    public OrderEntity retrieveOrderByOrderId(Long orderId) throws OrderNotFoundException;

    public OrderEntity checkout(Long customerId, Long outletId, Integer totalLineItem, Integer totalQuantity, BigDecimal totalAmount, String address, String addressDetails, String ccNum, String deliveryNote, List<OrderLineItem> orderLineItems, PromoEntity promo) throws CheckoutError;

    public void giveOutletRating(Long orderId, Long ratingValue) throws GiveOutletRatingException;
}
