/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.PromoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chloe Tanny
 */
public class RetrievePromosInCustomerWalletResp implements Serializable {
    
    private List<PromoEntity> customerCurrentPromos;
    private List<Boolean> usedStatus; 

    public RetrievePromosInCustomerWalletResp() {
        customerCurrentPromos = new ArrayList<>();
        usedStatus = new ArrayList<>();
    }

    public RetrievePromosInCustomerWalletResp(List<PromoEntity> customerCurrentPromos, List<Boolean> usedStatus) {
        this();
        this.customerCurrentPromos = customerCurrentPromos;
        this.usedStatus = usedStatus;
    }

    public List<PromoEntity> getCustomerCurrentPromos() {
        return customerCurrentPromos;
    }

    public void setCustomerCurrentPromos(List<PromoEntity> customerCurrentPromos) {
        this.customerCurrentPromos = customerCurrentPromos;
    }

    public List<Boolean> getUsedStatus() {
        return usedStatus;
    }

    public void setUsedStatus(List<Boolean> usedStatus) {
        this.usedStatus = usedStatus;
    }
    
    
    
}
