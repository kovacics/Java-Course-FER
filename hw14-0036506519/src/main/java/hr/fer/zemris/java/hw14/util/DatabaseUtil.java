package hr.fer.zemris.java.hw14.util;

import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Properties;

/**
 * Util method with many methods for working with database.
 */
public class DatabaseUtil {

    /**
     * SQL query that creates polls table.
     */
    public static final String POLLS_TABLE_CREATE = "CREATE TABLE Polls\n" +
            " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
            " title VARCHAR(150) NOT NULL,\n" +
            " message CLOB(2048) NOT NULL\n" +
            ")";

    /**
     * SQL query that creates poll options table.
     */
    public static final String POLL_OPTIONS_TABLE_CREATE = "CREATE TABLE PollOptions\n" +
            " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
            " optionTitle VARCHAR(100) NOT NULL,\n" +
            " optionLink VARCHAR(150) NOT NULL,\n" +
            " pollID BIGINT,\n" +
            " votesCount BIGINT,\n" +
            " FOREIGN KEY (pollID) REFERENCES Polls(id)\n" +
            ")";

    /**
     * Helping method for getting derby jdbc connection url.
     *
     * @param dbProp database properties resource bundle
     * @return connection url
     * @throws MissingResourceException if database properties file is not valid
     */
    public static String getDerbyConnectionUrl(Properties dbProp) throws MissingResourceException {
        String dbName = dbProp.getProperty("name");
        String host = dbProp.getProperty("host");
        String port = dbProp.getProperty("port");
        String user = dbProp.getProperty("user");
        String password = dbProp.getProperty("password");

        return "jdbc:derby://" + host + ":" + port + "/" + dbName + ";" + "user=" + user + ";password=" + password;
    }

    /**
     * Method that executes given SQL query.
     *
     * @param query query to execute
     * @throws SQLException if sql error happens
     */
    public static void executeQuery(String query) throws SQLException {
        PreparedStatement statement = SQLConnectionProvider.getConnection().prepareStatement(query);
        statement.executeUpdate();
    }

    /**
     * Method checks if table exist in the database.
     *
     * @param tableName table name
     * @return true if exist, false otherwise
     * @throws SQLException if sql error happens
     */
    public static boolean tableExistInDatabase(String tableName) throws SQLException {
        DatabaseMetaData dbmd = SQLConnectionProvider.getConnection().getMetaData();
        ResultSet rs = dbmd.getTables(null, null, tableName.toUpperCase(), null);
        return rs.next();
    }

    /**
     * Method loads all tables data.
     *
     * @param filepath filepath of the tables data
     * @throws IOException  if i/o error happens
     * @throws SQLException if sql error happens
     */
    public static void loadAllTables(String filepath) throws IOException, SQLException {
        List<String> lines = Files.readAllLines(Paths.get(filepath));

        for (int i = 0; i < lines.size(); ) {
            String line = lines.get(i++);
            String pollID = addNewPoll(line);

            while (true) {
                if (i >= lines.size()) break;
                line = lines.get(i++);
                if (line.trim().isEmpty()) break;
                addNewPollOption(line, pollID);
            }
        }
    }

    /**
     * Method checks if table is empty.
     *
     * @param tableName table name
     * @return true if empty, false otherwise
     * @throws SQLException if sql error happens
     */
    public static boolean isEmptyTable(String tableName) throws SQLException {
        PreparedStatement ps = SQLConnectionProvider.getConnection().prepareStatement("SELECT * FROM " + tableName);
        ResultSet rs = ps.executeQuery();
        return !rs.next();
    }

    //*************************************
    //          HELPING METHODS
    //*************************************


    /**
     * Private method that adds new poll option in the poll options table.
     *
     * @param line   line of the textual file representing poll option
     * @param pollID id of the poll whose poll option is to be added
     * @throws SQLException if sql error happens
     */
    private static void addNewPollOption(String line, String pollID) throws SQLException {
        Objects.requireNonNull(pollID);

        PreparedStatement pst = SQLConnectionProvider.getConnection().prepareStatement(
                "INSERT INTO PollOptions (optionTitle,optionLink, pollID, votesCount) values (?,?,?,?)");

        String[] parts = line.split("\\t");
        pst.setString(1, parts[0]);
        pst.setString(2, parts.length == 2 ? parts[1] : "");
        pst.setString(3, pollID);
        pst.setInt(4, 0);

        pst.executeUpdate();
    }

    /**
     * Private method that adds new poll in the polls table.
     *
     * @param line line representing new poll
     * @return id of the added poll
     * @throws SQLException if sql error happens
     */
    private static String addNewPoll(String line) throws SQLException {
        PreparedStatement pst = SQLConnectionProvider.getConnection().prepareStatement(
                "INSERT INTO POLLS (title, message) values (?,?)",
                Statement.RETURN_GENERATED_KEYS);

        String[] parts = line.split("\\t");

        pst.setString(1, parts[0]);
        pst.setString(2, parts[1]);

        pst.executeUpdate();

        try (ResultSet rset = pst.getGeneratedKeys()) {
            if (rset != null && rset.next()) {
                return String.valueOf(rset.getLong(1));
            }
        }
        return null;
    }
}
