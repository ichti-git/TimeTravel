/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import help.FlightHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exception.ApiException;
import exception.IllegalInputException;
import exception.NoFlightsFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import timeTravel.entities.Airline;
import timeTravel.entities.Reservation;
import timeTravel.facade.Facade;

/**
 * REST Web Service
 *
 * @author ichti (Simon T)
 * Internal API
 */
@Path("flights")
public class Flights {
    private List<Airline> apiUrls = new ArrayList();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Flights
     */
    public Flights() {
        apiUrls = new Facade().getAirlines();
    }

    //api/flightinfo/:from/:date/:numTickets
    //api/flightinfo/:from/:to/date/:numTickets
    /*
     * If url does not respond with a http code 200, it will return null
     * 
     */
    
    
    @GET
    @Produces("application/json")
    @Path("{from}/{date}/{tickets}")
    public String getFlightsFrom(@PathParam("from") String from, 
                                 @PathParam("date") String date, 
                                 @PathParam("tickets") int numTickets) throws IOException, ApiException {
        FlightHelper fh = new FlightHelper(from, date, numTickets);
        fh.checkInput();
        fh.logFlightSearch();
        JsonArray flights = fh.searchFlights();
        
        if (flights.size() < 1) {
            throw new NoFlightsFoundException("No flights found");
        }
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(flights);
    }
    @GET
    @Produces("application/json")
    @Path("{from}/{to}/{date}/{tickets}")
    public String getFlightsFromTo(@PathParam("from") String from,
                                   @PathParam("to") String to,
                                   @PathParam("date") String date, 
                                   @PathParam("tickets") int numTickets) throws IOException, ApiException {
        FlightHelper fh = new FlightHelper(from, to, date, numTickets);
        fh.checkInput();
        fh.logFlightSearch();
        JsonArray flights = fh.searchFlights();
        if (flights.size() < 1) {
            throw new NoFlightsFoundException("No flights found");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(flights);
    }
   
    
}

