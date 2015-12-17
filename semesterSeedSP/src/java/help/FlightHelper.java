/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import exception.ApiException;
import exception.IllegalInputException;
import facades.AirlineFacade;
import facades.SearchLogFacade;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import rest.Flights;
import timeTravel.entities.Airline;
import timeTravel.entities.FlightInstance;

/**
 *
 * @author ichti (Simon T)
 */
public class FlightHelper {
    
    private final String airlineName = "TimeTravel";
    private JsonArray flights;
    interface FlightClass {};
    private String origin;
    private String destination;
    private String date;
    private int tickets;

    public FlightHelper(String origin, String destination, String date, int tickets) {
        this.destination = destination;
        this.origin = origin;
        this.tickets = tickets;
        this.date = date;
    }
    
    public FlightHelper(String origin, String date, int tickets) {
        this.destination = null;
        this.origin = origin;
        this.tickets = tickets;
        this.date = date;
    }
    
    public FlightHelper() {
        
    }
    
    public void logFlightSearch() {
        SearchLogFacade slf = new SearchLogFacade();
        slf.saveSearch(origin, destination, date, tickets);
    }
    
    public String convertFlightInstanceListToJson(List<FlightInstance> flights, int numTickets) {
        JsonObject json = new JsonObject();
        json.addProperty("airline", airlineName);
        JsonArray jsonFlights = new JsonArray();
        for (FlightInstance fi: flights) {
            jsonFlights.add(convertFlightInstanceToJson(fi, numTickets));
        }
        json.add("flights", jsonFlights);
        return new GsonBuilder().setPrettyPrinting().create().toJson(json);
    }
    
    private JsonObject convertFlightInstanceToJson(final FlightInstance flight, final int numTickets) { 
        JsonObject json = new JsonObject();
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String date = sdfISO.format(flight.getDepartureDate());
        json.addProperty("date", date);
        json.addProperty("numberOfSeats", numTickets);
        json.addProperty("totalPrice", flight.getPrice()*numTickets);
        json.addProperty("flightID", flight.getFlightNumber());
        json.addProperty("traveltime", flight.getFlightTime());
        json.addProperty("destination", flight.getFliesTo().getIatacode());
        json.addProperty("origin", flight.getFliesFrom().getIatacode());
        return json;
    }

    public void checkInput() throws ApiException {
        if (destination != null) {
            if (destination.length() != 3) {
                throw new IllegalInputException("Origin airport code not valid");
            }
        }
        if (origin.length() != 3) {
            throw new IllegalInputException("Destination airport code not valid");
        }
        if (tickets < 1) {
            throw new IllegalInputException("Amount of ticket must be at least 1");
        }
        
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            sdfISO.parse(date);
        } catch (ParseException ex) {
            throw new IllegalInputException("Date format wrong");
        }
    }
    
    
    
    public JsonArray searchFlights() {
        this.flights = getFlights(origin, destination, date, tickets);
        return this.flights;
    }
    
    private JsonArray getFlights(String from, String date, int numTickets) throws IOException {
        return getFlights(from, null, date, numTickets);
    }
    
    private JsonArray getFlights(final String from, final String to, final String date, final int numTickets) {
        
        List<Future<JsonArray>> list = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(8);
        List<Airline> apiUrls = new AirlineFacade().getAirlines();
        
        for (final Airline airline : apiUrls) {
            Callable<JsonArray> task = new FlightGetterCallable(from, to, date, numTickets, airline.getUrl());
            list.add(executor.submit(task));
        }
        executor.shutdown();
        JsonArray allFlights = new JsonArray();
        for (Future<JsonArray> array : list) {
            try {
                allFlights.addAll(array.get());
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Flights.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       return allFlights;
    }
}
