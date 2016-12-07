package facade;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import entity.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;

/**
 *
 * @author Acer
 */
public class AirlineDBFacade {

    //Returns all flight instances for a single flight between two airports
    public List<FlightInstance> getFlightInstancesBetweenAirports(String from, String to, String date) {
        Flight flight = getFlightFromTo(from, to);
        List<FlightInstance> flightInstances = getFlightInstancesByFlight(flight, date);
        return flightInstances;
    }

    //Gives back all the flight instances for each flight from a certain airport
    public List<FlightInstance> getFlightInstancesFromAirport(String from, String date) {
        List<FlightInstance> flightInstanceList = new ArrayList();

        List<Flight> flightsList = getFlightsFrom(from);
        System.out.println("FLIGHTS RETURNED SIZE: " + flightsList.size());
        for (Flight f : flightsList) {
            List<FlightInstance> flightInstancesByFlight = getFlightInstancesByFlight(f, date);
            for (FlightInstance fi : flightInstancesByFlight) {
                flightInstanceList.add(fi);
            }
        }
        return flightInstanceList;
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
            System.out.println("Exception in getFLight: " + ex.toString());
            return null;
        }
    }

    private List<Flight> getFlightsFrom(String from) {
        try {
            Flight flight;
            EntityManager em = getEntityManager();

            Query query = em.createQuery("SELECT f FROM Flight f WHERE f.from = :origin");
            query.setParameter("origin", em.find(Airport.class, from));
            List resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            System.out.println("Exception in getFLight: " + ex.toString());
            return null;
        }
    }

    private List<Flight> getAllFlightsFrom(String from) {
        try {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("SELECT f FROM Flight f WHERE f.from = :origin");
            query.setParameter("origin", em.find(Airport.class, from));
            List<Flight> resultList = query.getResultList();
            return resultList;
        } catch (Exception ex) {
            System.out.println("Exception in getAllFightsFrom: " + ex.toString());
            return null;
        }
    }

    private List<FlightInstance> getFlightInstancesByFlight(Flight f, String date) {
        DateFormat sdfISO = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateObj = new Date(System.currentTimeMillis());

        try {
            dateObj = sdfISO.parse(date + "T08:00:00.000Z");
        } catch (ParseException ex) {
            Logger.getLogger(AirlineDBFacade.class.getName()).log(Level.SEVERE, null, ex);
            try {
                dateObj = sdfISO.parse(date);
            } catch (ParseException ex1) {
                Logger.getLogger(AirlineDBFacade.class.getName()).log(Level.SEVERE, null, ex1);
                System.out.println("WARNING! Date is absolutely unparseable in getFlightInstancesByFlight");
            }
        }

        List<FlightInstance> list = null;
        try {
            EntityManager em = getEntityManager();
            Query query = em.createQuery("SELECT f FROM FlightInstance f WHERE f.flight = :flight AND f.date = :date");
            query.setParameter("flight", f);
            query.setParameter("date", dateObj);
            list = query.getResultList();
            return list;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return list;
        }
    }

    public Flight getFlightByFlightNumber(String flightNumber) {
        Flight f;
        EntityManager em = getEntityManager();
        f = em.find(Flight.class, flightNumber);
        return f;
    }

    private EntityManager getEntityManager() {
        EntityManager em = Persistence.createEntityManagerFactory("pu").createEntityManager();
        return em;
    }

}
