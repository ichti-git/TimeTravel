/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import rest.Flights;

/**
 *
 * @author ichti
 */
public class FlightGetterCallable implements Callable<JsonArray> {
    private String apiBase = "api/flightinfo/";
    private String from;
    private String to;
    private String date;
    private int numTickets;
    private String airline;

    public FlightGetterCallable(String from, String to, String date, int numTickets, String airline) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.numTickets = numTickets;
        this.airline = airline;
    }
    
    
    
    @Override
    public JsonArray call() throws MalformedURLException {
        JsonArray flights = new JsonArray();
        //Build the urlString
        StringBuilder urlString = new StringBuilder(airline);
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
                JsonElement jsonFlightsElement = airlineFlights.get("flights");
                if (jsonFlightsElement != null) {
                    JsonArray jsonFlights = jsonFlightsElement.getAsJsonArray();
                    //Add the flights from the airline to combined flights
                    for (JsonElement nf: jsonFlights) {
                        JsonObject newFlight = (JsonObject)nf;
                        newFlight.add("airline", jsonAirline);
                        flights.add(newFlight);
                    }
                }
            }
        } catch (IOException ex) {
            //URL not found or error in response, skip it!
            Logger.getLogger(Flights.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flights;
    }
    
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
    
}
