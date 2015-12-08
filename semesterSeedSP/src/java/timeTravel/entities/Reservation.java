
package timeTravel.entities;

import entity.User;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
/*
{
"flightID":"MCA2345",
"Origin":"Copenhagen (CPH)"
"Destination": "Barcelona (BCN)",
"Date":"2016-02-25T11:30:00.000Z",
"FlightTime": 190,
"numberOfSeats":2,
"ReserveeName":" Peter Hansen ",
"Passengers":[
{ "firstName":"Peter",
"lastName": "Peterson"
},
{ "firstName":"Jane",
"lastName": "Peterson"
}
]
"flightID":" String ",
"Origin":"String (Friendly name + IATA)",
"Destination":"String (Friendly name + IATA)",
"Date":"ISO-8601-Date/time",
"FlightTime":"Integer (minutes)",
"numberOfSeats":"Integer",
"ReserveeName":"String",
"Passengers":[
{
"firstName":"String",
"lastName":"String"
}
]
}
*/
@Entity
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String flightId;
    private Airport origin;
    private Airport destination;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private int flightTime;
    private int numberOfSeats;
    
    private User Reservee;
    
    @OneToMany
    private List<Passenger> Passengers;
    
    
    
    
    
    public Reservation() {
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public User getReservee() {
        return Reservee;
    }

    public void setReservee(User Reservee) {
        this.Reservee = Reservee;
    }

    public List<Passenger> getPassengers() {
        return Passengers;
    }

    public void setPassengers(List<Passenger> Passengers) {
        this.Passengers = Passengers;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "entities.Reservation[ id=" + id + " ]";
    }

}
