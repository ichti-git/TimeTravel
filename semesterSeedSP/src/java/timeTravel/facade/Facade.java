package timeTravel.facade;

import deploy.DeploymentConfiguration;
import entity.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.Airline;
import timeTravel.entities.Airport;
import timeTravel.entities.FlightInstance;
import timeTravel.entities.Passenger;
import timeTravel.entities.Reservation;


public class Facade {
    
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
    
    
/*****************************************************************
*                           facade getters
*****************************************************************/
    
    public List<FlightInstance> getFlightInstances(String origin, String date, int numTickets){
        date = date.substring(0, 10);
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
    
    public FlightInstance getFlightInstance(String flightId){
        
        emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        FlightInstance flightInstance = em.find(FlightInstance.class, flightId);
        
        em.getTransaction().commit();
        em.close();
        return flightInstance;
    } 
    

    public List<FlightInstance> getFlightInstances(String origin,String destination, String date, int numTickets){
             emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        date = date.substring(0, 10);
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
   
    public List<Airline> getAirlines() {
        EntityManager em = emf.createEntityManager();
        List urls = em.createNativeQuery("select * from AIRLINE a", Airline.class).getResultList();
        em.close();
        return urls;
    }
    
    public List<Reservation> getReservationsByUser(User user){
        return getReservationsByUsername(user.getUserName());
    }
    
    public List<Reservation> getReservationsByUsername(String userName){
        EntityManager em = emf.createEntityManager();    
        
        
        List reservations = em.createNativeQuery("select * from RESERVATION r where r.RESERVEEUSER_USERNAME='"+userName+"';",Reservation.class).getResultList();
        em.close();                               
        return reservations;
    }
    
    public List<Reservation> getReservations(){
        EntityManager em = emf.createEntityManager();
        List reservations = em.createNativeQuery("select * from RESERVATION r",Reservation.class).getResultList();
        em.close();
        return reservations;
    }
    
    public List<Airport> getAirports() {
        EntityManager em = emf.createEntityManager();
        List airports = em.createNativeQuery("SELECT * FROM AIRPORT a", Airport.class).getResultList();
        em.close();
        return airports;
    }
    
    
/*****************************************************************
*                           facade setters
*****************************************************************/
     
    public void addFlightInstance(String date, int numberOfSeats, int price, 
                                  String flightId, int travelTime, String destination, String origin) {
        SimpleDateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date2 = sdfISO.parse(date);        
            EntityManager em = emf.createEntityManager();
            try {
                Airport from = em.find(Airport.class, origin);
                Airport to = em.find(Airport.class, destination);
                if (from == null || to == null) {
                    throw new Exception("Airport not found");
                }
                else {
                    FlightInstance f = new FlightInstance(date2, 0, travelTime, flightId, numberOfSeats, price, from, to);
                    em.getTransaction().begin();
                    em.persist(f);
                    em.getTransaction().commit();
                }
            }
            finally {
                em.close();
            }
        } catch (ParseException ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex) {
            Logger.getLogger(Facade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  
     

 
    
    
//     public void setFlight(Flight flight){
//         emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
//         EntityManager em = emf.createEntityManager();
//         Flight newFlight = flight;
//         em.getTransaction().begin();
//         em.persist(newFlight);
//         em.getTransaction().commit();
//         em.close();
//     }
//     
//      public void setFlightInstance(FlightInstance flightInstance){
//         emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
//         EntityManager em = emf.createEntityManager();
//         FlightInstance newFlightInstance = flightInstance;
//         em.getTransaction().begin();
//         em.persist(newFlightInstance);
//         em.getTransaction().commit();
//         em.close();
//     }
//      
//      public void setPassenger(Passenger passenger){
//         emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
//         EntityManager em = emf.createEntityManager();
//         Passenger newPassenger = passenger;
//         em.getTransaction().begin();
//         em.persist(newPassenger);
//         em.getTransaction().commit();
//         em.close();
//     }
      
//      public void setReservation(Reservation reservation){
//         emf = Persistence.createEntityManagerFactory("PU-Local");
//         EntityManager em = emf.createEntityManager();
//         Reservation newReservation = reservation;
//         em.getTransaction().begin();
//         em.persist(newReservation);
//         em.getTransaction().commit();
//         em.close();
//     }
      
//      public void setAirport(Airport airport){
//         emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
//         EntityManager em = emf.createEntityManager();
//         Airport newAirport = airport;
//         em.getTransaction().begin();
//         em.persist(newAirport);
//         em.getTransaction().commit();
//         em.close();
//     }

  
     
     

}
