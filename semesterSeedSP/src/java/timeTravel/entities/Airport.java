
package timeTravel.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Airport implements Serializable {
    //private static final long serialVersionUID = 1L;//??????????????????????? skal den så fjernes??????????????????????????
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String iatacode;
    private String name;
    private String city;
    private int timezone;
/*
    @OneToMany(mappedBy = "fliesTo")
    private List<FlightInstance> FlightInstancesTo;
    
    @OneToMany(mappedBy = "fliesFrom")
    private List<FlightInstance> FlightInstancesFrom;
    */
    
    public Airport(){}
    
    public Airport(String iatacode, String name, String city, int timezone) {
        this.iatacode = iatacode;
        this.name = name;
        this.city = city;
        this.timezone = timezone;
    }
/*
    public List<FlightInstance> getFlightInstancesTo() {
        return FlightInstancesTo;
    }
    
    public List<FlightInstance> getFlightInstancesFrom() {
        return FlightInstancesFrom;
    }
*/
//    public void setFlightInstances(List<FlightInstance> FlightInstancesTo) {
//        this.FlightInstances = FlightInstancesTo;
//    }
    
//    public void setFlightInstances(List<FlightInstance> FlightInstancesFrom) {
//        this.FlightInstances = FlightInstancesFrom;
//    }
    
    public String getIatacode() {
        return iatacode;
    }

    public void setIatacode(String iatacode) {
        this.iatacode = iatacode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }
    
    

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (id != null ? id.hashCode() : 0);
//        return hash;
//    }

//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof Airport)) {
//            return false;
//        }
//        Airport other = (Airport) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
//    }

//    @Override
//    public String toString() {
//        return "entities.Airport[ id=" + id + " ]";
//    }

}
