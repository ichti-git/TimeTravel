package facades;

import deploy.DeploymentConfiguration;
import entity.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.Passenger;
import timeTravel.entities.Reservation;

public class ReservationFacade {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
     
    public void setReservation(String flightId, int numberOfSeats, String ReserveeName, String ReservePhone, String ReserveeEmail, List<Passenger> passengers){
    
        EntityManager em = emf.createEntityManager();
        Reservation reservation = new Reservation(); 
        Passenger passenger = new Passenger();
        
      
        
        em.getTransaction().begin();
        
        
        reservation.setFlightId(flightId);
        reservation.setNumberOfSeats(numberOfSeats);
        reservation.setPassengers(passengers);
        reservation.setReserveeName(ReserveeName);
        reservation.setReservePhone(ReservePhone);
        reservation.setReserveeEmail(ReserveeEmail);
//        reservation.setReserveeUser(em.find(User.class, user));//skal måske flyttes op..
        
        
//        for(Passenger p : passengers){
//            passenger.setFirstName(p.getFirstName());
//            passenger.setLastName(p.getLastName());
//            passenger.setReservation(reservation);
//            em.persist(passenger);
//        }
        
//        em.flush();
        
        em.persist(reservation);
        em.getTransaction().commit();
        
        
        em.close();
    }
}
