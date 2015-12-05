/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

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
import static rest.FlightHelper.flightInputChecker;

/**
 * REST Web Service
 *
 * @author ichti (Simon T)
 * Internal API
 */
@Path("flights")
public class Flights {
    private ArrayList<String> apiUrls = new ArrayList();
    private String apiBase = "api/flightinfo/";
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Flights
     */
    public Flights() {
        apiUrls.add("http://angularairline-plaul.rhcloud.com/");
        apiUrls.add("http://wildfly-x.cloudapp.net/airline/");
    }

    //api/flightinfo/:from/:date/:numTickets
    //api/flightinfo/:from/:to/date/:numTickets
    /*
     * If url does not respond with a http code 200, it will return null
     * 
     */
    private JsonObject getFlightsFromAirline(URL url) throws IOException {
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();
        if (request.getResponseCode() != 200) {
            return null;
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject object = element.getAsJsonObject();
        return object;
    }
    
    private JsonArray getFlights(String from, String date, int numTickets) throws IOException {
        return getFlights(from, null, date, numTickets);
    }
    private JsonArray getFlights(final String from, final String to, final String date, final int numTickets) {
        
        List<Future<JsonArray>> list = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        Iterator ite = apiUrls.iterator();
        while (ite.hasNext()) {
            final String next = (String)ite.next();
            Callable<JsonArray> task = new Callable<JsonArray>() {
                @Override
                public JsonArray call() throws MalformedURLException {
                    JsonArray flights = new JsonArray();
                    //Build the urlString
                    StringBuilder urlString = new StringBuilder(next);
                    urlString.append(apiBase);
                    urlString.append(from);
                    urlString.append("/");
                    if (to != null) {
                        urlString.append(to);
                        urlString.append("/");
                    }
                    urlString.append(date);
                    urlString.append("/");
                    urlString.append(numTickets);
                    URL url = new URL(urlString.toString());
                    try {
                        //Get the flights from the airline
                        JsonObject airlineFlights = getFlightsFromAirline(url);
                        if (airlineFlights != null) {
                            JsonElement jsonAirline = airlineFlights.get("airline");
                            JsonArray jsonFlights = airlineFlights.get("flights").getAsJsonArray();
                            Iterator jsonIte = jsonFlights.iterator();
                            //Add the flights from the airline to combined flights
                            while (jsonIte.hasNext()) {
                                JsonObject newFlight = ((JsonObject)jsonIte.next());
                                newFlight.add("airline", jsonAirline);
                                flights.add(newFlight);
                            }
                        }
                    } catch (IOException ex) {
                        //URL not found or error in response, skip it!
                        Logger.getLogger(Flights.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return flights;
                }
            };
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
    
    @GET
    @Produces("application/json")
    @Path("{from}/{date}/{tickets}")
    public String getFlightsFrom(@PathParam("from") String from, 
                                 @PathParam("date") String date, 
                                 @PathParam("tickets") int numTickets) throws IOException, ApiException {
        flightInputChecker(from, null, date, numTickets);
        JsonArray flights = getFlights(from, date, numTickets);
        
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
        flightInputChecker(from, to, date, numTickets);
        JsonArray flights = getFlights(from, to, date, numTickets);
        if (flights.size() < 1) {
            throw new NoFlightsFoundException("No flights found");
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(flights);
    }

}

