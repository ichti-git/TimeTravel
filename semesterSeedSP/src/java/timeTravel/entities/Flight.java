
package timeTravel.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Flight implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int fligthNumber;
    private int numberOfSeats;

/********************************************************************************************
*                           TROR DENNE KLASSE SKAL SLETTES
*********************************************************************************************/
    
    public Flight(){}
    
    public Flight(int fligthNumber, int numberOfSeats) {
        this.fligthNumber = fligthNumber;
        this.numberOfSeats = numberOfSeats;
    }

    
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

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
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Flight)) {
            return false;
        }
        Flight other = (Flight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Flight[ id=" + id + " ]";
    }

}
