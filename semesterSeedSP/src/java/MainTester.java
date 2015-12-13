
import deploy.DeploymentConfiguration;
import entity.User;
import facades.ReservationFacade;
import facades.UserFacade;
import timeTravel.facade.Facade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.FlightInstance;
import timeTravel.entities.Passenger;



public class MainTester {
    
    public static void main(String[] args) {
        
//        Persistence.generateSchema(DeploymentConfiguration.PU_NAME, null);

        
        UserFacade uf = new UserFacade();
        
        //uf.createUser("testuser", "testpassword", "testfirstName",  "testlastName", "reserveeEmail@hotmail.com", "ReservePhone");
        
        List<Passenger> passengers = new ArrayList<Passenger>();
        Passenger p = new Passenger("hans" ,"mogensen");
        Passenger p2 = new Passenger("morten" ,"horner");
        passengers.add( p );
        passengers.add( p2 );

        ReservationFacade facade = new ReservationFacade();
        facade.setReservation("flight1",2,"testuser", "ReservePhone","reserveeEmail@hotmail.com",  passengers);
                            
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        

        
        
//        UserFacade facade = new UserFacade(Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME));
//        String userName = "testting";
//        String password = "testting";
//        String firstName = "testting";
//        String lastName = "testting";
//        String email = "testting";
//        String phone = "testting";
//        EntityManagerFactory emf;
//        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
//        EntityManager em = emf.createEntityManager();
//        
//    em.getTransaction().begin();
        //List <FlightInstance> flights = facade.getFlightInstances("LAX", "1990-01-01", 40);
//         Persistence.generateSchema(DeploymentConfiguration.PU_NAME, null);
//        for (FlightInstance f : flights){
//            System.out.println(f.getPrice());
//            System.out.println(f.getDepartureDate());
//        }
//        User user = new User();
//        facade.createUser(userName, password, firstName, lastName, email, phone);
//        System.out.println(""+user);
    }
}
