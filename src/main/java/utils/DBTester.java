/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entity.Airline;
import entity.Flight;
import entity.FlightInstance;
import facade.AirlineDBFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        AirlineDBFacade facade = new AirlineDBFacade();
//        List<Flight> allFlight = facade.getAllFlight();
//        for (Flight f : allFlight) {
//            System.out.println("Flight: " + f.getFlightNumber() + " from: " + f.getFrom().getCode());
//        }
//        
//        List<Flight> allFlightsFrom = facade.getAllFlightsFrom("BUD");
//        System.out.println("-------ALL FLIGHT FROM CPH-------");
//        for(Flight f : allFlightsFrom) {
//            List<FlightInstance> flightInstanceByDate = facade.getFlightInstances(f);
//            System.out.println("Flight instance response: "+ flightInstanceByDate.size());
//            for (FlightInstance fi :  flightInstanceByDate) {
//                System.out.println("FlightInstance: "+ fi.getTime()+ ", " + fi.getPrice());
//            }
//        }        
//        System.out.println("-------ALL FLIGHT FROM CPH-------");
//
//        Flight flight = facade.getFlight("CPH", "BUD");
//        System.out.println("flight = " + flight.getFlightNumber() + " seats: " + flight.getSeats());
        List<FlightInstance> flightInstancesBetweenAirports = facade.getFlightInstancesBetweenAirports("CPH", "BUD", "");
        for (FlightInstance fi : flightInstancesBetweenAirports) {
            System.out.println("------------------------------------------");
            System.out.println("From: " + fi.getFlight().getFrom().getCity() + " code: "+fi.getFlight().getFrom().getCode());
            System.out.println("To: " + fi.getFlight().getTo().getCity() + " code: "+fi.getFlight().getTo().getCode());
            System.out.println("Seats: " + fi.getFlight().getSeats());
            System.out.println("Flight number: " + fi.getFlight().getFlightNumber());
            System.out.println("Flight time: " + fi.getFlight().getFlightTime());
            System.out.println("Available seats: " + fi.getAvailableSeats());
            System.out.println("Flight id: " + fi.getFlightId());
            System.out.println("Price: " + fi.getPrice());
            System.out.println("Time: " + fi.getTime());
            System.out.println("Date: " + fi.getDate());
        }
       
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
    
    
     //Validates that the date confirms to
    public static boolean validateDate(String uncertainDate) {
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = sdfISO.parse(uncertainDate);
        } catch (ParseException ex) {
            Logger.getLogger(DBTester.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
