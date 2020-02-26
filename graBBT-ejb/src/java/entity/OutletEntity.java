/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Chloe Tanny
 */
@Entity
public class OutletEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outletId;
    
    @Column(nullable = false, unique = true, length = 64)
    @NotNull
    @Size(max = 64)
    private String outletName;
    
    @Column(nullable = false)
    @NotNull
    @Min(0000)
    @Max(2359)
    private Integer openingHour; //store in 24-hr format
    
    @Column(nullable = false)
    @NotNull
    @Min(0000)
    @Max(2359)
    private Integer closingHour; //store in 24-hr format
    
    @Column(nullable = false)
    @Max(5)
    @Min(0)
    private Double outletRating = 5.0; 
    
    @Column(nullable = false)
    @Min(0)
    private Integer ratingCount = 0; //stores total number of ratings made for this outlet

    //constraints to be added
    private Double locationLatitude;
    //constraints to be added
    private Double locationLongitude;
    
    //constraints to be added; to confirm format of storing revenue reports
    private Double outletRevenueDaily = 0.0;
    private Double outletRevenueMonthly = 0.0;
    private Double outletRevenueOverall = 0.0;
    
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private RetailerEntity retailerEntity;
    
    public OutletEntity(){
    }

    public OutletEntity(String outletName, Integer openingHour, Integer closingHour, Double locationLatitude, Double locationLongitude) {
        this.outletName = outletName;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }
   
    
    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (outletId != null ? outletId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the outletId fields are not set
        if (!(object instanceof OutletEntity)) {
            return false;
        }
        OutletEntity other = (OutletEntity) object;
        if ((this.outletId == null && other.outletId != null) || (this.outletId != null && !this.outletId.equals(other.outletId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OutletEntity[ id=" + outletId + " ]";
    }

    /**
     * @return the outletName
     */
    public String getOutletName() {
        return outletName;
    }

    /**
     * @param outletName the outletName to set
     */
    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    /**
     * @return the openingHour
     */
    public Integer getOpeningHour() {
        return openingHour;
    }

    /**
     * @param openingHour the openingHour to set
     */
    public void setOpeningHour(Integer openingHour) {
        this.openingHour = openingHour;
    }

    /**
     * @return the closingHour
     */
    public Integer getClosingHour() {
        return closingHour;
    }

    /**
     * @param closingHour the closingHour to set
     */
    public void setClosingHour(Integer closingHour) {
        this.closingHour = closingHour;
    }

    /**
     * @return the outletRating
     */
    public Double getOutletRating() {
        return outletRating;
    }

    /**
     * @param outletRating the outletRating to set
     */
    public void setOutletRating(Double outletRating) {
        this.outletRating = outletRating;
    }

    /**
     * @return the ratingCount
     */
    public Integer getRatingCount() {
        return ratingCount;
    }

    /**
     * @param ratingCount the ratingCount to set
     */
    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    /**
     * @return the locationLatitude
     */
    public Double getLocationLatitude() {
        return locationLatitude;
    }

    /**
     * @param locationLatitude the locationLatitude to set
     */
    public void setLocationLatitude(Double locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    /**
     * @return the locationLongitude
     */
    public Double getLocationLongitude() {
        return locationLongitude;
    }

    /**
     * @param locationLongitude the locationLongitude to set
     */
    public void setLocationLongitude(Double locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    /**
     * @return the outletRevenueDaily
     */
    public Double getOutletRevenueDaily() {
        return outletRevenueDaily;
    }

    /**
     * @param outletRevenueDaily the outletRevenueDaily to set
     */
    public void setOutletRevenueDaily(Double outletRevenueDaily) {
        this.outletRevenueDaily = outletRevenueDaily;
    }

    /**
     * @return the outletRevenueMonthly
     */
    public Double getOutletRevenueMonthly() {
        return outletRevenueMonthly;
    }

    /**
     * @param outletRevenueMonthly the outletRevenueMonthly to set
     */
    public void setOutletRevenueMonthly(Double outletRevenueMonthly) {
        this.outletRevenueMonthly = outletRevenueMonthly;
    }

    /**
     * @return the outletRevenueOverall
     */
    public Double getOutletRevenueOverall() {
        return outletRevenueOverall;
    }

    /**
     * @param outletRevenueOverall the outletRevenueOverall to set
     */
    public void setOutletRevenueOverall(Double outletRevenueOverall) {
        this.outletRevenueOverall = outletRevenueOverall;
    }

    /**
     * @return the retailerEntity
     */
    public RetailerEntity getRetailerEntity() {
        return retailerEntity;
    }

    /**
     * @param retailerEntity the retailerEntity to set
     */
    public void setRetailerEntity(RetailerEntity retailerEntity) {
        this.retailerEntity = retailerEntity;
    }
    
}
