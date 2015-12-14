package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import facades.ReservationFacade;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.core.Context;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import timeTravel.entities.Reservation;


/**
 * REST Web Service
 *
 * @author Simon T
 */
@Path("cancelreservation")
public class CancelReservation {
    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("User")
    @Path("{id}")
    public Response getAirports(@Context SecurityContext sc, @PathParam("id") int id) {
        String userName = sc.getUserPrincipal().getName();
        UserFacade uf = new UserFacade();
        ReservationFacade rf = new ReservationFacade();
        entity.User user = uf.getUserByUserId(userName);
        JsonObject jsonobj = new JsonObject();
        /*
        Reservation reservation = rf.getReservationById(id); //TODO
        if (reservation.getReserveeUser() == user.getUserName() || sc.isUserInRole("Admin")) {
            rf.deleteReservation(id); //TODO, check if actually removed?
            jsonobj.addProperty("message", "Reservaiton succesfully canceled"); 
            return Response.status(200).entity(gson.toJson(jsonobj)).type(MediaType.APPLICATION_JSON).build();
        }
        else {
        
            //User not allowed to delete this reservation
            jsonobj.addProperty("message", "You're not allowed to cancel this reservation"); 
            return Response.status(403).entity(gson.toJson(jsonobj)).type(MediaType.APPLICATION_JSON).build();
        }
        */

        return Response.status(200).entity(gson.toJson(user)).type(MediaType.APPLICATION_JSON).build();
    }
    
}