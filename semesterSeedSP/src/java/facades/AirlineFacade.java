package facades;

import deploy.DeploymentConfiguration;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.Airline;

public class AirlineFacade {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    
    public Airline getAirline(String airline){
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Airline A = em.find(Airline.class, airline);
        return A;
    }
    public List<Airline> getAirlines() {
        EntityManager em = emf.createEntityManager();
        List urls = em.createNativeQuery("select * from AIRLINE a", Airline.class).getResultList();
        em.close();
        return urls;
    }
}
