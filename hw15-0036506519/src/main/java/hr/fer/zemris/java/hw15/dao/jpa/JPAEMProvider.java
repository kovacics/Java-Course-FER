package hr.fer.zemris.java.hw15.dao.jpa;

import hr.fer.zemris.java.hw15.dao.DAOException;

import javax.persistence.EntityManager;

/**
 * Class represents provider of the entity manager.
 */
public class JPAEMProvider {

    /**
     * Thread local map of the entity managers, ensures safe usage of entity managers.
     */
    private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

    /**
     * Method gets entity manager.
     *
     * @return entity manager
     */
    public static EntityManager getEntityManager() {
        EntityManager em = locals.get();
        if (em == null) {
            em = JPAEMFProvider.getEmf().createEntityManager();
            em.getTransaction().begin();
            locals.set(em);
        }
        return em;
    }

    /**
     * Method closes entity manager.
     *
     * @throws DAOException if dao error happens
     */
    public static void close() throws DAOException {
        EntityManager em = locals.get();
        if (em == null) {
            return;
        }
        DAOException dex = null;
        try {
            em.getTransaction().commit();
        } catch (Exception ex) {
            dex = new DAOException("Unable to commit transaction.", ex);
        }
        try {
            em.close();
        } catch (Exception ex) {
            if (dex != null) {
                dex = new DAOException("Unable to close entity manager.", ex);
            }
        }
        locals.remove();
        if (dex != null) throw dex;
    }
}