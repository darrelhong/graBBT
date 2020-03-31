package ws.restful.model;

import entity.Listing;
import java.util.List;

public class RetrieveListingsbyOutletResp {
    private List<Listing> listings;
    
    public RetrieveListingsbyOutletResp() {}
    
    public RetrieveListingsbyOutletResp(List<Listing> listings) {
        this.listings = listings;
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
}
