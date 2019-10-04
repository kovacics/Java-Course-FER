package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * Class represents provider of the connection which this thread can use.
 * This way we can use same connection in many methods without passing
 * that connection as method parameter.
 */
public class SQLConnectionProvider {

    /**
     * Map which maps thread to connection.
     */
    private static ThreadLocal<Connection> connections = new ThreadLocal<>();

    /**
     * Sets connection for the running thread.
     *
     * @param con connection to set
     */
    public static void setConnection(Connection con) {
        if (con == null) {
            connections.remove();
        } else {
            connections.set(con);
        }
    }

    /**
     * Getter for the connection which can be used in this thread.
     *
     * @return connection of the thread
     */
    public static Connection getConnection() {
        return connections.get();
    }
}