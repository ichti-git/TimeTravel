package facades;

import deploy.DeploymentConfiguration;
import entity.SearchLog;
import static help.DateHelp.getDateFromDateString;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.Airline;
import timeTravel.entities.Airport;

public class SearchLogFacade {
    
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    
    public void saveSearch(String origin, String destination, String dateString, int tickets) {
        EntityManager em = emf.createEntityManager();
        Airport orig = em.find(Airport.class, origin);
        Airport dest;
        if (destination == null) {
            dest = null;
        }
        else {
            dest = em.find(Airport.class, destination);
        }
        Date date;
        try {
            date = getDateFromDateString(dateString);
            SearchLog search = new SearchLog(orig, dest, date, tickets);
            em.getTransaction().begin();
            em.persist(search);
            em.getTransaction().commit();
        } catch (ParseException ex) {
            Logger.getLogger(SearchLogFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
