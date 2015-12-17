package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.JOSEException;
import facades.ReservationFacade;
import java.text.ParseException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import timeTravel.entities.Reservation;

@Path("admin")
@RolesAllowed("Admin")
public class Admin {

    @GET
    @Produces("application/json")
    @Path("reservationlist")
    public String getReservations() throws ParseException, JOSEException {
        ReservationFacade facade = new ReservationFacade();
        List<Reservation> reservations = facade.getReservations();
        System.out.println(reservations.get(0));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(reservations);
    }
}
