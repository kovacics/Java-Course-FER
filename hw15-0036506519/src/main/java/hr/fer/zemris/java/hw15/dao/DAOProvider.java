package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Class represents provider of the current web app data access object.
 */
public class DAOProvider {

    /**
     * Concrete DAO implementation.
     */
    private static DAO dao = new JPADAOImpl();

    /**
     * Methods returns web app DAO.
     *
     * @return DAO implementation
     */
    public static DAO getDAO() {
        return dao;
    }
}