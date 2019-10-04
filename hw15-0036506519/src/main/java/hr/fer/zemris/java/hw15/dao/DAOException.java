package hr.fer.zemris.java.hw15.dao;

/**
 * Exception specific for DAO interface.
 */
public class DAOException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }
}