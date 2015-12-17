package timeTravel.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.ApiException;
import static help.DateHelp.getDateStringFromDate;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    
    public TimeTravelRestReservation() {
    }
    
    @POST
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public String setReservation(String content) throws  ParseException,IOException, ApiException {
        
        JsonObject json = parser.parse(content).getAsJsonObject();
               
        
        String flightID = json.get("flightID").getAsString();
        int numberOfSeats = json.get("numberOfSeats").getAsInt();
        String ReserveeName = json.get("ReserveeName").getAsString();
        String ReservePhone = json.get("ReservePhone").getAsString();
        String ReserveeEmail = json.get("ReserveeEmail").getAsString();
        JsonArray passengersArray = (JsonArray)json.get("passengers");
        
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
        String responseDate = getDateStringFromDate(flightinstance.getDepartureDate());
        String responseDestination = flightinstance.getFliesTo().getIatacode()+" : "+flightinstance.getFliesTo().getCity();
        int responseFlightTime = flightinstance.getDeparturetime();
        int responsenumberOfSeats = numberOfSeats;
        String responseReserveeName = ReserveeName;
        
        System.out.println("THIS WAS FLIGHTTIME RESONSE  FROM TIMETRAVEL REST !!!!!!!!!!!!!!!!!!!!!!!!!!!"+responseFlightTime);
        
        JsonObject responseObject = new JsonObject();
        
        responseObject.add("passengers", passengersArray); //responsePassengersArray
        responseObject.addProperty("flightID", responseflightID);
        responseObject.addProperty("Origin", responseOrigin);
        responseObject.addProperty("Date", responseDate);
        responseObject.addProperty("Destination", responseDestination);
        responseObject.addProperty("FlightTime", responseFlightTime);
        responseObject.addProperty("numberOfSeats", responsenumberOfSeats);
        responseObject.addProperty("ReserveeName", responseReserveeName);
        String jsonResponse = new Gson().toJson(responseObject);
        
        System.out.println("THIS WAS FROM TIMETRAVEL JSONRESPONSE "+jsonResponse);
                
        return jsonResponse;
    }

}
