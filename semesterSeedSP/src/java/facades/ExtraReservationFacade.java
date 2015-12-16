package facades;

import deploy.DeploymentConfiguration;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import timeTravel.entities.Reservation;

/**
 *
 * @author ichti
 */
public class ExtraReservationFacade {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory(DeploymentConfiguration.PU_NAME);
        
    public ExtraReservationFacade(){
    }

    public ExtraReservationFacade(EntityManagerFactory e) {
        emf = e;
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
    
    
}
