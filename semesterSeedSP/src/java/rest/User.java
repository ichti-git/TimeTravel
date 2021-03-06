package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import deploy.DeploymentConfiguration;
import facades.ReservationFacade;
import facades.UserFacade;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import security.PasswordHash;
import timeTravel.entities.Reservation;

@Path("user")
@RolesAllowed("User")
public class User {
    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    UserFacade facade = new UserFacade(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME));

    
    @GET
    @Produces("application/json")
    @Path("reservationlist")
    public String getReservations(@Context SecurityContext sc) throws ParseException, JOSEException {
        String userName;
        userName = sc.getUserPrincipal().getName();
        ReservationFacade facade = new ReservationFacade();
        
        List<Reservation> reservations = facade.getReservationsByUsername(userName);
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(reservations);
    }
    
    @GET
    @Produces("application/json")
    @Path("getuser")
    public String getUser(@Context SecurityContext sc) throws ParseException, JOSEException {
        String userName = sc.getUserPrincipal().getName();
        //UserFacade facade = new UserFacade(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME));
        entity.User user = facade.getUserByUserId(userName);
        JsonObject json = new JsonObject();
        
        json.addProperty("password", user.getPassword());
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
    public String editUser(@Context SecurityContext sc, String content) throws ParseException, JOSEException {
        
        String userName = sc.getUserPrincipal().getName();
        entity.User user = facade.getUserByUserId(userName);
        JsonObject json = parser.parse(content).getAsJsonObject();
        JsonElement password = json.get("password");
        if (password != null){
            try {
                user.setPassword(PasswordHash.createHash(password.getAsString()));
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidKeySpecException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        JsonElement firstName = json.get("firstname");
        if (firstName != null){
            user.setFirstName(firstName.getAsString());
        }
        JsonElement lastName = json.get("lastname");
        if (lastName != null){
            user.setLastName(lastName.getAsString());
        }
        JsonElement phone = json.get("phone");
        if (phone != null){
            user.setPhone(phone.getAsString());
        }
        JsonElement email = json.get("email");
        if (email != null){
            user.setEmail(email.getAsString());
        }
        
        entity.User editUser = facade.editUser(user);
        JsonObject jsonOb = new JsonObject();
        jsonOb.addProperty("userName", editUser.getUserName());
        jsonOb.addProperty("password", editUser.getPassword());
        jsonOb.addProperty("firstname", editUser.getFirstName());
        jsonOb.addProperty("lastname", editUser.getLastName());
        jsonOb.addProperty("phone", editUser.getPhone());
        jsonOb.addProperty("email", editUser.getEmail());
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}
