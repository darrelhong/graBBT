/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.OrderEntity;
import java.math.BigDecimal;

/**
 *
 * @author Chloe Tanny
 */
public class OrderDetailsResp {
    
    private OrderEntity orderEntity;
    private String promoCode;
    private BigDecimal promoValue;
    
    public OrderDetailsResp () 
    {
        this.promoCode = null;
        this.promoValue = null;     
    }

    public OrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public BigDecimal getPromoValue() {
        return promoValue;
    }

    public void setPromoValue(BigDecimal promoValue) {
        this.promoValue = promoValue;
    }
    
    
}
