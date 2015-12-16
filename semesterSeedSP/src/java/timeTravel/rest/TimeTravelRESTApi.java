package timeTravel.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import data.GenerateFlights;
import exception.ApiException;
import exception.NoFlightsFoundException;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import rest.FlightHelper;
import static rest.FlightHelper.*;
import timeTravel.entities.FlightInstance;
import timeTravel.facade.Facade;

/**
 * REST Web Service
 * 
 * @author <martinweberhansen at gmail.com>
 */
@Path("flightinfo")
public class TimeTravelRESTApi {
    @GET
    @Produces("application/json")
    @Path("generate/{n}")
    public String generateFlights(@PathParam("n") int n) {
        GenerateFlights gen = new GenerateFlights();
        gen.generateFlights(n);
        return "{\"msg\":\"ok\"}";
    }
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public TimeTravelRESTApi() {
    }
    
    @GET
    @Produces("application/json")
    @Path("{from}/{date}/{tickets}")
    public String getFlightsFrom(@PathParam("from") String from, 
                                 @PathParam("date") String date, 
                                 @PathParam("tickets") int numTickets) throws IOException, ApiException {
        FlightHelper fh = new FlightHelper(from, date, numTickets);
        fh.checkInput();
        Facade facade = new Facade();
        List<FlightInstance> flights = facade.getFlightInstances(from, date, numTickets);
        
        if (flights.size() < 1) {
            throw new NoFlightsFoundException("No flights found");
        }
        String jsonFlights = fh.convertFlightInstanceListToJson(flights, numTickets);
        return jsonFlights;
    }
    
    @GET
    @Produces("application/json")
    @Path("{from}/{to}/{date}/{tickets}")
    public String getFlightsFromTo(@PathParam("from") String from,
                                   @PathParam("to") String to,
                                   @PathParam("date") String date, 
                                   @PathParam("tickets") int numTickets) throws IOException, ApiException {
        FlightHelper fh = new FlightHelper(from, date, numTickets);
        fh.checkInput();
        Facade facade = new Facade();
        List<FlightInstance> flights = facade.getFlightInstances(from, to, date, numTickets);
        if (flights.size() < 1) {
            throw new NoFlightsFoundException("No flights found");
        }
        String jsonFlights = fh.convertFlightInstanceListToJson(flights, numTickets);
        return jsonFlights;
        
        //return "";
    }
    
    
    

}
