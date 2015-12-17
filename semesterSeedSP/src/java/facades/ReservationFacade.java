package facades;

import deploy.DeploymentConfiguration;
import entity.User;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.Passenger;
import timeTravel.entities.Reservation;

public class ReservationFacade {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
     
    public void setReservation(String flightId, int numberOfSeats, String ReserveeName, String ReservePhone, String ReserveeEmail, List<Passenger> passengers, String origin, String destination,Date date,  User user, int flightTime){
    
        EntityManager em = emf.createEntityManager();
        Reservation reservation = new Reservation();
      
        
        reservation.setFlightId(flightId);
        reservation.setNumberOfSeats(numberOfSeats);
        reservation.setPassengers(passengers);
        reservation.setReserveeName(ReserveeName);
        reservation.setReservePhone(ReservePhone);
        reservation.setReserveeEmail(ReserveeEmail);
        reservation.setOrigin(origin);
        reservation.setDestination(destination);
        reservation.setReservationDate(date);
        reservation.setReserveeUser(user);   
        reservation.setFlightTime(flightTime);
        
        em.getTransaction().begin();
        em.persist(reservation);
        em.getTransaction().commit();
        em.close();
    }
    public Reservation getReservationById(int id) {
        EntityManager em = emf.createEntityManager();
        Reservation reservation = em.find(Reservation.class, id);
        return reservation;
    }

    public void deleteReservation(int id) {
        EntityManager em = emf.createEntityManager();
        Reservation reservation = em.find(Reservation.class, id);
        if (reservation != null) {
            em.getTransaction().begin();
            em.remove(reservation);
            em.getTransaction().commit();
        }
        em.close();
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
}
