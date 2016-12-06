package facade;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import entity.*;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Acer
 */
public class AirlineDBFacade {

    private EntityManager getEntityManager() {
        EntityManager em = Persistence.createEntityManagerFactory("pu").createEntityManager();
        return em;
    }
    
    public Flight getFlight(String from, String to) {
        try {
            Flight flight;
            EntityManager em = getEntityManager();
            
            Query query = em.createQuery("SELECT f FROM Flight f WHERE f.from = :origin AND f.to = :destination");
            query.setParameter("origin", em.find(Airport.class, from));
            query.setParameter("destination", em.find(Airport.class, to));
            flight = (Flight) query.getSingleResult();
            return flight;
        } catch (Exception ex) {
            System.out.println("Exception in getFLight: "+ex.toString());
            return null;
        }
    }
    
    public List<Flight> getAllFlightsFrom(String from) {
        try {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("SELECT f FROM Flight f WHERE f.from = :origin");
            query.setParameter("origin", em.find(Airport.class, from));
            List<Flight> resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            System.out.println("Exception in getAllFightsFrom: "+ex.toString());
            return null;
        }
    }
    
    public List<FlightInstance> getFlightInstancesBetweenAirports(String from, String to, String date) {
        Flight flight = getFlight(from, to);
        List<FlightInstance> flightInstances = getFlightInstancesByFlight(flight);
        return flightInstances;
    }
    
   
    
    public List<FlightInstance> getFlightInstancesByFlight(Flight f) {
    List<FlightInstance> list = null;
        try {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("SELECT f FROM FlightInstance f WHERE f.flight = :flight");
            query.setParameter("flight", f);
            list = query.getResultList();
            return list;
        } catch (Exception e) {
            System.out.println("Exception: "+ e.getMessage());
            return list;
        }
    }

    public Airline getAirlineByName(String airlineName) {
        Airline a;
        EntityManager em = getEntityManager();
        a = em.find(Airline.class, airlineName);
        return a;
    }


    public Flight getFlightByFlightNumber(String flightNumber) {
        Flight f;
        EntityManager em = getEntityManager();
        f = em.find(Flight.class, flightNumber);
        return f;
    }

    public List<Flight> getAllFlight() {
        List<Flight> flightList;
        EntityManager em = getEntityManager();
        Query query = em.createQuery("SELECT f FROM Flight f");
        flightList = (List<Flight>)query.getResultList();
        return flightList;
    }

}
