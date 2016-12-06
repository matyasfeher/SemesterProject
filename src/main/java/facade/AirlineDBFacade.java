package facade;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import entity.*;
import java.util.ArrayList;
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
    
    private Flight getFlightFromTo(String from, String to) {
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
    
    private List<Flight> getFlightsFrom(String from, String date) {
        try {
            Flight flight;
            EntityManager em = getEntityManager();
            
            Query query = em.createQuery("SELECT f FROM Flight f WHERE f.from = :origin");
            query.setParameter("origin", em.find(Airport.class, from));
            List resultList = query.getResultList();
            return resultList;
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
    
    //Returns all flight instances for a single flight between two airports
    public List<FlightInstance> getFlightInstancesBetweenAirports(String from, String to, String date) {
        Flight flight = getFlightFromTo(from, to);
        List<FlightInstance> flightInstances = getFlightInstancesByFlight(flight);
        return flightInstances;
    }
    
    //Gives back all the flight instances for each flight from a certain airport
    public List<FlightInstance> getFlightInstancesFromAirport(String from, String date) {
        List<FlightInstance> flightInstanceList = new ArrayList();
        
        List<Flight> flightsList = getFlightsFrom(from, date);
        for (Flight f : flightsList) {
            List<FlightInstance> flightInstancesByFlight = getFlightInstancesByFlight(f);
            for (FlightInstance fi : flightInstancesByFlight) {
                flightInstanceList.add(fi);
            }
        }
        return flightInstanceList;
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

    public void addAirline(Airline a) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Airline getAirlineByName(String airlineName) {
        Airline a;
        EntityManager em = getEntityManager();
        a = em.find(Airline.class, airlineName);
        return a;
    }

    public void addFlight(Flight f) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(f);
            System.out.println("Persisted: " + f.toString());
            em.getTransaction().commit();
            System.out.println("Commited: " + f.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
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

    public void deleteFlightByFlightNumber(String flightNumber) {
        EntityManager em = getEntityManager();
        Flight f = em.find(Flight.class, flightNumber);
        em.remove(f);
    }

    
}
