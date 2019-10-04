package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Comparator;

/**
 * Class represents bar chart component.
 *
 * @author Stjepan Kovačić
 */
public class BarChartComponent extends JComponent {

    /**
     * Gap between the bars.
     */
    private static final int BAR_GAP = 2;

    /**
     * Space between some parts in the component.
     */
    private static final int SPACE = 10;

    /**
     * Dash length.
     */
    private static final int DASH = 7;

    /**
     * Bar color.
     */
    public static final Color BAR_COLOR = new Color(29, 74, 194);

    /**
     * Chart with all data.
     */
    private BarChart chart;

    /**
     * Bottom-left coordinate system point.
     */
    private Point start;

    /**
     * Upper-right coordinate system point.
     */
    private Point end;

    /**
     * Font metrics.
     */
    private FontMetrics fm;

    /**
     * One step on x axis.
     */
    private int oneStepX;

    /**
     * One step on y axis.
     */
    private int oneStepY;

    /**
     * Graphics used for modeling component.
     */
    private Graphics2D g;

    /**
     * Maximum x value.
     */
    private Integer maxXValue;

    /**
     * Constructs component.
     *
     * @param chart bar chart
     */
    public BarChartComponent(BarChart chart) {
        this.chart = chart;
    }


    @Override
    protected void paintComponent(Graphics gg) {
        g = (Graphics2D) gg;
        g.translate(getInsets().left, getInsets().top);

        initFields();
        drawAxisAndNumbers();
        fillBars();
        drawArrows();
        addLabels();
    }

    /**
     * Initialize all field values.
     */
    private void initFields() {
        //windows size
        int width = getSize().width;
        int height = getSize().height;

        //font metrics
        g.setFont(new Font(g.getFont().getName(), Font.BOLD, g.getFont().getSize()));
        fm = getFontMetrics(g.getFont());

        //coordinate system start,end
        int startX = SPACE + fm.getHeight() + SPACE + getMaximumLength(chart, fm) + SPACE;
        int startY = (height - SPACE - fm.getHeight() - SPACE - fm.getHeight() - SPACE);
        start = new Point(startX, startY);

        int endX = width - start.x;
        int endY = height - start.y;
        end = new Point(endX, endY);

        //steps
        maxXValue = chart.getData().stream().map(XYValue::getX).max(Comparator.naturalOrder()).orElse(5);
        oneStepX = (end.x - start.x) / maxXValue;
        oneStepY = (start.y - end.y) / (chart.getyMax() - chart.getyMin());
    }

    /**
     * Draws axis.
     */
    private void drawAxisAndNumbers() {
        g.drawLine(start.x, start.y, end.x, start.y); // x axis
        g.drawLine(start.x, start.y + DASH, start.x, end.y); // y axis

        for (int i = 1; i <= maxXValue; i++) {
            g.drawLine(
                    start.x + i * oneStepX, start.y,
                    start.x + i * oneStepX, start.y + DASH
            );
            String xValue = String.valueOf(i);
            g.drawString(
                    xValue,
                    start.x + i * oneStepX - oneStepX / 2 - fm.stringWidth(xValue) / 2,
                    start.y + fm.getHeight()
            );
        }

        for (int i = 0; i <= chart.getyMax() - chart.getyMin(); i += chart.getyStep()) {
            String yValue = String.valueOf(chart.getyMin() + i);
            g.drawString(
                    yValue,
                    start.x - SPACE - fm.stringWidth(yValue),
                    start.y - oneStepY * i + (fm.getAscent() - fm.getDescent()) / 2
            );
            g.drawLine(start.x - DASH, start.y - oneStepY * i, end.x, start.y - oneStepY * i);
        }
    }

    /**
     * Fill bars and initialize axis.
     */
    private void fillBars() {
        g.setPaint(BAR_COLOR);
        for (var xyvalue : chart.getData()) {
            g.fill(new Rectangle2D.Double(
                    start.x + (xyvalue.getX() - 1) * oneStepX + BAR_GAP / 2.0,
                    start.y - oneStepY * (xyvalue.getY() - chart.getyMin()),
                    oneStepX - BAR_GAP,
                    oneStepY * (xyvalue.getY() - chart.getyMin())));
        }
        g.setPaint(Color.BLACK);
    }

    /**
     * Draws arrows on the axis.
     */
    private void drawArrows() {
        // ^
        g.drawLine(start.x - DASH, end.y - DASH, start.x, end.y - 2 * DASH);
        g.drawLine(start.x + DASH, end.y - DASH, start.x, end.y - 2 * DASH);
        g.drawLine(start.x, end.y, start.x, end.y - 2 * DASH);

        // >
        g.drawLine(end.x + DASH, start.y - DASH, end.x + 2 * DASH, start.y);
        g.drawLine(end.x + DASH, start.y + DASH, end.x + 2 * DASH, start.y);
        g.drawLine(end.x, start.y, end.x + 2 * DASH, start.y);
    }

    /**
     * Adds labels.
     */
    private void addLabels() {
        String xlabel = chart.getxAxis();
        g.drawString(
                xlabel,
                (start.x + end.x) / 2 - fm.stringWidth(xlabel) / 2,
                start.y + 2 * SPACE + fm.getHeight()
        );

        var t = new AffineTransform();
        t.rotate(-Math.PI / 2);
        var savedAT = g.getTransform();
        g.transform(t);

        String ylabel = chart.getyAxis();
        g.drawString(
                ylabel,
                -(start.y + end.y) / 2 - fm.stringWidth(ylabel) / 2,
                start.x - SPACE - getMaximumLength(chart, fm) - SPACE
        );

        g.setTransform(savedAT);
    }

    /**
     * Helping method to find maximum length of all values in the chart.
     *
     * @param chart chart
     * @param fm    font metrics
     * @return maximum length
     */
    private int getMaximumLength(BarChart chart, FontMetrics fm) {
        return fm.stringWidth(String.valueOf(chart.getyMax()));
    }
}