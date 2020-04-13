package ws.restful.model;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutReq {

    private Long customerId;
    private Long outletId;
    private Integer totalLineItem;
    private Integer totalQuantity;
    private BigDecimal totalAmount;
    private List<CheckoutItem> checkoutItems;
    private String address;
    private String addressDetails;
    private String ccNum;
    private String deliveryNote;

    public CheckoutReq() {
    }
    
    @Override
    public String toString() {
        return "outletId: " + outletId + " lineItem: " + totalLineItem + " totalQty: "
                + totalQuantity + " totalAmount: " + totalAmount + " address: " +
                address + " addressDetails: " + addressDetails + " ccNum: " + ccNum +
                " deliveryNote: " + deliveryNote;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public String getCcNum() {
        return ccNum;
    }

    public void setCcNum(String ccNum) {
        this.ccNum = ccNum;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public List<CheckoutItem> getCheckoutItems() {
        return checkoutItems;
    }

    public void setCheckoutItems(List<CheckoutItem> checkoutItems) {
        this.checkoutItems = checkoutItems;
    }

    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public Integer getTotalLineItem() {
        return totalLineItem;
    }

    public void setTotalLineItem(Integer totalLineItem) {
        this.totalLineItem = totalLineItem;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
}
