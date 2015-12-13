    
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
    private int numberOfSeats;
    private String ReserveeName;
    private String ReservePhone;
    private String ReserveeEmail;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Passenger> Passengers;
    
    
    
    public Reservation() {
    }

    public Reservation(String flightId, int numberOfSeats, String ReserveeName, String ReservePhone, String ReserveeEmail, List<Passenger> Passengers) {
        this.flightId = flightId;
        this.numberOfSeats = numberOfSeats;
        this.ReserveeName = ReserveeName;
        this.ReservePhone = ReservePhone;
        this.ReserveeEmail = ReserveeEmail;
        this.Passengers = Passengers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Passenger> getPassengers() {
        return Passengers;
    }

    public void setPassengers(List<Passenger> Passengers) {
        this.Passengers = Passengers;
    }

    

   

}
