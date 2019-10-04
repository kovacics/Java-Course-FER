package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

/**
 * Class represents provider of the current web app data access object.
 */
public class DAOProvider {

    private static DAO dao = new SQLDAO();

    /**
     * Methods returns web app DAO.
     *
     * @return DAO implementation
     */
    public static DAO getDao() {
        return dao;
    }
}