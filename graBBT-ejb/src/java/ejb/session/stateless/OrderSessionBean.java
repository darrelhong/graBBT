package ejb.session.stateless;

import entity.Customer;
import entity.OrderEntity;
import entity.OrderLineItem;
import entity.OutletEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import util.exception.CheckoutError;
import util.exception.CustomerNotFoundException;
import util.exception.OutletNotFoundException;

@Stateless
public class OrderSessionBean implements OrderSessionBeanLocal {

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
            String ccNum, String deliveryNote, List<OrderLineItem> orderLineItems) throws CheckoutError {
        try {
            Customer customer = customerSessionBean.retrieveCustomerById(customerId);
            OutletEntity outlet = outletSessionBean.retrieveOutletByOutletId(outletId);
            OrderEntity oe = new OrderEntity(totalLineItem, totalQuantity, totalAmount, new Date(),
                    customer, outlet, address, addressDetails, deliveryNote, ccNum);

            Set<ConstraintViolation<OrderEntity>> constraintViolations = validator.validate(oe);

            if (constraintViolations.isEmpty()) {

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

        } catch (CustomerNotFoundException | OutletNotFoundException ex) {
            throw new CheckoutError(ex.getMessage());
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

    private String prepareInputDataValidationErrorsMessage(Set<ConstraintViolation<OrderEntity>> constraintViolations) {
        String msg = "Input data validation error!:";

        for (ConstraintViolation constraintViolation : constraintViolations) {
            msg += "\n\t" + constraintViolation.getPropertyPath() + " - " + constraintViolation.getInvalidValue() + "; " + constraintViolation.getMessage();
        }

        return msg;
    }
}
