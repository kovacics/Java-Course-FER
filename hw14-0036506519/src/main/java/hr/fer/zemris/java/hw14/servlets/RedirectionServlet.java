package hr.fer.zemris.java.hw14.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used for redirecting home page.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/index.html")
public class RedirectionServlet extends HttpServlet {
    private static final long serialVersionUID = 2630793607756484047L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("servleti/index.html");
    }
}
