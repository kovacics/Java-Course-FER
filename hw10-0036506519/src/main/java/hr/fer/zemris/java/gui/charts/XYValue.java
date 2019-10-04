package hr.fer.zemris.java.gui.charts;

/**
 * Class represents x and y values pair for the chart.
 *
 * @author Stjepan Kovačić
 */
public class XYValue {

    /**
     * X value.
     */
    private int x;

    /**
     * Y value.
     */
    private int y;

    /**
     * Constructor specifying x and y.
     *
     * @param x x value
     * @param y y value
     */
    public XYValue(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the x value.
     *
     * @return x value
     */
    public int getX() {
        return x;
    }

    /**
     * Getter for the y value.
     *
     * @return y value
     */
    public int getY() {
        return y;
    }
}
