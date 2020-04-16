/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.PERSIST;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
    private Boolean isActive;

    @Column(nullable = false)
    @NotNull
    @Min(0000)
    @Max(2359)
    private Integer openingHour; //store in 24-hr format

    @Column(nullable = false, length = 4)
    @NotNull
    @Min(0000)
    @Max(2359)
    private Integer closingHour; //store in 24-hr format

    @Column(nullable = false)
    @Max(5)
    @Min(0)
    private Double outletRating;

    @Column(nullable = false)
    @Min(0)
    private Integer ratingCount; //stores total number of ratings made for this outlet

    //constraints to be added
    @Column(nullable = false)
    @NotNull
    private Double locationLatitude;
    //constraints to be added
    @Column(nullable = false)
    @NotNull
    private Double locationLongitude;

    //nullable FOR NOW so as not to break anything
    @Column(nullable = false)
    @NotNull
    private String imageSrc;

    //constraints to be added; to confirm format of storing revenue reports
    private Double outletRevenueDaily;
    private Double outletRevenueMonthly;
    private Double outletRevenueOverall;

    //changed optional to false
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private RetailerEntity retailerEntity;

    @OneToMany(mappedBy = "outletEntity", cascade = CascadeType.PERSIST)
    private List<Listing> listings;

//    @OneToMany(mappedBy = "outlet", cascade=CascadeType.PERSIST)
//    private List<CategoryEntity> categories;
    public OutletEntity() {
        this.outletRating = 5.0;
        this.ratingCount = 0;
        this.outletRevenueDaily = 0.0;
        this.outletRevenueMonthly = 0.0;
        this.outletRevenueOverall = 0.0;

        this.isActive = true; //by default

        this.listings = new ArrayList<>();
//        this.categories = new ArrayList<>();
    }

    public OutletEntity(String outletName, Integer openingHour, Integer closingHour, Double locationLatitude, Double locationLongitude) {
        this();
        this.outletName = outletName;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }

    public OutletEntity(String outletName, Integer openingHour, Integer closingHour, Double locationLatitude, Double locationLongitude, String imageSrc) {
        this();
        this.outletName = outletName;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
        this.imageSrc = imageSrc;
    }

    public void addListing(Listing listing) {
        if (listing != null) {
            if (!this.listings.contains(listing)) {
                this.listings.add(listing);

                if (!listing.getOutletEntity().getOutletId().equals(outletId)) {
                    listing.setOutletEntity(this);
                }
            }
        }
    }

    public void removeListing(Listing listing) {
        if (listing != null) {
            if (this.listings.contains(listing)) {
                this.listings.remove(listing);
            }
        }
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    /**
     * @return the listings
     */
    public List<Listing> getListings() {
        return listings;
    }

    /**
     * @param listings the listings to set
     */
    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

//    /**
//     * @return the categories
//     */
//    public List<CategoryEntity> getCategories() {
//        return categories;
//    }
//
//    /**
//     * @param categories the categories to set
//     */
//    public void setCategories(List<CategoryEntity> categories) {
//        this.categories = categories;
//    }
    /**
     * @return the imageSrc
     */
    public String getImageSrc() {
        return imageSrc;
    }

    /**
     * @param imageSrc the imageSrc to set
     */
    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

}
