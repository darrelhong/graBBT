package ejb.session.stateless;

import entity.Customer;
import entity.OrderEntity;
import entity.OrderLineItem;
import entity.OutletEntity;
import entity.PromoEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CancelOrderException;
import util.exception.CheckoutError;
import util.exception.CustomerNotFoundException;
import util.exception.GiveOutletRatingException;
import util.exception.OrderNotFoundException;
import util.exception.OutletNotFoundException;
import util.exception.PromoNotClaimedByCustomer;
import util.exception.PromoNotFoundException;
import util.exception.PromoUsedAlreadyException;

@Stateless
public class OrderSessionBean implements OrderSessionBeanLocal {

    @EJB
    private PromoSessionBeanLocal promoSessionBean;

    @EJB
    private OutletSessionBeanLocal outletSessionBean;

    @EJB
    private CustomerSessionBeanLocal customerSessionBean;

    @PersistenceContext(unitName = "graBBT-ejbPU")
    private EntityManager em;

    private final ValidatorFactory validatorFactory;
    private final Validator validator;

    public OrderSessionBean() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Override
    public OrderEntity checkout(Long customerId, Long outletId, Integer totalLineItem,
            Integer totalQuantity, BigDecimal totalAmount, String address, String addressDetails,
            String ccNum, String deliveryNote, List<OrderLineItem> orderLineItems, PromoEntity promo) throws CheckoutError {
        try {
            Customer customer = customerSessionBean.retrieveCustomerById(customerId);
            OutletEntity outlet = outletSessionBean.retrieveOutletByOutletId(outletId);
            OrderEntity oe = new OrderEntity(totalLineItem, totalQuantity, totalAmount, new Date(),
                    customer, outlet, address, addressDetails, deliveryNote, ccNum);
    
            if (promo != null) {
                /*
                 * darrel - To add use promo code here
                 * chloe - done
                 */
                PromoEntity pr = promoSessionBean.retrievePromoById(promo.getPromoId());
                PromoEntity updatedPromoEntity = promoSessionBean.customerUsesPromo(customerId, pr.getPromoId());
                oe.setPromo(updatedPromoEntity);
                oe.setTotalAmountAftPromo(oe.getTotalAmount().subtract(updatedPromoEntity.getValue()));
                
            } else {
                oe.setPromo(null);
                oe.setTotalAmountAftPromo(oe.getTotalAmount());
            }

            Set<ConstraintViolation<OrderEntity>> constraintViolations = validator.validate(oe);

            if (constraintViolations.isEmpty()) {
                
                //add bb-points
                int pointsConverted = (int) Math.floor(oe.getTotalAmountAftPromo().doubleValue());
                System.out.println("customer earned " + pointsConverted + "bb points from transaction");
                
                int currentPoints = oe.getCustomer().getBbPoints();
                oe.getCustomer().setBbPoints(currentPoints + pointsConverted);
                
                em.persist(oe);
            } else {
                throw new CheckoutError(prepareInputDataValidationErrorsMessage(constraintViolations));
            }
            for (OrderLineItem oli : orderLineItems) {
                em.persist(oli);
                oe.getOrderLineItems().add(oli);
            }

            em.flush();
            return oe;

        } catch (CustomerNotFoundException | OutletNotFoundException | PromoNotFoundException | PromoNotClaimedByCustomer | PromoUsedAlreadyException ex) {
            throw new CheckoutError(ex.getMessage());
        }
    }

    @Override
    public OrderEntity retrieveOrderByOrderId(Long orderId) throws OrderNotFoundException {
        OrderEntity oe = em.find(OrderEntity.class, orderId);

        if (oe != null) {
            return oe;
        } else {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found!");
        }
    }

    @Override
    public List<OrderEntity> retrieveOrderHistoryByCustomerId(Long customerId) {
        Query q = em.createQuery("SELECT o FROM OrderEntity o WHERE o.customer.customerId = :inCustomerId ORDER BY o.transactionDateTime DESC");
        q.setParameter("inCustomerId", customerId);
        return q.getResultList();
    }

    @Override
    public List<OrderEntity> retrieveOrdersByOuletId(Long outletId) {
        Query q = em.createQuery("SELECT o FROM OrderEntity o WHERE o.outlet.outletId = :inOutletId ORDER BY o.transactionDateTime DESC");
        q.setParameter("inOutletId", outletId);
        return q.getResultList();
    }

    @Override
    public OrderEntity cancelOrder(Long orderId) throws CancelOrderException {
        try {
            Query q = em.createQuery("SELECT o FROM OrderEntity o WHERE o.orderId = :inOrderId");
            q.setParameter("inOrderId", orderId);

            OrderEntity oe = (OrderEntity) q.getSingleResult();
            oe.setCancelled(true);
            return oe;

        } catch (NonUniqueResultException | NoResultException ex) {
            throw new CancelOrderException(ex.getMessage());
        }
    }
    
    public void giveOutletRating(Long orderId, Long ratingValue) throws GiveOutletRatingException {
        try {
            OrderEntity oe = retrieveOrderByOrderId(orderId);
            OutletEntity outlet = oe.getOutlet();
            Double newOutletRating = (outlet.getOutletRating() * outlet.getRatingCount() + ratingValue) / (outlet.getRatingCount() + 1);
            outlet.setOutletRating(newOutletRating);
            outlet.setRatingCount(outlet.getRatingCount() + 1);
            oe.setOutletRatingGiven(true);
        } catch (OrderNotFoundException ex) {
            throw new GiveOutletRatingException(ex.getMessage());
        }
    }

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<OrderEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
