/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import entity.Airport;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

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

    public Airport getAirport(String code) {
        EntityManager em = getEntityManager();
        Airport a = em.find(Airport.class, code);
        return a;
    }

}
