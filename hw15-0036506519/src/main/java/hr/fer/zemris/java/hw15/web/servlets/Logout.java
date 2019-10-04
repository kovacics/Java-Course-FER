package hr.fer.zemris.java.hw15.web.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used for user logout from the blog web app.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/servleti/logout")
public class Logout extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
    }
}
