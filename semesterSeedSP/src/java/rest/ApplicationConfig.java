package rest;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(exception.ApiExceptionMapper.class);
        resources.add(exception.NotFoundExceptionMapper.class);
        resources.add(rest.Admin.class);
        resources.add(rest.Airports.class);
        resources.add(rest.CreateUser.class);
        resources.add(rest.Flights.class);
        resources.add(rest.Reservations.class);
        resources.add(rest.Test.class);
        resources.add(rest.User.class);
        resources.add(security.JWTAuthenticationFilter.class);
        resources.add(security.Login.class);
        resources.add(security.NotAuthorizedExceptionMapper.class);
        resources.add(security.RolesAllowedFilter.class);
        resources.add(timeTravel.rest.TimeTravelRESTApi.class);
    }

}
