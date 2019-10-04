package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet which is used for getting list of all polls.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet {

    private static final long serialVersionUID = 6153930119407145387L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Poll> pollsList = DAOProvider.getDao().getPolls();
            req.setAttribute("pollsList", pollsList);
            req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }
}
