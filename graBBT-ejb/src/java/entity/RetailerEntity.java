package entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import util.security.CryptographicHelper;

@Entity
public class RetailerEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long retailerId;

    @Column(nullable = false, unique = true, length = 64)
    @NotNull
    @Size(max = 64)
    private String retailerName;

    @Column(nullable = false, unique = true, length = 32)
    @NotNull
    @Size(min = 4, max = 32)
    private String username;

    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String password;

    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;

    public RetailerEntity() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
    }

    public RetailerEntity(String retailerName, String username, String password) {
        this();
        this.retailerName = retailerName;
        this.username = username;

        setPassword(password);
    }
    
      /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        if (password != null) {
            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        } else {
            this.password = null;
        }
    }

    public Long getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(Long retailerId) {
        this.retailerId = retailerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getRetailerId() != null ? getRetailerId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the retailerId fields are not set
        if (!(object instanceof RetailerEntity)) {
            return false;
        }
        RetailerEntity other = (RetailerEntity) object;
        if ((this.getRetailerId() == null && other.getRetailerId() != null) || (this.getRetailerId() != null && !this.retailerId.equals(other.retailerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Retailer[ id=" + getRetailerId() + " ]";
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * @return the retailerName
     */
    public String getRetailerName() {
        return retailerName;
    }

    /**
     * @param retailerName the retailerName to set
     */
    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

}
