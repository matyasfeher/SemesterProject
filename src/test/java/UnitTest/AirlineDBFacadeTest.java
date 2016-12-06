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

/**
 *
 * @author Nicolai
 */
public class AirlineDBFacadeTest {
    AirlineDBFacade AirlineDBFacade = new AirlineDBFacade();
    
    @Test
    public void testGetFlight(){
        String from = "BUD";
        String to = "CPH";
        int flightTime = 146;
        String seats = "100";
        String flightNumber = "123456";
        AirlineDBFacade ADBFacade = new AirlineDBFacade();
        Flight flight = ADBFacade.getFlightByFlightNumber("123456");
        flight.getFrom().getCode();
        Flight result = ADBFacade.getFlight(from, to);
        
        assertEquals(to, flight.getTo().getCode());
        assertEquals(from, flight.getFrom().getCode());
        assertEquals(flightTime, flight.getFlightTime());
        assertEquals(seats, flight.getSeats());
        assertEquals(flightNumber, flight.getFlightNumber());
        
    }
    
    @Test
    public void testGetAllFlights(){
        AirlineDBFacade ADBFacade = new AirlineDBFacade();
        List<Flight> flights = ADBFacade.getAllFlight();
        
        for(Flight flight : flights){
            assertNotNull(flight);
        }
    }
    
    @Test
    public void testGetFlightInstancesBetweenAirports(){
        String from = "CPH";
        String to = "BUD";
        String date = "01-01-2017-T00:00:00.000Z";
        AirlineDBFacade ADBFacade = new AirlineDBFacade();
        List<FlightInstance> flightInstances = ADBFacade.getFlightInstancesBetweenAirports(from, to, date);
        
        
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
