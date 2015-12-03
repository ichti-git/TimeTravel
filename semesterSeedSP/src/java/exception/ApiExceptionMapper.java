/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
/**
 *
 * @author ichti (Simon T)
 */
@Provider
public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Context
    ServletContext context;

    @Override
    public Response toResponse(ApiException ex) {
        JsonObject error = new JsonObject();
        error.addProperty("httpError", ex.getHttpError());
        error.addProperty("errorCode", ex.getErrorCode());
        error.addProperty("message", ex.getMessage());
        return Response.status(ex.getHttpError()).entity(gson.toJson(error)).type(MediaType.APPLICATION_JSON).build();
    }
}
