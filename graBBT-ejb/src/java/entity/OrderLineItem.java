/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    
    @Column(nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    private String itemName;
    
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal price;
    
    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer quantity;
    
    private List<String> itemOptions;

    public OrderLineItem() {
    }

    public OrderLineItem(String itemName, BigDecimal price, Integer quantity, List<String> itemOptions) {
        this();
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
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
     * @return the itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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
    
}
