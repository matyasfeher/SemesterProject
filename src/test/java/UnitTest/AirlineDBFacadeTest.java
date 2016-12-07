/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UnitTest;

import entity.Flight;
import entity.FlightInstance;
import facade.AirlineDBFacade;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Nicolai
 */
public class AirlineDBFacadeTest {
    AirlineDBFacade AirlineDBFacade = new AirlineDBFacade();


//    These test are commented out because the methods are now private, if they were still public these test would have worked aswell.
//    @Test
//    public void testGetFlightFromTo(){
//        String from = "BUD";
//        String to = "CPH";
//        int flightTime = 146;
//        String seats = "100";
//        String flightNumber = "123456";
//        AirlineDBFacade ADBFacade = new AirlineDBFacade();
//        Flight flight = ADBFacade.getFlightByFlightNumber("123456");
//        flight.getFrom().getCode();
//        Flight result = ADBFacade.getFlightFromTo(from, to);
//        
//        assertEquals(to, flight.getTo().getCode());
//        assertEquals(from, flight.getFrom().getCode());
//        assertEquals(flightTime, flight.getFlightTime());
//        assertEquals(seats, flight.getSeats());
//        assertEquals(flightNumber, flight.getFlightNumber());
//        
//    }
//    
//    @Test
//    public void testGetAllFlightsFrom(){
//        String from = "CPH";
//        AirlineDBFacade ADBFacade = new AirlineDBFacade();
//        List<Flight> flights = ADBFacade.getAllFlightsFrom(from);
//        
//        for(Flight flight : flights){
//            assertNotNull(flight);
//        }
//    }
    
    
        @Test
    public void testGetFlightByFlightNumber(){
        String flightNumber = "123456";
        AirlineDBFacade ADBFacade = new AirlineDBFacade();
        Flight flight = ADBFacade.getFlightByFlightNumber("123456");
        
        assertEquals(flightNumber, flight.getFlightNumber());
    }
    
    @Ignore
            @Test
        public void testGetFlightByFlightNumberFail(){
        String flightNumber = "123456";
        AirlineDBFacade ADBFacade = new AirlineDBFacade();
        Flight flight = ADBFacade.getFlightByFlightNumber("123456");
        
        assertEquals(flightNumber, flight.getFlightNumber());
    }
    
    @Test
    public void testGetFlightInstancesBetweenAirports(){
        String from = "CPH";
        String to = "BUD";
        String date = "2017-01-01T08:00:00.000Z";
        AirlineDBFacade ADBFacade = new AirlineDBFacade();
        List<FlightInstance> flightInstances = ADBFacade.getFlightInstancesBetweenAirports(from, to, date);
        
        
        for(FlightInstance flightInstance : flightInstances){
            assertNotNull(flightInstance);
//            assertNull(flightInstance);
        }
    }
    
    @Ignore
        @Test
        public void testGetFlightInstancesBetweenAirportsFail(){
        String from = "CPH";
        String to = "BUD";
        String date = "2017-01-01T08:00:00.000Z";
        AirlineDBFacade ADBFacade = new AirlineDBFacade();
        List<FlightInstance> flightInstances = ADBFacade.getFlightInstancesBetweenAirports(from, to, date);
        
        
        for(FlightInstance flightInstance : flightInstances){
            assertNull(flightInstance);
        }
    }
    
    
    @Test
    public void testGetFlightInstancesFromAirport(){
        String from = "CPH";
        String date = "2017-01-01T08:00:00.000Z";
        AirlineDBFacade ADBFacade = new AirlineDBFacade();
        List<FlightInstance> flightInstances = ADBFacade.getFlightInstancesFromAirport(from, date);
        
        for(FlightInstance flightInstance : flightInstances){
            assertNotNull(flightInstance);
        }
    }
    
    @Test
    public void testGetFlightInstancesFromAirportFail(){
        String from = "";
        String date = "2017-01-01T08:00:00.000Z";
        AirlineDBFacade ADBFacade = new AirlineDBFacade();
        List<FlightInstance> flightInstances = ADBFacade.getFlightInstancesFromAirport(from, date);
        
        for(FlightInstance flightInstance : flightInstances){
            assertNotNull(flightInstance);
        }
    }
            
            
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
}
