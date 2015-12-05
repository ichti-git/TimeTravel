package timeTravel.facade;

import deploy.DeploymentConfiguration;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.Airport;
import timeTravel.entities.Flight;
import timeTravel.entities.FlightInstance;
import timeTravel.entities.Passenger;
import timeTravel.entities.Reservation;


public class Facade {
    
    private EntityManagerFactory emf;
    
    
/*****************************************************************
*                           facade getters
*****************************************************************/
    
       //denne skal laves om!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List getAirportByIATAcode(int IATA){ 
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List instances = em.createQuery("select a from Airport a",Airport.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return instances;
    }
    
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!IKKE FÆRDIG!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public List getFlightInstance(String fliesTo, String fliesFrom, Date date, int numberOfTickets){ 
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List instances = em.createQuery("select a from Airport a",Airport.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return instances;
    }
    
    
        
        
    
//   select * from person p join person_hobby ph  where p.`ID`=ph.`persons_ID` and  ph.hobbies_id="+hobby.getId()
    
    public List<FlightInstance> getFlightInstances(String origin, String date, int numTickets){
            
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List FlightInstances = em.createNativeQuery("select * from FLIGHTINSTANCE f where "
                 + "f.FLIESFROM_IATACODE ='"+origin+"'"
                 + "and f.DEPARTUREDATE='"+date+"'" 
                 + "and f.AVAILABLESEATS > '"+numTickets+"'" ,FlightInstance.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return FlightInstances;
    }   

     public List<FlightInstance> getFlightInstances(String origin,String destination, String date, int numTickets){
             emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List FlightInstances = em.createNativeQuery("select * from FLIGHTINSTANCE f where "
                 + "f.FLIESFROM_IATACODE ='"+origin+"'"
                 + "and f.DEPARTUREDATE='"+date+"'" 
                + "and f.FLIESTO_IATACODE ='"+destination+"'" 
                 + "and f.AVAILABLESEATS > '"+numTickets+"'" ,FlightInstance.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return FlightInstances;
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
      
      public void setPassenger(Passenger passenger){
         emf = Persistence.createEntityManagerFactory("PU-Local");
         EntityManager em = emf.createEntityManager();
         Passenger newPassenger = passenger;
         em.getTransaction().begin();
         em.persist(newPassenger);
         em.getTransaction().commit();
         em.close();
     }
      
      public void setReservation(Reservation reservation){
         emf = Persistence.createEntityManagerFactory("PU-Local");
         EntityManager em = emf.createEntityManager();
         Reservation newReservation = reservation;
         em.getTransaction().begin();
         em.persist(newReservation);
         em.getTransaction().commit();
         em.close();
     }
      
      public void setAirport(Airport airport){
         emf = Persistence.createEntityManagerFactory("PU-Local");
         EntityManager em = emf.createEntityManager();
         Airport newAirport = airport;
         em.getTransaction().begin();
         em.persist(newAirport);
         em.getTransaction().commit();
         em.close();
     }
     
     
     

}
