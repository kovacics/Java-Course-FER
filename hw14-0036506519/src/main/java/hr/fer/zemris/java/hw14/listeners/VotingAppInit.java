package hr.fer.zemris.java.hw14.listeners;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.hw14.util.DatabaseUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.Properties;

/**
 * Servlet context listener which does web app initialization.
 */
@WebListener
public class VotingAppInit implements ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        String connectionURL = null;

        try {
            String realPath = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");
            Properties dbproperties = new Properties();
            try (FileInputStream in = new FileInputStream(realPath)) {
                dbproperties.load(in);
            }

            connectionURL = DatabaseUtil.getDerbyConnectionUrl(dbproperties);
        } catch (MissingResourceException | IOException e) {
            throw new RuntimeException("Error while reading the database setting file.");
        }

        ComboPooledDataSource cpds = new ComboPooledDataSource();

        try {
            cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("Error while initializing pool.", e1);
        }

        cpds.setJdbcUrl(connectionURL);
        sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

        try {
            SQLConnectionProvider.setConnection(cpds.getConnection());

            if (!DatabaseUtil.tableExistInDatabase("polls")) {
                DatabaseUtil.executeQuery(DatabaseUtil.POLLS_TABLE_CREATE);
            }
            if (!DatabaseUtil.tableExistInDatabase("PollOptions")) {
                DatabaseUtil.executeQuery(DatabaseUtil.POLL_OPTIONS_TABLE_CREATE);
            }
            if (DatabaseUtil.isEmptyTable("polls") && DatabaseUtil.isEmptyTable("pollOptions")) {
                DatabaseUtil.loadAllTables(sce.getServletContext().getRealPath("/WEB-INF/allPollsHere.txt"));
            }

            SQLConnectionProvider.setConnection(null);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}