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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import net.minidev.json.JSONObject;
import timeTravel.entities.Passenger;
import timeTravel.entities.Reservation;
import timeTravel.facade.Facade;


/**
 * REST Web Service
 *
 * @author Silas
 */
@Path("reservations")
public class Reservations {
   
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    Facade f = new Facade();
    
    
    
    
    private String apiBase = "api/flightreservation/";
    
//    private static JsonArray list = new JsonArray();
    
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Reservations
     */
    public Reservations() {
    }

    /**
     * Retrieves representation of an instance of rest.Reservations
     * @return an instance of java.lang.String
     * 
{
 "flightID":" String ",
 "Origin":"String (Friendly name + IATA)",
 "Destination":"String (Friendly name + IATA)",
 "Date":"ISO-8601-Date/time",
 "FlightTime":"Integer (minutes)",
 "numberOfSeats":"Integer",
 "ReserveeName":"String",
 "Passengers":[
 {
 "firstName":"String",
 "lastName":"String"
 }
 ]
}
     */
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String setReservation(String content) throws ParseException{
        
        JsonObject json = parser.parse(content).getAsJsonObject();
        
        String flightID = json.get("flightID").getAsString();
        String Origin = json.get("Origin").getAsString();
        String Destination = json.get("Destination").getAsString();
        //Date Date = df.parse(json.get("Date").getAsString()); - Keep as comment for now
        String tempDate = gson.fromJson(json.get("Date").getAsString(),String.class);
        
        int FlightTime = json.get("FlightTime").getAsInt();
        int numberOfSeats = json.get("numberOfSeats").getAsInt();
        String ReserveeName = json.get("ReserveeName").getAsString();
        JsonArray passengers = json.get("Passengers").getAsJsonArray();
        //Passengers.add();
        
        
//        Iterator t = passengers.iterator();
//        while (t.hasNext()){
//            JsonObject fName = (JsonObject) passengers.getAsJsonObject().get("firstName");
//            JsonObject lName = (JsonObject) passengers.getAsJsonObject().get("lastName");
//            t.next();
//        }
        Iterator t = passengers.iterator();
        while (t.hasNext()){
            String fName = (String) t.next().getAsString()//..get("firstName");
            JsonObject lName = (JsonObject) passengers.getAsJsonObject().get("lastName");
            t.next();
        }
        
        for (int i = 0; i < passengers.size(); i++) {
            /*JsonElement*/JsonObject fName = (JsonObject) passengers.getAsJsonObject().get("firstName");
            String firstName = fName.toString();
            JsonElement pass = passengers.get(i);
         
            JsonElement lName = passengers.getAsJsonArray();//.getAsJsonObject().get("lastName");
            String lastName = lName.toString();
//            JSONObject ps = Passengers.getJSONObject(i);
            
            
        }
        
        
        
        // Trying different things at the same time
        
        JsonObject jo = new JsonObject();
        
        
        
        
        
        jo.addProperty("flightID", flightID);
        jo.addProperty("Origin", Origin);
        jo.addProperty("Destination", Destination);
        jo.addProperty("Date", tempDate);
        jo.addProperty("FlightTime", FlightTime);
        jo.addProperty("numberOfSeats", numberOfSeats);
        jo.addProperty("ReserveeName", ReserveeName);
        
        String jsonString = gson.toJson(jo);
        
        return gson.toJson(jsonString);
    }
    
    @GET
    @Produces("text/plain")
    public String getText() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of Reservations
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }
}
