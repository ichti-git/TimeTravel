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
import exception.ApiException;
import exception.IllegalInputException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import timeTravel.entities.FlightInstance;

/**
 *
 * @author ichti (Simon T)
 */
public class FlightHelper {
    
    private static String airlineName = "TimeTravel";
    interface FlightClass {}
    
    public static String convertFlightInstanceListToJson(List<FlightInstance> flights, int numTickets) {
        JsonObject json = new JsonObject();
        json.addProperty("airline", airlineName);
        JsonArray jsonFlights = new JsonArray();
        Iterator ite = flights.iterator();
        while (ite.hasNext()) {
            
            jsonFlights.add(convertFlightInstanceToJson((FlightInstance) ite.next(), numTickets));
        }
        json.add("flights", jsonFlights);
        return new GsonBuilder().setPrettyPrinting().create().toJson(json);
    }
    /*
    {
"date": ISO-8601 String,
"numberOfSeats": Integer,
"totalPrice": Number (Euro),
"flightID": String,
"traveltime": Integer (minutes),
"destination": IATA-Code (String),
"origin":"IATA-Code (String)
}
* */
    public static JsonObject convertFlightInstanceToJson(final FlightInstance flight, final int numTickets) { 
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
        //Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        //return gson.toJson(json);
    }

    public static void flightInputChecker(String from, String to, String date, int numTickets) throws ApiException {
        if (to != null) {
            if (to.length() != 3) {
                throw new IllegalInputException("Origin airport code not valid");
            }
        }
        if (from.length() != 3) {
            throw new IllegalInputException("Destination airport code not valid");
        }
        if (numTickets < 1) {
            throw new IllegalInputException("Amount of ticket must be at least 1");
        }
        
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date2 = sdfISO.parse(date);
        } catch (ParseException ex) {
            throw new IllegalInputException("Date format wrong");
        }
    }
}
