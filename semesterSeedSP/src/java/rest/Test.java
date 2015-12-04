/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author Silas
 */
@Path("test")
public class Test {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TestR
     */
    public Test() {
    }

   
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    private static Map<Integer, String> airlines = new HashMap() {
        {
            put(1, "TimeTravel");
            put(2, "AirYouKnowWhateverTravel");
            put(3, "BoatTravels");
        }
    };

    
    
    @GET
    @Produces("application/json")
    @Path("/test")
    public String getJohn() {

        Type type = new TypeToken<Map<Integer, String>>() {
        }.getType();

//        JsonObject response = new JsonObject();

//        response.addProperty("quote", quote);
        return gson.toJson(airlines, type);
        
    }
    /**
     * PUT method for updating or creating an instance of Test
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/plain")
    public void putText(String content) {
    }
}
