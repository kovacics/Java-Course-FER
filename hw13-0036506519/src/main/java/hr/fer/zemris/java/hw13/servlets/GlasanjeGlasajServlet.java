package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.util.GlasanjeUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Servlet for updating voting results.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
    private static final long serialVersionUID = 1066951202505615750L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String voteId = req.getParameter("id");
        String votingResultsFilename = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

        File file = new File(votingResultsFilename);
        if (!file.exists()) {
            Files.createFile(Paths.get(votingResultsFilename));
        }

        GlasanjeUtil.updateVotingResults(voteId, votingResultsFilename);

        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }
}
