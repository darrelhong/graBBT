package ws.restful.resources;

import ejb.session.stateless.CustomerSessionBeanLocal;
import entity.Customer;
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
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.exception.InputDataValidationException;
import util.exception.InvalidLoginCredentialException;
import util.exception.UnknownPersistenceException;
import ws.restful.model.CreateCustomerReq;
import ws.restful.model.CreateCustomerResp;
import ws.restful.model.CustomerLoginResp;
import ws.restful.model.ErrorResp;

@Path("Customer")
public class CustomerResource {

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

    private CustomerSessionBeanLocal lookupCustomerSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CustomerSessionBeanLocal) c.lookup("java:global/graBBT/graBBT-ejb/CustomerSessionBean!ejb.session.stateless.CustomerSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
