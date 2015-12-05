package timeTravel.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Airline implements Serializable {
    
    @Id
    private String name;
    
    @OneToMany(mappedBy = "airline")
    private List<Flight> flights;
}
