package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.JOSEException;
import java.text.ParseException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import static security.JWTAuthenticationFilter.getUsernameFromToken;
import timeTravel.entities.Reservation;
import timeTravel.facade.Facade;

@Path("demouser")
@RolesAllowed("User")
public class User {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("user")
//    @Path("test")
    public String getSomething() {
        return "{\"message\" : \"This message was delivered via a REST call accesible by only authenticated USERS\"}";
    }
    @GET
    @Produces("application/json")
    @Path("reservationlist")
    @RolesAllowed("user")
    public String getReservations(@HeaderParam("Authorization") String token) throws ParseException, JOSEException {
        token = token.substring("Bearer ".length());
        String userName = getUsernameFromToken(token);
        Facade facade = new Facade();
        List<Reservation> reservations = facade.getReservationsByUsername(userName);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(reservations);
    }
}
