package timeTravel.rest;

import data.GenerateFlights;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
/**
 * REST Web Service
 * 
 * @author Simon T
 */
@Path("generate")
public class TimeTravelGenerateFlights {
    
    /**
     * Creates a new instance of GenericResource
     */
    public TimeTravelGenerateFlights() {
    }
    
    @GET
    @Produces("application/json")
    @Path("{n}")
    public String generateFlights(@PathParam("n") int n) {
        GenerateFlights gen = new GenerateFlights();
        gen.generateFlights(n);
        return "{\"msg\":\"ok\"}";
    }
    @Context
    private UriInfo context;

    
    

}
