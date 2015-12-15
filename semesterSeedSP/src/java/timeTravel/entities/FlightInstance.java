
package timeTravel.entities;

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

@Entity
public class FlightInstance implements Serializable {
    @Id
    private String flightNumber;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date departureDate;
    private int departuretime;//minutes
    private int flightTime;
    private int availableSeats;
    private int price;
    
//    @ManyToOne
//    private Flight flight;
    @ManyToOne
    private Airport fliesTo;
    @ManyToOne
    private Airport fliesFrom;
    
    
    
    
    

    public FlightInstance(){}
    
    public FlightInstance(Date departureDate, int departuretime, int flightTime, String flightNumber, int availableSeats, int price,Airport fliesFrom,Airport fliesTo) {
        this.departureDate = departureDate;
        this.departuretime = departuretime;
        this.flightTime = flightTime;
        this.flightNumber = flightNumber;
        this.availableSeats = availableSeats;
        this.price = price;
        this.fliesFrom = fliesFrom;
        this.fliesTo = fliesTo;
    }
    
    

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public int getDeparturetime() {
        return departuretime;
    }

    public void setDeparturetime(int departuretime) {
        this.departuretime = departuretime;
    }

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Airport getFliesTo() {
        return fliesTo;
    }

    public void setFliesTo(Airport fliesTo) {
        this.fliesTo = fliesTo;
    }

    public Airport getFliesFrom() {
        return fliesFrom;
    }

    public void setFliesFrom(Airport fliesFrom) {
        this.fliesFrom = fliesFrom;
    }

    public int getNumberOfSeats() {
        return availableSeats;
    }
}
