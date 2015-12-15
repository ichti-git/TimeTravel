package facades;

import deploy.DeploymentConfiguration;
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
}
