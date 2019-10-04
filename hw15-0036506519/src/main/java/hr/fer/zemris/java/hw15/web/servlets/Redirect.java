package hr.fer.zemris.java.hw15.web.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used for redirection on the main page.
 *
 * @author Stjepan Kovačić
 */
@WebServlet(urlPatterns = {"/index.jsp"})
public class Redirect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
    }
}
