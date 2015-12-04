package timeTravel.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.Flight;
import timeTravel.entities.FlightInstance;

public class Facade {
    
    private EntityManagerFactory emf;
    
    
/*****************************************************************
*                           facade getters
*****************************************************************/
    
    
    
    
    
    
    public void getFlightInstances(String from, String date, int numTickets){
            
    }   

     public void getFlightInstances(String from,String to, String date, int numTickets){
            
    }  
   
    
    //api/flightreservation
    
     
/*****************************************************************
*                           facade setters
*****************************************************************/
 
     public void setFlight(Flight flight){
         emf = Persistence.createEntityManagerFactory("PU-Local");
         EntityManager em = emf.createEntityManager();
         Flight newFlight = flight;
         em.getTransaction().begin();
         em.persist(newFlight);
         em.getTransaction().commit();
         em.close();
     }
     
      public void setFlightInstance(FlightInstance flightInstance){
         emf = Persistence.createEntityManagerFactory("PU-Local");
         EntityManager em = emf.createEntityManager();
         FlightInstance newFlightInstance = flightInstance;
         em.getTransaction().begin();
         em.persist(newFlightInstance);
         em.getTransaction().commit();
         em.close();
     }
     
     
     
//       public void         addPerson(Person p){
//        emf = Persistence.createEntityManagerFactory("CA2_projectPU");
//        EntityManager em = emf.createEntityManager();
//        Person newPerson = p;
//        em.getTransaction().begin();
//        em.persist(newPerson);
//        em.getTransaction().commit();
//        em.close();
//    }
}
