package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Acer
 */
@Entity
public class Airline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String name;

    @OneToMany
    private List<Flight> airlineFlightList;

    public Airline() {
    }

    public Airline(String name, List<Flight> airlineFlight) {
        this.name = name;
        this.airlineFlightList = airlineFlight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Flight> getAirlineFlightList() {
        return airlineFlightList;
    }

    public void setAirlineFlightList(List<Flight> airlineFlightList) {
        this.airlineFlightList = airlineFlightList;
    }

    
}
