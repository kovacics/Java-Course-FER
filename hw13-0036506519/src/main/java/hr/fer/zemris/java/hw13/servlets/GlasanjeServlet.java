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
 * Servlet for voting.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {
    private static final long serialVersionUID = 859667318410166062L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String bandsListFilePath = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        List<GlasanjeUtil.BandRecord> bands = GlasanjeUtil.getAllBands(bandsListFilePath);

        req.setAttribute("bands", bands);
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
