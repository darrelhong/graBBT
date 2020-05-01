package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer totalLineItem;

    @Column(nullable = false)
    @NotNull
    @Min(1)
    private Integer totalQuantity;

    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalAmount;

    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal totalAmountAftPromo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    private Date transactionDateTime;

    @OneToMany
    private List<OrderLineItem> orderLineItems;

    @Column(nullable = false)
    @NotNull
    private Boolean cancelled;

    // nullable for now
    @Column(nullable = true)
    private String address;

    @Column(nullable = true)
    private String addressDetails;

    @Column(nullable = true)
    private String deliveryNote;

    @Column(nullable = true)
    private String ccNum;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Customer customer;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private OutletEntity outlet;

    @OneToOne(optional = true)
    @JoinColumn(nullable = true)
    private PromoEntity promo;

    @Column(nullable = false)
    @NotNull
    private Boolean outletRatingGiven;

    public OrderEntity() {
        this.orderLineItems = new ArrayList<>();
        this.cancelled = false;
        this.promo = null;
        this.outletRatingGiven = false;
    }

    public OrderEntity(Integer totalLineItem, Integer totalQuantity, BigDecimal totalAmount,
            Date transactionDateTime, Customer customer, OutletEntity outlet, String address,
            String addressDetails, String deliveryNote, String ccNum) {
        this();
        this.totalLineItem = totalLineItem;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.transactionDateTime = transactionDateTime;
        this.customer = customer;
        this.outlet = outlet;
        this.address = address;
        this.addressDetails = addressDetails;
        this.deliveryNote = deliveryNote;
        this.ccNum = ccNum;
    }

    public OrderEntity(Integer totalLineItem, Integer totalQuantity, BigDecimal totalAmount,
            Date transactionDateTime, Customer customer, OutletEntity outlet,
            List<OrderLineItem> orderLineItems) {
        this();
        this.totalLineItem = totalLineItem;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.transactionDateTime = transactionDateTime;
        this.customer = customer;
        this.outlet = outlet;
        this.orderLineItems = orderLineItems;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the orderId fields are not set
        if (!(object instanceof OrderEntity)) {
            return false;
        }
        OrderEntity other = (OrderEntity) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderEntity[ id=" + orderId + " ]";
    }

    /**
     * @return the totalLineItem
     */
    public Integer getTotalLineItem() {
        return totalLineItem;
    }

    /**
     * @param totalLineItem the totalLineItem to set
     */
    public void setTotalLineItem(Integer totalLineItem) {
        this.totalLineItem = totalLineItem;
    }

    /**
     * @return the totalQuantity
     */
    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * @param totalQuantity the totalQuantity to set
     */
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    /**
     * @return the totalAmount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the transactionDateTime
     */
    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    /**
     * @param transactionDateTime the transactionDateTime to set
     */
    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    /**
     * @return the orderLineItems
     */
    public List<OrderLineItem> getOrderLineItems() {
        return orderLineItems;
    }

    /**
     * @param orderLineItems the orderLineItems to set
     */
    public void setOrderLineItems(List<OrderLineItem> orderLineItems) {
        this.orderLineItems = orderLineItems;
    }

    /**
     * @return the cancelled
     */
    public Boolean getCancelled() {
        return cancelled;
    }

    /**
     * @param cancelled the cancelled to set
     */
    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public OutletEntity getOutlet() {
        return outlet;
    }

    public void setOutlet(OutletEntity outlet) {
        this.outlet = outlet;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the addressDetails
     */
    public String getAddressDetails() {
        return addressDetails;
    }

    /**
     * @param addressDetails the addressDetails to set
     */
    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    /**
     * @return the deliveryNote
     */
    public String getDeliveryNote() {
        return deliveryNote;
    }

    /**
     * @param deliveryNote the deliveryNote to set
     */
    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    /**
     * @return the ccNum
     */
    public String getCcNum() {
        return ccNum;
    }

    /**
     * @param ccNum the ccNum to set
     */
    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    public BigDecimal getTotalAmountAftPromo() {
        return totalAmountAftPromo;
    }

    public void setTotalAmountAftPromo(BigDecimal totalAmountAftPromo) {
        this.totalAmountAftPromo = totalAmountAftPromo;
    }

    public PromoEntity getPromo() {
        return promo;
    }

    public void setPromo(PromoEntity promo) {
        this.promo = promo;
    }

    public Boolean getOutletRatingGiven() {
        return outletRatingGiven;
    }

    public void setOutletRatingGiven(Boolean outletRatingGiven) {
        this.outletRatingGiven = outletRatingGiven;
    }

}
