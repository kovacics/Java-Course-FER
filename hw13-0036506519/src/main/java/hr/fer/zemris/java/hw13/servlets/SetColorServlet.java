package hr.fer.zemris.java.hw13.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for setting background color of the pages.
 *
 * @author Stjepan Kovačić
 */
@WebServlet(name = "color", urlPatterns = {"/setcolor"})
public class SetColorServlet extends HttpServlet {
    private static final long serialVersionUID = -5003208624996008092L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("pickedBgColor", req.getParameter("color"));
        resp.sendRedirect("index.jsp");
    }
}
