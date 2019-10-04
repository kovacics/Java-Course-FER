package hr.fer.zemris.java.hw16.init;

import hr.fer.zemris.java.hw16.imageDB.ImageDB;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class for web app initialization.
 *
 * @author Stjepan Kovačić
 */
@WebListener
public class InitGalleryApp implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ImageDB.createDB(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
