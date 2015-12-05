
package timeTravel.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Flight implements Serializable {
    
    @Id
    private int fligthNumber;
    private int numberOfSeats;

    @OneToMany(mappedBy = "flight")
    private List <FlightInstance> FlightInstances;
    
    @ManyToOne
    private Airline airline;
    
    public Flight(){}
    
    public Flight(int fligthNumber, int numberOfSeats) {
        this.fligthNumber = fligthNumber;
        this.numberOfSeats = numberOfSeats;
    }

    public int getFligthNumber() {
        return fligthNumber;
    }

    public void setFligthNumber(int fligthNumber) {
        this.fligthNumber = fligthNumber;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public List<FlightInstance> getFlightInstances() {
        return FlightInstances;
    }

    public void setFlightInstances(List<FlightInstance> FlightInstances) {
        this.FlightInstances = FlightInstances;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }
    
    
}
