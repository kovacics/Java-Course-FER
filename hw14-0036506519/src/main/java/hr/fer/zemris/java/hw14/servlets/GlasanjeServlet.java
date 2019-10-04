package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for voting in concrete poll.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {
    private static final long serialVersionUID = 5524870498690226563L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pollId = req.getParameter("pollID");

        try {
            List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollId);
            req.setAttribute("pollOptions", pollOptions);

            Poll pollInfo = DAOProvider.getDao().getPoll(pollId);
            req.setAttribute("pollInfo", pollInfo);
        } catch (DAOException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
