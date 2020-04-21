/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.resources;

import ejb.session.stateless.PromoSessionBeanLocal;
import entity.PromoEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import util.exception.PromoClaimedByCustomerAlreadyException;
import util.exception.PromoNotFoundException;
import ws.restful.model.ErrorResp;
import ws.restful.model.RetrieveAllPromosResp;
import ws.restful.model.RetrievePromosInCustomerWalletResp;

/**
 *
 * @author Chloe Tanny
 */
@Path("Promo")
public class PromoResource {
    
    private PromoSessionBeanLocal promoSessionBeanLocal = lookupPromoSessionBeanLocal();
    
    @Context
    private UriInfo context;
    
    public PromoResource(){
        
    }
    
    @Path("retrieveAllPromos")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllAvailablePromos()
    {
        List<PromoEntity> availablePromos = promoSessionBeanLocal.retrieveAllActivePromos();
        
        for (PromoEntity promo: availablePromos){
            promo.setCustomerUsedStatus(null);
            promo.setMaxLimit(null);
        }
        
        return Response.status(Status.OK).entity(new RetrieveAllPromosResp(availablePromos)).build();
    }
    
    @Path("retrievePromosByCustomerId/{customerId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePromosInCustomerWallet(@PathParam("customerId") Long customerId)
    {
        List<PromoEntity> customerWalletPromos = promoSessionBeanLocal.retrievePromosInCustomerWallet(customerId);
        List<Boolean> usedStatus = new ArrayList<>();
        
        for (PromoEntity promo: customerWalletPromos){
            promo.setMaxLimit(null);
            usedStatus.add(promo.getCustomerUsedStatus().get(customerId)); //putting status into a separate list
            promo.setCustomerUsedStatus(null);
        }
        
        return Response.status(Status.OK).entity(new RetrievePromosInCustomerWalletResp(customerWalletPromos, usedStatus)).build();
    }
    
    @Path("claimPromoIntoWallet/{customerId}{promoId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response claimPromoIntoWallet(@PathParam("customerId") Long customerId, @PathParam("promoId") Long promoId)
    {
        System.out.println("PromoResource: claimPromoIntoWallet");
        
        try {
            promoSessionBeanLocal.customerClaimsPromo(customerId, promoId);
            
            //return back new list of claimed promos
            return this.retrievePromosInCustomerWallet(customerId);
        }
        catch (PromoNotFoundException | PromoClaimedByCustomerAlreadyException ex){
            ErrorResp errorResp = new ErrorResp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorResp).build();
        }
    }
    
    private PromoSessionBeanLocal lookupPromoSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PromoSessionBeanLocal) c.lookup("java:global/graBBT/graBBT-ejb/PromoSessionBean!ejb.session.stateless.PromoSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
