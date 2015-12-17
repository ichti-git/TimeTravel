    
package timeTravel.entities;

import entity.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String Destination;
    private String Origin;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date reservationDate;
    private int FlightTime;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Passenger> Passengers;
    
    public Reservation() {
    }

    public Reservation(String flightId, int numberOfSeats, List<Passenger> passengers,String reserveeEmail,String reservePhone,String reserveeName,String origin,String destination, Date date,User user,int flightTime) {
        this.flightId = flightId;
        this.numberOfSeats = numberOfSeats;
        this.Passengers = passengers;
        this.ReserveeEmail = reserveeEmail;
        this.ReservePhone = reservePhone;
        this.ReserveeName = reserveeName;
        this.reservationDate = date;
        this.Origin = origin;
        this.Destination = destination;
        this.reserveeUser = user;
        this.FlightTime = flightTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date date) {
        this.reservationDate = date;
    }

    public int getFlightTime() {
        return FlightTime;
    }

    public void setFlightTime(int FlightTime) {
        this.FlightTime = FlightTime;
    }
    
    public User getReserveeUser() {
        return reserveeUser;
    }

    public void setReserveeUser(User reserveeUser) {
        this.reserveeUser = reserveeUser;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String Origin) {
        this.Origin = Origin;
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

    public List<Passenger> getPassengers() {
        return Passengers;
    }

    public void setPassengers(List<Passenger> Passengers) {
        this.Passengers = Passengers;
    }

    

   

}
