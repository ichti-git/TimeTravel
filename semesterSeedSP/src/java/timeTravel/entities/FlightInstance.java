
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
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date departureDate;
    private int departuretime;//minuttes
    private int flightTime;
    private int flightNumber;
    private String destination;
    private int availableSeats;
    private int price;
    
    
    
    @ManyToOne
    private Airport fliesTo;
    
    @ManyToOne
    private Airport fliesFrom;
   
    @OneToMany
    private List <Reservation> reservations;
    
    
    
    

    public FlightInstance(){}
    
    public FlightInstance(Date departureDate, int departuretime, int flightTime, int flightNumber, String destination, int availableSeats, int price) {
        this.departureDate = departureDate;
        this.departuretime = departuretime;
        this.flightTime = flightTime;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.price = price;
    }
    
    
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

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

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
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
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FlightInstance)) {
            return false;
        }
        FlightInstance other = (FlightInstance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FlightInstance[ id=" + id + " ]";
    }

}
