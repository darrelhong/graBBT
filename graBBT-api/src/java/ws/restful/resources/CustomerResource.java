package ws.restful.resources;

import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.ListingSessionBeanLocal;
import ejb.session.stateless.OrderSessionBeanLocal;
import entity.Customer;
import entity.Listing;
import entity.OrderEntity;
import entity.OrderLineItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.CheckoutError;
import util.exception.CustomerNotFoundException;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.ListingNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateCustomerException;
import ws.restful.model.CheckoutItem;
import ws.restful.model.CheckoutReq;
import ws.restful.model.CheckoutResp;
import ws.restful.model.CreateCustomerReq;
import ws.restful.model.CreateCustomerResp;
import ws.restful.model.CustomerLoginResp;
import ws.restful.model.ErrorResp;
import ws.restful.model.UpdatedCustomerReq;

@Path("Customer")
public class CustomerResource {

    OrderSessionBeanLocal orderSessionBean = lookupOrderSessionBeanLocal();

    ListingSessionBeanLocal listingSessionBean = lookupListingSessionBeanLocal();

    private CustomerSessionBeanLocal customerSessionBeanLocal = lookupCustomerSessionBeanLocal();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CustomerResource
     */
    public CustomerResource() {
    }

    @Path("customerLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerLogin(@QueryParam("username") String username,
            @QueryParam("password") String password) {
        try {
            Customer customer = customerSessionBeanLocal.customerLogin(username, password);
            System.out.println("************API Request Customer Login: " + username + " " + password + " *********************");

            customer.setPassword(null);
            customer.setSalt(null);

            return Response.status(Status.OK).entity(new CustomerLoginResp(customer)).build();
        } catch (InvalidLoginCredentialException ex) {
            ErrorResp errorResp = new ErrorResp(ex.getMessage());
            return Response.status(Status.UNAUTHORIZED).entity(errorResp).build();
        }
    }

    @Path("createNewCustomer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewCustomer(CreateCustomerReq createCustomerReq) {
        if (createCustomerReq != null) {
            try {

                Customer newCust = customerSessionBeanLocal.createNewCustomer(createCustomerReq.getNewCustomer());

                CreateCustomerResp createCustomerResp = new CreateCustomerResp(newCust);

                return Response.status(Status.OK).entity(createCustomerResp).build();
            } catch (InputDataValidationException | UnknownPersistenceException ex) {
                ErrorResp errorResp = new ErrorResp(ex.getMessage());

                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResp).build();
            }
        } else {
            ErrorResp errorResp = new ErrorResp("Invalid request");

            return Response.status(Status.BAD_REQUEST).entity(errorResp).build();
        }
    }

    @Path("updateCustomer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCustomer(UpdatedCustomerReq updatedCustomerReq) {

        System.out.println("updateCustomer in webservice");

        if (updatedCustomerReq != null) {

            try {

                System.out.println("********** CustomerResource.updateCustomer() ************** ");
                customerSessionBeanLocal.updateCustomer(updatedCustomerReq.getUpdatedCustomer());

                //CreateCustomerResp createCustomerResp = new CreateCustomerResp(updatedCustomer); //return updated customer under the same wrapper class as create
                return Response.status(Response.Status.OK).build();

            } catch (CustomerNotFoundException | InputDataValidationException | UpdateCustomerException ex) {

                ErrorResp errorResp = new ErrorResp("Invalid Request");

                return Response.status(Status.BAD_REQUEST).entity(errorResp).build();
            }
        } else {
            ErrorResp errorResp = new ErrorResp("Invalid Request");

            return Response.status(Status.BAD_REQUEST).entity(errorResp).build();
        }
    }

    @Path("checkout")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkout(CheckoutReq checkoutReq) {
        System.out.println("checkout() REST API CAll");
        if (checkoutReq != null) {
            try {
                System.out.println(checkoutReq);
                List<OrderLineItem> orderLineItems = new ArrayList<>();
                for (CheckoutItem ci : checkoutReq.getCheckoutItems()) {
                    System.out.println(ci);

                    Listing listing = listingSessionBean.retrieveListingById(ci.getListingId());
                    orderLineItems.add(new OrderLineItem(listing, ci.getQty(), ci.getSubtotal(), ci.getSelectedOptions()));
                }

                OrderEntity oe = orderSessionBean.checkout(checkoutReq.getCustomerId(), checkoutReq.getOutletId(),
                        checkoutReq.getTotalLineItem(), checkoutReq.getTotalQuantity(), checkoutReq.getTotalAmount(),
                        checkoutReq.getAddress(), checkoutReq.getAddressDetails(), checkoutReq.getCcNum(),
                        checkoutReq.getDeliveryNote(), orderLineItems);

                System.out.println("SUCCESS");
                oe.setCustomer(null);
                oe.getOutlet().setRetailerEntity(null);
                oe.getOutlet().setListings(null);
                for (OrderLineItem oli : oe.getOrderLineItems()) {
                    oli.getListing().setOutletEntity(null);
                    oli.getListing().setCategory(null);
                    oli.getListing().setSizeOptions(null);
                    oli.getListing().setSugarOptions(null);
                    oli.getListing().setIceOptions(null);
                    oli.getListing().setToppingOptions(null);
                }
                
                
                return Response.status(Response.Status.OK).entity(oe).build();

//                CheckoutResp checkoutResp = new CheckoutResp("true");
//                return Response.status(Response.Status.OK).entity(checkoutResp).build();
            } catch (ListingNotFoundException | CheckoutError ex) {
                ErrorResp errorResp = new ErrorResp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResp).build();

            }
        } else {
            ErrorResp errorResp = new ErrorResp("Invalid Request");
            return Response.status(Status.BAD_REQUEST).entity(errorResp).build();
        }
    }

    private CustomerSessionBeanLocal lookupCustomerSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerSessionBeanLocal) c.lookup("java:global/graBBT/graBBT-ejb/CustomerSessionBean!ejb.session.stateless.CustomerSessionBeanLocal");
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

    private OrderSessionBeanLocal lookupOrderSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (OrderSessionBeanLocal) c.lookup("java:global/graBBT/graBBT-ejb/OrderSessionBean!ejb.session.stateless.OrderSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
