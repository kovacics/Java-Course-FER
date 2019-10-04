package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for updating voting results.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
    private static final long serialVersionUID = -2402571340221778265L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pollOptionId = req.getParameter("id");
        String pollID = DAOProvider.getDao().getPollID(pollOptionId);

        try {
            DAOProvider.getDao().addVote(pollOptionId);
        } catch (DAOException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }

        resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
    }
}
