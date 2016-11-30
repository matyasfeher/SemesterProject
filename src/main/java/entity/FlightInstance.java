package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Acer
 */
@Entity
public class FlightInstance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String flightId;
    private String date;
    private String time;
    private String availableSeats;
    private String price;

    @ManyToOne
    private Flight flight;
    
    @OneToMany
    private List<Reservation> reservationList;
    
    public FlightInstance() {
    }

    public FlightInstance(String flightId, String date, String time, String availableSeats, String price) {
        this.flightId = flightId;
        this.date = date;
        this.time = time;
        this.availableSeats = availableSeats;
        this.price = price;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(String availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    
    
}
