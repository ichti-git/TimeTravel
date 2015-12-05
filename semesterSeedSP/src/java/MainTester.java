
import deploy.DeploymentConfiguration;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.FlightInstance;
import timeTravel.facade.Facade;


public class MainTester {
    
    public static void main(String[] args) {
        
        Facade facade = new Facade();
        
//        EntityManagerFactory emf;
//        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
//        EntityManager em = emf.createEntityManager();
//        
//    em.getTransaction().begin();
        List <FlightInstance> flights = facade.getFlightInstances("LAX", "1990-01-01", 40);
//         Persistence.generateSchema(DeploymentConfiguration.PU_NAME, null);
        for (FlightInstance f : flights){
            System.out.println(f.getPrice());
            System.out.println(f.getDepartureDate());
        }
    }
}
