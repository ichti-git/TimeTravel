package timeTravel.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import timeTravel.entities.FlightInstance;
import timeTravel.facade.Facade;

/**
  * @author <martinweberhansen at gmail.com>
 */
@Path("flightinfo")
public class TimeTravelRESTApi {

    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    Facade facade = new Facade();
    
    @Context
    private UriInfo context;

    public TimeTravelRESTApi() {
    }

    
    @GET
    @Path("flightinfo/{origin}/{date}/{numTickets}")
    @Produces("application/json")
    public String getFlightInstances(@PathParam("origin")String origin,@PathParam("date")String date,@PathParam("numTickets")String numTickets){ 
        
        int convertedNumTickets = Integer.parseInt(numTickets);
        JsonArray response = new JsonArray();
        List<FlightInstance> flightInstances = facade.getFlightInstances(origin,date,convertedNumTickets);
        
        for (FlightInstance flightInstance : flightInstances) {
            
            JsonObject jsObj = new JsonObject();
            FlightInstance f = flightInstance;
            
            jsObj.addProperty("airline",f.getFlight().getAirline().getName());
            
            jsObj.addProperty("date", f.getDepartureDate().toString());
            jsObj.addProperty("numberOfSeats", f.getAvailableSeats());
            jsObj.addProperty("totalPrice", f.getPrice());
            jsObj.addProperty("flightID",f.getFlight().getFligthNumber());
            jsObj.addProperty("traveltime", f.getFlightTime());
            jsObj.addProperty("destination",f.getFliesTo().getCity());
            jsObj.addProperty("origin",f.getFliesFrom().getCity());
            response.add(jsObj);
        }
        return gson.toJson(response);
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @GET
    @Produces("application/json")
    public String getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("application/json")
    public void putJson(String content) {
    }
}
