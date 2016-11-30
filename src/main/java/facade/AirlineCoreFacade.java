package facade;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import entity.*;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Acer
 */
public class AirlineCoreFacade {

    private EntityManager getEntityManager() {
        EntityManager em = Persistence.createEntityManagerFactory("pu").createEntityManager();
        return em;
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
