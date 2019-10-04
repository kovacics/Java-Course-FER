package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.util.GlasanjeUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Servlet for getting pie chart of the voting results.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeSlikaServlet extends HttpServlet {

    private static final long serialVersionUID = -423158230515609630L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("image/png");

        OutputStream outputStream = resp.getOutputStream();

        JFreeChart chart = getChart(req);
        int width = 500;
        int height = 500;
        ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
    }

    /**
     * Helping method for getting chart.
     *
     * @param req request
     * @return chart
     * @throws IOException if io error happens
     */
    private JFreeChart getChart(HttpServletRequest req) throws IOException {

        String resultsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

        var records = GlasanjeUtil.getVotingResults(resultsFileName, bandsFileName);

        DefaultPieDataset dataset = new DefaultPieDataset();

        for (var record : records) {
            if (record.getVotes() != 0) {
                dataset.setValue(record.getBand().getName(), record.getVotes());
            }
        }

        boolean legend = true;
        boolean tooltips = false;
        boolean urls = false;

        JFreeChart chart = ChartFactory.createPieChart("Bands voting results", dataset, legend, tooltips, urls);

        chart.setBorderPaint(Color.BLUE);
        chart.setBorderStroke(new BasicStroke(2.0f));
        chart.setBorderVisible(true);

        return chart;
    }
}
