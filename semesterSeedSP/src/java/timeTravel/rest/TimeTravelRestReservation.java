package timeTravel.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.ApiException;
import exception.NoFlightsFoundException;
import facades.ReservationFacade;
import facades.UserFacade;
import static help.DateHelp.getDateFromDateString;
import static help.DateHelp.getDateStringFromDate;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import timeTravel.entities.FlightInstance;
import timeTravel.entities.Passenger;
import timeTravel.facade.Facade;

/**
 * REST Web Service
 * 
 * @author <martinweberhansen at gmail.com>
 */
@Path("flightreservation")
public class TimeTravelRestReservation {

    JsonParser parser = new JsonParser();
    
    public TimeTravelRestReservation() {
    }

    /**
     * Retrieves representation of an instance of timeTravel.rest.TimeTravelRestReservation
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }
    
    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public String setReservation(String content) throws  ParseException,IOException, ApiException {
        
        JsonObject json = parser.parse(content).getAsJsonObject();
        
        System.out.println(content+" THIS WAS content FROM timeTravel.rest.TimeTravelReservation..");
        
        
        String flightID = json.get("flightID").getAsString();
        int numberOfSeats = json.get("numberOfSeats").getAsInt();
        String ReserveeName = json.get("ReserveeName").getAsString();
        String ReservePhone = json.get("ReservePhone").getAsString();
        String ReserveeEmail = json.get("ReserveeEmail").getAsString();
        JsonArray passengersArray = (JsonArray)json.get("Passengers");
        
        
        Facade facade = new Facade();
        FlightInstance flightinstance = facade.getFlightInstance(flightID);

        System.out.println(flightinstance.toString()+" this was flight instance timeTravel.rest.TimeTravelReservation");
        
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < passengers.size(); i++) {
            JsonObject passengerJson = (JsonObject) passengersArray.get(i).getAsJsonObject();
            String responseFirstName = passengerJson.get("firstName").getAsString();
            String responseLastName = passengerJson.get("lastName").getAsString();
            Passenger newPassenger = new Passenger(responseFirstName,responseLastName);
            passengers.add(newPassenger);
        } 
        
        
                
        String responseflightID = flightID;
        String responseOrigin = flightinstance.getFliesFrom().getIatacode()+" : "+flightinstance.getFliesFrom().getCity();
        String responseDate = getDateStringFromDate(flightinstance.getDepartureDate());
        String responseDestination = flightinstance.getFliesTo().getIatacode()+" : "+flightinstance.getFliesTo().getCity();
        int responseFlightTime = flightinstance.getDeparturetime();
        int responsenumberOfSeats = numberOfSeats;
        String responseReserveeName = ReserveeName;
        JsonArray responsePassengersArray = passengersArray;
        
        JsonObject responseObject = new JsonObject();
        
        responseObject.add("Passengers", responsePassengersArray);
        responseObject.addProperty("flightID", responseflightID);
        responseObject.addProperty("Origin", responseOrigin);
        responseObject.addProperty("Date", responseDate);
        responseObject.addProperty("Destination", responseDestination);
        responseObject.addProperty("FlightTime", responseFlightTime);
        responseObject.addProperty("numberOfSeats", responsenumberOfSeats);
        responseObject.addProperty("ReserveeName", responseReserveeName);
        String jsonResponse = new Gson().toJson(responseObject);
                
        
        return jsonResponse;
    }

    /**
     * PUT method for updating or creating an instance of TimeTravelRestReservation
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
