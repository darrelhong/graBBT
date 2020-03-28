package ws.restful.resources;

import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OutletSessionBeanLocal;
import entity.OutletEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.OutletNotFoundException;
import ws.restful.model.ErrorResp;
import ws.restful.model.RetrieveAllOutletsResp;

/**
 * REST Web Service
 *
 * @author darre
 */
@Path("Listing")
public class ListingResource {

    ListingSessionBeanLocal listingSessionBeanLocal = lookupListingSessionBeanLocal();
    OutletSessionBeanLocal outletSessionBeanLocal = lookupOutletSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ListingResource
     */
    public ListingResource() {
    }

    @Path("retrieveOutlet/{outletId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveOutlet(@PathParam("outletId") Long outletId) {
        try {
            System.out.println("HHHHHHHHHHHHEEEEEEEEEEEEEEELLLLLLLLLLLLLLLLLLLLLLLLLLLLLLOOOOOOOO");
            OutletEntity outlet = outletSessionBeanLocal.retrieveOutletByOutletId(outletId);
            outlet.getListings().clear();
            outlet.setRetailerEntity(null);

            return Response.status(Status.OK).entity(outlet).build();
        } catch (OutletNotFoundException ex) {
            ErrorResp errorResp = new ErrorResp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorResp).build();
        }
    }

    @Path("retrieveAllOutlets")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllOutlets() {

        List<OutletEntity> outletEntities = outletSessionBeanLocal.retrieveAllOutlets();
        for (OutletEntity outlet : outletEntities) {
            outlet.getListings().clear();
            outlet.setRetailerEntity(null);
        }

        return Response.status(Status.OK).entity(new RetrieveAllOutletsResp(outletEntities)).build();
    }

    private OutletSessionBeanLocal lookupOutletSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OutletSessionBeanLocal) c.lookup("java:global/graBBT/graBBT-ejb/OutletSessionBean!ejb.session.stateless.OutletSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ListingSessionBeanLocal lookupListingSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ListingSessionBeanLocal) c.lookup("java:global/graBBT/graBBT-ejb/ListingSessionBean!ejb.session.stateless.ListingSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
