/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.model;

import entity.PromoEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chloe Tanny
 */
public class RetrieveAllPromosResp {
    
    private List<PromoEntity> promos;
    
    public RetrieveAllPromosResp(){
        this.promos = new ArrayList<>();
    }
    
    public RetrieveAllPromosResp(List<PromoEntity> promos){
        this();
        this.promos = promos;
    }

    public List<PromoEntity> getPromos() {
        return promos;
    }

    public void setPromos(List<PromoEntity> promos) {
        this.promos = promos;
    }
    
    
}
