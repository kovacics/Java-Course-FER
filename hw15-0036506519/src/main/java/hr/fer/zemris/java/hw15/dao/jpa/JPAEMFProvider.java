package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;


/**
 * Class represents provider of the entity manager factory.
 */
public class JPAEMFProvider {

    /**
     * Entity manager factory.
     */
    private static EntityManagerFactory emf;

    /**
     * Method returns entity manager factory.
     *
     * @return entity manager factory
     */
    public static EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * Method sets entity manager factory.
     *
     * @param emf entity manager factory to set
     */
    public static void setEmf(EntityManagerFactory emf) {
        JPAEMFProvider.emf = emf;
    }
}