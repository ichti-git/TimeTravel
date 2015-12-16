package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import deploy.DeploymentConfiguration;
import facades.UserFacade;
import java.text.ParseException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import timeTravel.entities.Reservation;
import timeTravel.facade.Facade;

@Path("user")
//@RolesAllowed("User")
public class User {
    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    UserFacade facade = new UserFacade(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME));

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
    public String getReservations(@Context SecurityContext sc) throws ParseException, JOSEException {
        String userName;
        userName = sc.getUserPrincipal().getName();
        Facade facade = new Facade();
        
        List<Reservation> reservations = facade.getReservationsByUsername(userName);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(reservations);
    }
    
    @GET
    @Produces("application/json")
    @Path("getuser")
    @RolesAllowed("User")
    public String getUser(@Context SecurityContext sc) throws ParseException, JOSEException {
        String userName = sc.getUserPrincipal().getName();
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
    
    @PUT
    @Produces("application/json")
    @Path("edituser")
    @RolesAllowed("User")
    public String editUser(@Context SecurityContext sc, String content) throws ParseException, JOSEException {
        String userName = sc.getUserPrincipal().getName();
        JsonObject json = parser.parse(content).getAsJsonObject();
        String password = json.get("password").getAsString();
        String first = json.get("first").getAsString();
        String last = json.get("last").getAsString();
        String phone = json.get("phone").getAsString();
        String email = json.get("email").getAsString();
        
        entity.User user = facade.editUser(userName, password, first, last, phone, email);
        JsonObject jsonOb = new JsonObject();
        jsonOb.addProperty("userName", user.getUserName());
        jsonOb.addProperty("password", user.getPassword());
        jsonOb.addProperty("firstname", user.getFirstName());
        jsonOb.addProperty("lastname", user.getLastName());
        jsonOb.addProperty("phone", user.getPhone());
        jsonOb.addProperty("email", user.getEmail());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}
