package timeTravel.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.ApiException;
import exception.NoFlightsFoundException;
import facades.ReservationFacade;
import facades.UserFacade;
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
        
        System.out.println(content+" this was content from timetravel");
        String flightID = json.get("flightID").getAsString();
        int numberOfSeats = json.get("numberOfSeats").getAsInt();
        String ReserveeName = json.get("ReserveeName").getAsString();
        String ReservePhone = json.get("ReservePhone").getAsString();
        String ReserveeEmail = json.get("ReserveeEmail").getAsString();
        JsonArray passengersArray = (JsonArray)json.get("Passengers");
        
        
        Facade facade = new Facade();
        FlightInstance flightinstance = facade.getFlightInstance(flightID);

        
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
        String responseDate = flightinstance.getDepartureDate().toString();
        String responseDestination = flightinstance.getFliesTo().getIatacode()+" : "+flightinstance.getFliesTo().getCity();
        int responseFlightTime = flightinstance.getDeparturetime();
        int responsenumberOfSeats = flightinstance.getNumberOfSeats();
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
        
//        Date date =  SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(responseDate);
//        Date d = responseDate.;
        
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss"); 
        Date date = df.parse(responseDate);
                
                
                
        ReservationFacade rf = new ReservationFacade();
        rf.setReservation(responseflightID, responsenumberOfSeats, responseReserveeName, ReservePhone,ReserveeEmail, passengers,responseOrigin,responseDestination,date);
    
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
