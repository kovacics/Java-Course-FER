package hr.fer.zemris.java.hw13.servlets;

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
 * Servlet used for generating chart.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/reportImage")
public class ChartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("image/png");

        OutputStream outputStream = response.getOutputStream();

        JFreeChart chart = getSimpleChart();
        int width = 500;
        int height = 500;
        ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
    }

    /**
     * Simple private method for getting simple testing chart.
     *
     * @return chart
     */
    private JFreeChart getSimpleChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Windows", 47);
        dataset.setValue("MacOS", 27);
        dataset.setValue("Linux", 26);

        boolean legend = false;
        boolean tooltips = false;
        boolean urls = false;

        JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, legend, tooltips, urls);

        chart.setBorderPaint(Color.BLACK);
        chart.setBorderStroke(new BasicStroke(10.0f));
        chart.setBorderVisible(true);

        return chart;
    }
}
