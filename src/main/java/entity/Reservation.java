/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Acer
 */
@Entity
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String reserveeFirstName;
    private String reserveeLastName;
    private String numberOfSeats;
    private String price;
    @Temporal(TemporalType.TIMESTAMP)
    private String reserved;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Passenger> passengerList;
    
    @ManyToOne
    private Reservation reservation;

    public Reservation() {
    }

    public Reservation(String reserveeFirstName, String reserveeLastName, String numberOfSeats, String price) {
        this.reserveeFirstName = reserveeFirstName;
        this.reserveeLastName = reserveeLastName;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReserveeFirstName() {
        return reserveeFirstName;
    }

    public void setReserveeFirstName(String reserveeFirstName) {
        this.reserveeFirstName = reserveeFirstName;
    }

    public String getReserveeLastName() {
        return reserveeLastName;
    }

    public void setReserveeLastName(String reserveeLastName) {
        this.reserveeLastName = reserveeLastName;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public List<Passenger> getPassengerList() {
        return passengerList;
    }

    public void setPassengerList(List<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
    
    
    
}
