package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Listing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;

    @Column(nullable = false, length = 64)
    @NotNull
    @Size(max = 64)
    private String name;

    @Column(nullable = false, precision = 11, scale = 2)
    @NotNull
    @DecimalMin("0.00")
    private BigDecimal basePrice;

    @Column(length = 512)
    @Size(max = 512)
    private String description;

    @Column(length = 100)
    @Size(max = 100)
    private String imageSrc;

    @ElementCollection
    @CollectionTable(name = "size_price_map")
    @MapKeyColumn(name = "size")
    @Column(name = "size_price")
    private Map<String, BigDecimal> sizeOptions;

    @ElementCollection
    @CollectionTable(name = "sugarlevel_price_map")
    @MapKeyColumn(name = "sugarlevel")
    @Column(name = "sugarlevel_price")
    private Map<String, BigDecimal> sugarOptions;

    @ElementCollection
    @CollectionTable(name = "icelevel_price_map")
    @MapKeyColumn(name = "icelevel")
    @Column(name = "icelevel_price")
    private Map<String, BigDecimal> iceOptions;

    @ElementCollection
    @CollectionTable(name = "topping_price_map")
    @MapKeyColumn(name = "topping_name")
    @Column(name = "topping_price")
    private Map<String, BigDecimal> toppingOptions;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private OutletEntity outletEntity;
    
    @ManyToOne(optional = true)
    @JoinColumn(nullable = true)
    private CategoryEntity category;

    public Listing() {
        this.toppingOptions = new HashMap<>();
        this.sugarOptions = new HashMap<>();
        this.sizeOptions = new HashMap<>();
        this.iceOptions = new HashMap<>();
    }

    public Listing(String name, BigDecimal basePrice, OutletEntity outletEntity) {
        this();
        this.name = name;
        this.basePrice = basePrice;
        this.outletEntity = outletEntity;
    }

    public Listing(String name, BigDecimal basePrice, OutletEntity outletEntity, String description, String imageSrc) {
        this();
        this.name = name;
        this.basePrice = basePrice;
        this.outletEntity = outletEntity;
        this.description = description;
        this.imageSrc = imageSrc;
    }

    public Listing(String name, BigDecimal basePrice, OutletEntity outletEntity, String imageSrc) {
        this();
        this.name = name;
        this.basePrice = basePrice;
        this.outletEntity = outletEntity;
        this.imageSrc = imageSrc;
    }

    public Listing(String name, BigDecimal basePrice, OutletEntity outletEntity, String imageSrc, Map<String, BigDecimal> sizeOptions, Map<String, BigDecimal> sugarOptions, Map<String, BigDecimal> iceOptions, Map<String, BigDecimal> toppingOptions) {
        this();
        this.name = name;
        this.basePrice = basePrice;
        this.outletEntity = outletEntity;
        this.imageSrc = imageSrc;
        this.sizeOptions = sizeOptions;
        this.sugarOptions = sugarOptions;
        this.iceOptions = iceOptions;
        this.toppingOptions = toppingOptions;
    }

    public Listing(String name, BigDecimal basePrice, OutletEntity outletEntity, String description, String imageSrc, Map<String, BigDecimal> sizeOptions, Map<String, BigDecimal> sugarOptions, Map<String, BigDecimal> iceOptions, Map<String, BigDecimal> toppingOptions) {
        this.name = name;
        this.basePrice = basePrice;
        this.outletEntity = outletEntity;
        this.description = description;
        this.imageSrc = imageSrc;
        this.sizeOptions = sizeOptions;
        this.sugarOptions = sugarOptions;
        this.iceOptions = iceOptions;
        this.toppingOptions = toppingOptions;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listingId != null ? listingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the listingId fields are not set
        if (!(object instanceof Listing)) {
            return false;
        }
        Listing other = (Listing) object;
        if ((this.listingId == null && other.listingId != null) || (this.listingId != null && !this.listingId.equals(other.listingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Listing[ id=" + listingId + " ]";
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the basePrice
     */
    public BigDecimal getBasePrice() {
        return basePrice;
    }

    /**
     * @param basePrice the basePrice to set
     */
    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

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

    /**
     * @return the toppingOptions
     */
    public Map<String, BigDecimal> getToppingOptions() {
        return toppingOptions;
    }

    /**
     * @param toppingOptions the toppingOptions to set
     */
    public void setToppingOptions(Map<String, BigDecimal> toppingOptions) {
        this.toppingOptions = toppingOptions;
    }

    /**
     * @return the sugarOptions
     */
    public Map<String, BigDecimal> getSugarOptions() {
        return sugarOptions;
    }

    /**
     * @param sugarOptions the sugarOptions to set
     */
    public void setSugarOptions(Map<String, BigDecimal> sugarOptions) {
        this.sugarOptions = sugarOptions;
    }

    /**
     * @return the sizeOptions
     */
    public Map<String, BigDecimal> getSizeOptions() {
        return sizeOptions;
    }

    /**
     * @param sizeOptions the sizeOptions to set
     */
    public void setSizeOptions(Map<String, BigDecimal> sizeOptions) {
        this.sizeOptions = sizeOptions;
    }

    /**
     * @return the iceOptions
     */
    public Map<String, BigDecimal> getIceOptions() {
        return iceOptions;
    }

    /**
     * @param iceOptions the iceOptions to set
     */
    public void setIceOptions(Map<String, BigDecimal> iceOptions) {
        this.iceOptions = iceOptions;
    }

    /**
     * @return the outletEntity
     */
    public OutletEntity getOutletEntity() {
        return outletEntity;
    }

    /**
     * @param outletEntity the outletEntity to set
     */
    public void setOutletEntity(OutletEntity outletEntity) {
        this.outletEntity = outletEntity;
    }

    /**
     * @return the category
     */
    public CategoryEntity getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

}
