/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Bryan
 */
@Entity
public class TransactionRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionRecordId;
    
    @Column(nullable = false, length = 128)
    @NotNull
    @Size(max = 128)
    private String deliveryAddress;
    
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal subtotal;
    
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal deliveryFee;
    
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal total;

    public TransactionRecord() {
    }

    public TransactionRecord(String deliveryAddress, BigDecimal subtotal, BigDecimal deliveryFee, BigDecimal total) {
        this();
        this.deliveryAddress = deliveryAddress;
        this.subtotal = subtotal;
        this.deliveryFee = deliveryFee;
        this.total = total;
    }

    public Long getTransactionRecordId() {
        return transactionRecordId;
    }

    public void setTransactionRecordId(Long transactionRecordId) {
        this.transactionRecordId = transactionRecordId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionRecordId != null ? transactionRecordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the transactionRecordId fields are not set
        if (!(object instanceof TransactionRecord)) {
            return false;
        }
        TransactionRecord other = (TransactionRecord) object;
        if ((this.transactionRecordId == null && other.transactionRecordId != null) || (this.transactionRecordId != null && !this.transactionRecordId.equals(other.transactionRecordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TransactionRecord[ id=" + transactionRecordId + " ]";
    }

    /**
     * @return the deliveryAddress
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * @param deliveryAddress the deliveryAddress to set
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * @return the subtotal
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the deliveryFee
     */
    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    /**
     * @param deliveryFee the deliveryFee to set
     */
    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
}
