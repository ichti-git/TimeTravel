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
import com.google.gson.stream.JsonReader;
import facades.AirlineFacade;
import facades.ReservationFacade;
import facades.UserFacade;
import static help.DateHelp.getDateFromDateString;
import help.PassengerIdGenerator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
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

@Path("reserve")
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
    @Produces("application/json")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setReservation(@Context SecurityContext sc, String content) throws ParseException, MalformedURLException, IOException {

        JsonObject json = parser.parse(content).getAsJsonObject();
        UserFacade uf = new UserFacade();
        AirlineFacade af = new AirlineFacade();
        String user = sc.getUserPrincipal().getName();

        String airline = json.get("airline").getAsString();
        String flightID = json.get("flightId").getAsString();
        int numberOfSeats = json.get("numberOfSeats").getAsInt();
        String firstName = uf.getUserByUserId(user).getFirstName();
        String lastName = uf.getUserByUserId(user).getLastName();
        String mail = uf.getUserByUserId(user).getEmail();
        String phone = uf.getUserByUserId(user).getPhone();
        JsonArray passengersArray = (JsonArray) json.get("passengers");

        List<Passenger> passengers = new ArrayList();
        
        for (JsonElement p : passengersArray) {
            JsonObject jo = p.getAsJsonObject();
            String first = jo.get("firstname").getAsString();
            String last = jo.get("lastname").getAsString();
            Passenger pas = new Passenger();
            pas.setFirstName(first);
            pas.setLastName(last);
            passengers.add(pas);
        }
        
        JsonObject requestObject = new JsonObject();
        requestObject.add("Passengers", passengersArray);
        requestObject.addProperty("flightID", flightID);
        requestObject.addProperty("numberOfSeats", numberOfSeats);
        requestObject.addProperty("ReserveeName", firstName + " " + lastName);
        requestObject.addProperty("ReservePhone", phone);
        requestObject.addProperty("ReserveeEmail", mail);
        String jsonRequest = new Gson().toJson(requestObject);

        Airline A = af.getAirline(airline);
        String url = A.getUrl() + apiBase;
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
        InputStreamReader is = HttpResult < 400 ? new InputStreamReader(con.getInputStream(), "utf-8")
                : new InputStreamReader(con.getErrorStream(), "utf-8");
        Scanner responseReader = new Scanner(is);
        String response = "";
        while (responseReader.hasNext()) {
            response += responseReader.nextLine() + System.getProperty("line.separator");
        }

        JsonObject returnJson = parser.parse(response).getAsJsonObject();
        JsonArray passengersArrayList = (JsonArray) returnJson.get("Passengers");
        /*
        List<Passenger> passengers = new ArrayList();

        for (JsonElement p : passengersArrayList) {
            JsonObject jo = p.getAsJsonObject();
            String first = jo.get("firstname").getAsString();
            String last = jo.get("lastname").getAsString();
            Passenger newPassenger = new Passenger(first, last);
            passengers.add(newPassenger);
        }
        * */
        String strDate = returnJson.get("Date").getAsString();
        Date date = getDateFromDateString(strDate);
        entity.User returnUser = uf.getUserByUserId(user);
        ReservationFacade rf = new ReservationFacade();

        rf.setReservation(returnJson.get("flightID").getAsString(),
                returnJson.get("numberOfSeats").getAsInt(),
                returnJson.get("ReserveeName").getAsString(),
                phone,
                mail,
                passengers,
                returnJson.get("Origin").getAsString(),
                returnJson.get("Destination").getAsString(),
                date,
                returnUser,
                returnJson.get("FlightTime").getAsInt()
        );

        return Response.status(200).entity(gson.toJson(response)).type(MediaType.APPLICATION_JSON).build();
    }
}
