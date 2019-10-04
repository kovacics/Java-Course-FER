package hr.fer.zemris.java.hw13.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Servlet context listener that saves time of the server startup.
 *
 * @author Stjepan Kovačić
 */
@WebListener
public class StartTimeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().removeAttribute("startTime");
    }
}
