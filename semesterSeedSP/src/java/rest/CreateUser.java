/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import deploy.DeploymentConfiguration;
import facades.UserFacade;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author christian
 */
@Path("generic")
public class CreateUser {
    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    UserFacade uf = new UserFacade(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME));
    private static JsonArray list = new JsonArray();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CreateUser
     */
    public CreateUser() {
    }

    /**
     * Retrieves representation of an instance of rest.CreateUser
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(String content) {
        JsonObject json = parser.parse(content).getAsJsonObject();
        
        
        String userName = json.get("userName").getAsString();
        String pw = json.get("pw").getAsString();
        String first = json.get("first").getAsString();
        String last = json.get("last").getAsString();
        String phone = json.get("phone").getAsString();
        String email = json.get("email").getAsString();
                
                
        uf.createUser(userName, pw, first, last, phone, email);
        
        JsonObject jObj = new JsonObject();
        
        jObj.addProperty("userName", userName);
        jObj.addProperty("pw", pw);
        jObj.addProperty("first", first);
        jObj.addProperty("last", last);
        jObj.addProperty("phone", phone);
        jObj.addProperty("email", email);
        
        String jsonString = gson.toJson(jObj);
        
        return gson.toJson(jsonString);
        
    }
}
