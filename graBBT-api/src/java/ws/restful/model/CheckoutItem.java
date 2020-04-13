package ws.restful.model;

import java.math.BigDecimal;
import java.util.List;

public class CheckoutItem {

    private Long listingId;
    private List<String> selectedOptions;
    private Integer qty;
    private BigDecimal subtotal;

    public CheckoutItem() {
    }
    
    @Override
    public String toString() {
        return " listingId: " + listingId + " selectedOptions: " + selectedOptions +
                " qty: " + qty + " subtotal: " + subtotal;
    }

    public Long getListingId() {
        return listingId;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public List<String> getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

}
