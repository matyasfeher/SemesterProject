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
public class Flight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String flightNumber;
    private String seats;
    private int flightTime;
    
    @OneToMany
    private List<FlightInstance> flightList;
    @ManyToOne
    private Airport from;
    @ManyToOne
    private Airport to;

    public Flight(String flightNumber, String seats, int flightTime, Airport from, Airport to) {
        this.flightNumber = flightNumber;
        this.seats = seats;
        this.flightTime = flightTime;
        this.from = from;
        this.to = to;
    }
    
    public Flight() {
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public int getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(int flightTime) {
        this.flightTime = flightTime;
    }

    public List<FlightInstance> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<FlightInstance> flightList) {
        this.flightList = flightList;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

}
