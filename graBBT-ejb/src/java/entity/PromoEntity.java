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
 * @author Chloe Tanny
 */
@Entity
public class PromoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long promoId;
    
    @Column(nullable = false, length = 10)
    @NotNull
    @Size(max = 10, min= 6)
    private String promoCode;
    
    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal value; //using single value for now. To expand into percentage value option etc.
    
    @Column(nullable = false)
    @NotNull
    private boolean isActive;
    
    //to implement link to outlets that it applies to
    //refer to tagentity for implementation
    //private List<Integer> outletIds;

    public PromoEntity() {
    }

    public PromoEntity(String promoCode, BigDecimal value) {
        this.isActive = true;
        this.promoCode = promoCode;
        this.value = value;
    }
    
   
    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promoId != null ? promoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the promoId fields are not set
        if (!(object instanceof PromoEntity)) {
            return false;
        }
        PromoEntity other = (PromoEntity) object;
        if ((this.promoId == null && other.promoId != null) || (this.promoId != null && !this.promoId.equals(other.promoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PromoEntity[ id=" + promoId + " ]";
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
}
