/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Bryan
 */
@Entity
public class OrderLineItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderLineItemId;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Listing listing;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer quantity;

    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal subTotal;

    @ElementCollection
    private List<String> itemOptions;

    public OrderLineItem() {
        this.itemOptions = new ArrayList<>();
    }

    public OrderLineItem(Listing listing, Integer quantity, BigDecimal subTotal, List<String> itemOptions) {
        this();
        this.listing = listing;
        this.quantity = quantity;
        this.subTotal = subTotal;
        this.itemOptions = itemOptions;
    }

    public Long getOrderLineItemId() {
        return orderLineItemId;
    }

    public void setOrderLineItemId(Long orderLineItemId) {
        this.orderLineItemId = orderLineItemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderLineItemId != null ? orderLineItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the orderLineItemId fields are not set
        if (!(object instanceof OrderLineItem)) {
            return false;
        }
        OrderLineItem other = (OrderLineItem) object;
        if ((this.orderLineItemId == null && other.orderLineItemId != null) || (this.orderLineItemId != null && !this.orderLineItemId.equals(other.orderLineItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderLineItem[ id=" + orderLineItemId + " ]";
    }

    /**
     * @return the quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the itemOptions
     */
    public List<String> getItemOptions() {
        return itemOptions;
    }

    /**
     * @param itemOptions the itemOptions to set
     */
    public void setItemOptions(List<String> itemOptions) {
        this.itemOptions = itemOptions;
    }

    /**
     * @return the listing
     */
    public Listing getListing() {
        return listing;
    }

    /**
     * @param listing the listing to set
     */
    public void setListing(Listing listing) {
        this.listing = listing;
    }

    /**
     * @return the subTotal
     */
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    /**
     * @param subTotal the subTotal to set
     */
    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

}
