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
import facades.AirlineFacade;
import facades.ReservationFacade;
import facades.UserFacade;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import java.util.Scanner;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.swing.text.AbstractDocument.Content;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import net.minidev.json.JSONObject;
import timeTravel.entities.Airline;
import timeTravel.entities.Passenger;
import timeTravel.entities.Reservation;
import timeTravel.facade.Facade;
import us.monoid.web.Resty;
//import static us.monoid.web.Resty.*;



@Path("reserv")
public class Reservations {
   
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    //Facade f = new Facade();
    private String apiBase = "api/flightreservation/";
  
    @Context
    private UriInfo context;

    public Reservations() {
    }
    
@POST
@RolesAllowed("User")
@Consumes(MediaType.APPLICATION_JSON)
public Response setReservation(@Context SecurityContext sc, String content) throws ParseException, MalformedURLException, IOException{

    JsonObject json = parser.parse(content).getAsJsonObject();
    UserFacade uf = new UserFacade();
    AirlineFacade af = new AirlineFacade();
    String user = sc.getUserPrincipal().getName();
    
    
    String airline = json.get("airline").getAsString();
    String flightID = json.get("flightID").getAsString();
    int numberOfSeats = json.get("numberOfSeats").getAsInt();
    String firstName = uf.getUserByUserId(user).getFirstName();
    String lastName = uf.getUserByUserId(user).getLastName();
    String mail = uf.getUserByUserId(user).getEmail();
    String phone = uf.getUserByUserId(user).getPhone();
    JsonArray passengersArray = (JsonArray)json.get("Passengers");

    JsonObject requestObject = new JsonObject();
    requestObject.add("Passengers", passengersArray);
    requestObject.addProperty("flightID", flightID);
    requestObject.addProperty("numberOfSeats", numberOfSeats);
    requestObject.addProperty("ReserveeName", firstName+" "+lastName);
    requestObject.addProperty("ReservePhone", phone);
    requestObject.addProperty("ReserveeEmail", mail);
    String jsonRequest = new Gson().toJson(requestObject);

    Airline A = af.getAirline(airline);
    String url = A.getUrl()+apiBase;

    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
    con.setRequestProperty("Content-Type", "application/json;");
    con.setRequestProperty("Accept", "application/json");
    con.setRequestProperty("Method", "POST");
    con.setDoOutput(true);
    PrintWriter pw = new PrintWriter(con.getOutputStream());
    try (OutputStream os = con.getOutputStream()) {
    os.write(jsonRequest.getBytes("UTF-8"));
    }
    int HttpResult = con.getResponseCode();
    InputStreamReader is = HttpResult < 400 ? new InputStreamReader(con.getInputStream(), "utf-8") :
    new InputStreamReader(con.getErrorStream(), "utf-8");
    Scanner responseReader = new Scanner(is);
    String response = "";
    while (responseReader.hasNext()) {
    response += responseReader.nextLine()+System.getProperty("line.separator");
    }
    
    
    System.out.println(response);
    System.out.println(con.getResponseCode());
    System.out.println(con.getResponseMessage());
    
    

//    JsonObject responseJson = parser.parse(response).getAsJsonObject();
//    String responseFlightID = responseJson.get("FlightID").getAsString();
//    String responseOrigin = responseJson.get("Origin").getAsString();
//    String responseDate = responseJson.get("Date").getAsString();
//    String responseDestination = responseJson.get("Destination").getAsString();
//    String responseFlightTime = responseJson.get("FlightTime").getAsString();
//    int responseNumberOfSeats = responseJson.get("numberOfSeats").getAsInt();
//    String responseReserveeName = responseJson.get("ReserveeName").getAsString();
//    JsonArray responsePassengersArray = (JsonArray)json.get("Passengers");
//    
//    JsonObject responseObject = new JsonObject();
//    responseObject.add("Passengers", responsePassengersArray);
//    responseObject.addProperty("flightID", flightID);
//    responseObject.addProperty("Origin", responseOrigin);
//    responseObject.addProperty("Date", responseDate);
//    responseObject.addProperty("Destination", responseDestination);
//    responseObject.addProperty("FlightTime", responseFlightTime);
//    responseObject.addProperty("numberOfSeats", responseNumberOfSeats);
//    responseObject.addProperty("ReserveeName", responseReserveeName);
//    String jsonResponse = new Gson().toJson(responseObject);
    
    return Response.status(200).entity(gson.toJson(response)).type(MediaType.APPLICATION_JSON).build();
    //  return response;
//    return jsonResponse;
    
}




// "flightID":" String ",
// "Origin":"String (Friendly name + IATA)",
// "Destination":"String (Friendly name + IATA)",
// "Date":"ISO-8601-Date/time",
// "FlightTime":"Integer (minutes)",
// "numberOfSeats":"Integer",
// "ReserveeName":"String",
// "Passengers":[
// {
// "firstName":"String",
// "lastName":
        
//    List<Passenger> passengers = new ArrayList<>();
//    for (int i = 0; i < passengers.size(); i++) {
//        JsonObject passengerJson = (JsonObject) passengersArray.get(i).getAsJsonObject();
//        String firstName = passengerJson.get("firstName").getAsString();
//        String lastName = passengerJson.get("lastName").getAsString();
//        Passenger newPassenger = new Passenger(firstName,lastName);
//        passengers.add(newPassenger);
//        
//    }


//

    
    
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
        //String Origin = json.get("Origin").getAsString();
        //String Destination = json.get("Destination").getAsString();
        //Date Date = df.parse(json.get("Date").getAsString()); - Keep as comment for now
        //String tempDate = gson.fromJson(json.get("Date").getAsString(),String.class);
        
        //int FlightTime = json.get("FlightTime").getAsInt();



//        jo.addProperty("flightID", flightID);
//        jo.addProperty("Origin", Origin);
//        jo.addProperty("Destination", Destination);
//        jo.addProperty("Date", tempDate);
//        jo.addProperty("FlightTime", FlightTime);
//        jo.addProperty("numberOfSeats", numberOfSeats);
//        jo.addProperty("ReserveeName", ReserveeName);
//        
//        String jsonString = gson.toJson(jo);
//        
//        return gson.toJson(jsonString);