    
package timeTravel.entities;

import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

@Entity
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String flightId;
    private int numberOfSeats;
    @OneToOne
    private User reserveeUser;
    
    private String ReserveeName;
    private String ReservePhone;
    private String ReserveeEmail;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Passenger> Passengers;
    
    
    
    public Reservation() {
    }

    public Reservation(String flightId, int numberOfSeats, User user, List<Passenger> Passengers,String ReserveeEmail,String ReservePhone,String ReserveeName) {
        this.flightId = flightId;
        this.numberOfSeats = numberOfSeats;
//        this.reserveeUser = user;
        this.Passengers = Passengers;
        this.ReserveeEmail = ReserveeEmail;
        this.ReservePhone = ReservePhone;
        this.ReserveeName = ReserveeName;
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReserveeName() {
        return ReserveeName;
    }

    public void setReserveeName(String ReserveeName) {
        this.ReserveeName = ReserveeName;
    }

    public String getReservePhone() {
        return ReservePhone;
    }

    public void setReservePhone(String ReservePhone) {
        this.ReservePhone = ReservePhone;
    }

    public String getReserveeEmail() {
        return ReserveeEmail;
    }

    public void setReserveeEmail(String ReserveeEmail) {
        this.ReserveeEmail = ReserveeEmail;
    }
    
    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

//    public User getReserveeUser() {
//        return reserveeUser;
//    }
//
//    public void setReserveeUser(User reserveeUser) {
//        this.reserveeUser = reserveeUser;
//    }

    public List<Passenger> getPassengers() {
        return Passengers;
    }

    public void setPassengers(List<Passenger> Passengers) {
        this.Passengers = Passengers;
    }

    

   

}
