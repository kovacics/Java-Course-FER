package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.util.GlasanjeUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for getting voting results.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
    private static final long serialVersionUID = -3423817615829637932L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String resultsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

        List<GlasanjeUtil.VotingResult> records = GlasanjeUtil.getVotingResults(resultsFileName, bandsFileName);
        req.setAttribute("records", records);

        List<GlasanjeUtil.BandRecord> winners = GlasanjeUtil.getAllWinners(records);
        req.setAttribute("winners", winners);

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
