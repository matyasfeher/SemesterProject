package facade;

import entity.Airport;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Acer
 */
public class AirportFacade {

    private EntityManager getEntityManager() {
        EntityManager em = Persistence.createEntityManagerFactory("pu").createEntityManager();
        return em;
    }

    public void addAirports(Airport a) {
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

    public Airport getAirportByCode(String code) {
        EntityManager em = getEntityManager();
        Airport a;
        a = em.find(Airport.class, code);
        return a;
    }

    public Airport getAirportByName(String name) {
        EntityManager em = getEntityManager();
        Airport a;
        Query query = em.createQuery("SELECT a FROM AIRPORT a WHERE a.name =:" + name);
        a = (Airport) query.getSingleResult();
        return a;
    }
}
