    
package timeTravel.entities;

import entity.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
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
    
    @ManyToOne
    private User Reservee;
    @ManyToOne
    private FlightInstance flightInstance;
//    @OneToMany(mappedBy = "Reservation", cascade = CascadeType.PERSIST)
//    private List<Passenger> Passengers;
    
    @OneToMany(cascade = CascadeType.MERGE)
    private List<Passenger> Passengers;
    
    public Reservation(FlightInstance flightInstance,User reservee, int numberOfSeats,List<Passenger> passengers) {
        this.flightInstance = flightInstance;
        this.Reservee = reservee;
        this.Passengers = passengers;
    }
    
    public Reservation(FlightInstance flightInstance,User reservee, int numberOfSeats) {
        this.flightInstance = flightInstance;
        this.Reservee = reservee;
        
    }
    
    public Reservation() {
    }

    public User getReservee() {
        return Reservee;
    }

    public void setReservee(User Reservee) {
        this.Reservee = Reservee;
    }

//    public List<String> getPassengersAsStrings() {
//        List<String> passengersAsStrings = new ArrayList();
//        for (Passenger passenger : Passengers) {
//            passengersAsStrings.add(passenger.getFirstName() + " " + passenger.getLastName());
//        }
//        return passengersAsStrings;
//    }
    
    public void addPassenger(Passenger passenger) {
        Passengers.add(passenger);
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

    public FlightInstance getFlightInstance() {
        return flightInstance;
    }

    public void setFlightInstance(FlightInstance flightInstance) {
        this.flightInstance = flightInstance;
    }

   

}
