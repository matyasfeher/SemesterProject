package facade;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import entity.*;
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
    
    public void addFlight(Flight f) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(f);
            em.getTransaction().commit();
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
        Query query = em.createQuery("SELECT f FROM Flight f WHERE f.flightNumber = :"+flightNumber);
        f = (Flight) query.getSingleResult();
        System.out.println(f.toString());
        return f;
    }
    
    public void deleteFlightByFlightNumber(String flightNumber) {
        EntityManager em = getEntityManager();
        Flight f = em.find(Flight.class, flightNumber);
        em.remove(f);
    }
}
