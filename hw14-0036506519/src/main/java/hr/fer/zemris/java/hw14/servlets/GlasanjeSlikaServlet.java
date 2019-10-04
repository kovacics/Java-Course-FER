package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for getting pie chart of the voting results.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeSlikaServlet extends HttpServlet {

    private static final long serialVersionUID = -8158450381509226294L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String pollID = req.getParameter("pollID");
            JFreeChart chart = getChart(pollID);
            int width = 500;
            int height = 500;

            resp.setContentType("image/png");
            ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, width, height);
        } catch (DAOException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }


    /**
     * Helping method for getting chart.
     *
     * @param pollID pollID
     * @return chart
     * @throws DAOException if DAO error happens
     */
    private JFreeChart getChart(String pollID) throws DAOException {
        List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
        DefaultPieDataset dataset = new DefaultPieDataset();

        for (var option : options) {
            if (option.getVotesCount() != 0) {
                dataset.setValue(option.getOptionTitle(), option.getVotesCount());
            }
        }
        JFreeChart chart = ChartFactory.createPieChart("Voting results", dataset, true, false, false);
        chart.setBorderPaint(Color.BLUE);
        chart.setBorderStroke(new BasicStroke(2.0f));
        chart.setBorderVisible(true);

        return chart;
    }
}
