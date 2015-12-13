package facades;

import deploy.DeploymentConfiguration;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.Passenger;
import timeTravel.entities.Reservation;

public class ReservationFacade {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
     
    public void setReservation(String flightId, int numberOfSeats,String reserveeName, String reservePhone,String reserveeEmail, List<Passenger> passengers){
    
        EntityManager em = emf.createEntityManager();
        Reservation reservation = new Reservation(); 
        Passenger passenger = new Passenger();
        reservation.setFlightId(flightId);
        reservation.setNumberOfSeats(numberOfSeats);
        reservation.setPassengers(passengers);
        reservation.setReservePhone(reservePhone);
        reservation.setReserveeEmail(reserveeEmail);
        reservation.setReserveeName(reserveeName);
        
        em.getTransaction().begin();
        
        for(Passenger p : passengers){
            passenger.setFirstName(p.getFirstName());
            passenger.setLastName(p.getLastName());
            passenger.setReservation(reservation);
            em.persist(passenger);
        }em.flush();
        
        em.persist(reservation);
        
        em.getTransaction().commit();
        em.close();
    }
}
