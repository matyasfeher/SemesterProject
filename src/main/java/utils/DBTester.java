/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.Airline;
import entity.Flight;
import entity.FlightInstance;
import facade.AirlineCoreFacade;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author edipetres
 */
public class DBTester {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu", null);
        EntityManager em = emf.createEntityManager();
//        
        AirlineCoreFacade facade = new AirlineCoreFacade();
//        List<Flight> allFlight = facade.getAllFlight();
//        for (Flight f : allFlight) {
//            System.out.println("Flight: " + f.getFlightNumber() + " from: " + f.getFrom().getCode());
//        }
//        
        List<Flight> allFlightsFrom = facade.getAllFlightsFrom("BUD");
        System.out.println("-------ALL FLIGHT FROM CPH-------");
        for(Flight f : allFlightsFrom) {
            List<FlightInstance> flightInstanceByDate = facade.getFlightInstanceByDate(f);
            System.out.println("Flight instance response: "+ flightInstanceByDate.size());
            for (FlightInstance fi :  flightInstanceByDate) {
                System.out.println("FlightInstance: "+ fi.getTime()+ ", " + fi.getPrice());
            }
        }        
        System.out.println("-------ALL FLIGHT FROM CPH-------");
//
//        Flight flight = facade.getFlight("CPH", "BUD");
//        System.out.println("flight = " + flight.getFlightNumber() + " seats: " + flight.getSeats());

       
    }

    //Take date string in ISO 8601 and return date object
    public static Date stringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(date);
            return d;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
