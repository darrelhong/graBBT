/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    
    @Col
    private String itemName;
    
    private BigDecimal price;
    
    private Integer quantity;
    
    private ArrayList<String> itemOptions;

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
    
}
