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
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author christian
 */
@Path("createUser")
public class CreateUser {
    
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    JsonParser parser = new JsonParser();
    UserFacade uf = new UserFacade(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME));

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CreateUser
     */
    public CreateUser() {
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String content) {
        String jsonString = "";
        JsonObject jObj = new JsonObject();
        int statusCode = 0;
        
        String userName = "";
        String password = "";
        String first = "";
        String last = "";
        String phone = "";
        String email = "";
        
        try {
            JsonObject json = parser.parse(content).getAsJsonObject();

            userName = json.get("userName").getAsString();
            password = json.get("password").getAsString();
            first = json.get("first").getAsString();
            last = json.get("last").getAsString();
            phone = json.get("phone").getAsString();
            email = json.get("email").getAsString();

            uf.createUser(userName, password, first, last, phone, email);
           

            jObj.addProperty("userName", userName);
            jObj.addProperty("password", password);
            jObj.addProperty("first", first);
            jObj.addProperty("last", last);
            jObj.addProperty("phone", phone);
            jObj.addProperty("email", email);

            jsonString = gson.toJson(jObj);
            statusCode = 200;

        } catch (Exception e) {
            String temp = uf.getUserByUserId(userName).getUserName();
            if(userName.equalsIgnoreCase(temp)){
            jObj.addProperty("error", "That username is not available.");
            jsonString = gson.toJson(jObj);
            statusCode = 403;
            }else{
            jObj.addProperty("error", "Unknown error occured, please try again later.");
            jsonString = gson.toJson(jObj);
            statusCode = 403;    
            }
        }
        
        return Response.status(statusCode).entity(gson.toJson(jObj)).type(MediaType.APPLICATION_JSON).build();
        }

}
