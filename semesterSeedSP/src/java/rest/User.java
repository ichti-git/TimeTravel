package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.nimbusds.jose.JOSEException;
import deploy.DeploymentConfiguration;
import facades.UserFacade;
import java.text.ParseException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static security.JWTAuthenticationFilter.getUsernameFromToken;
import timeTravel.entities.Reservation;
import timeTravel.facade.Facade;

@Path("user")
//@RolesAllowed("User")
public class User {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("user")
    @Path("test")
    public String getSomething() {
        return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated USERS\"}";
    }
    
    @GET
    @Produces("application/json")
    @Path("reservationlist")
    @RolesAllowed("User")
    public String getReservations(@HeaderParam("Authorization") String token) throws ParseException, JOSEException {
        token = token.substring("Bearer ".length());
        String userName;
        try{
             userName = getUsernameFromToken(token);             //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        }catch (ParseException | JOSEException e) {     
                throw new NotAuthorizedException("You are not authorized to perform this action", Response.Status.FORBIDDEN);
            }
        Facade facade = new Facade();
        
        List<Reservation> reservations = facade.getReservationsByUsername(userName);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(reservations);
    }
    
    @GET
    @Produces("application/json")
    @Path("getuser")
    @RolesAllowed("User")
    public String getUser(@HeaderParam("Authorization") String token) throws ParseException, JOSEException {
        token = token.substring("Bearer ".length());
        String userName = getUsernameFromToken(token);
        UserFacade facade = new UserFacade(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME));
        entity.User user = facade.getUserByUserId(userName);
        JsonObject json = new JsonObject();
        json.addProperty("firstname", user.getFirstName());
        json.addProperty("lastname", user.getLastName());
        json.addProperty("phone", user.getPhone());
        json.addProperty("email", user.getEmail());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}
