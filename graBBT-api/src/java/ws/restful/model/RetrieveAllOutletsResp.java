package ws.restful.model;

import entity.OutletEntity;
import java.util.List;

public class RetrieveAllOutletsResp {

    private List<OutletEntity> outletEntites;

    public RetrieveAllOutletsResp() {}
    
    public RetrieveAllOutletsResp(List<OutletEntity> outletEntities) {
        this.outletEntites = outletEntities;
    }

    /**
     * @return the outletEntites
     */
    public List<OutletEntity> getOutletEntites() {
        return outletEntites;
    }

    /**
     * @param outletEntites the outletEntites to set
     */
    public void setOutletEntites(List<OutletEntity> outletEntites) {
        this.outletEntites = outletEntites;
    }
    
    
}
