package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class represents bar chart.
 *
 * @author Stjepan Kovačić
 */
public class BarChart {

    /**
     * All data for the chart.
     */
    private List<XYValue> data;

    /**
     * X axis label.
     */
    private String xAxis;

    /**
     * Y axis label.
     */
    private String yAxis;

    /**
     * Y axis minimum value.
     */
    private int yMin;

    /**
     * Y axis maximum value.
     */
    private int yMax;

    /**
     * Step on the y axis.
     */
    private int yStep;

    /**
     * Constructs bar chart with specified attributes.
     *
     * @param data  chart data
     * @param xAxis x axis label
     * @param yAxis y axis label
     * @param yMin  y minimum value
     * @param yMax  y maximum value
     * @param yStep y step
     */
    public BarChart(List<XYValue> data, String xAxis, String yAxis, int yMin, int yMax, int yStep) {
        this.data = data;
        this.xAxis = xAxis;
        this.yAxis = yAxis;

        if (yMin < 0) {
            throw new RuntimeException("Minimum y can't be negative.");
        }
        this.yMin = yMin;

        if (yMax <= yMin) {
            throw new RuntimeException("Maximum y must be greater than minimum y.");
        }

        this.yMax = yMax;

        while ((this.yMax - yMin) % yStep != 0) {
            this.yMax++;
        }

        this.yStep = yStep;

        for (XYValue xyvalue : data) {
            if (xyvalue.getY() < yMin) {
                throw new RuntimeException("Y value cannot be less than minimum y");
            }
        }
    }

    /**
     * Getter for the chart data.
     *
     * @return chart data
     */
    public List<XYValue> getData() {
        return data;
    }

    /**
     * Getter for the x axis label.
     *
     * @return x axis label
     */
    public String getxAxis() {
        return xAxis;
    }

    /**
     * Getter for the y axis label.
     *
     * @return y axis label
     */
    public String getyAxis() {
        return yAxis;
    }

    /**
     * Getter for the chart y minimum value.
     *
     * @return y minimum value
     */
    public int getyMin() {
        return yMin;
    }

    /**
     * Getter for the chart y maximum value.
     *
     * @return y maximum value
     */
    public int getyMax() {
        return yMax;
    }

    /**
     * Getter for the y axis step.
     *
     * @return y axis step
     */
    public int getyStep() {
        return yStep;
    }
}
