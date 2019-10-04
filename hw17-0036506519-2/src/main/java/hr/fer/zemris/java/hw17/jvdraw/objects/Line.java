package hr.fer.zemris.java.hw17.jvdraw.objects;

import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.awt.*;

/**
 * Class represents line object.
 *
 * @author Stjepan Kovačić
 */
public class Line extends GeometricalObject {

    /**
     * Start x value.
     */
    private int x1;

    /**
     * End x value.
     */
    private int x2;

    /**
     * Start y value.
     */
    private int y1;

    /**
     * End y value.
     */
    private int y2;

    /**
     * Color of the line.
     */
    private Color color;

    /**
     * Constructs line with specified attributes.
     *
     * @param x1    x1 value
     * @param x2    x2 value
     * @param y1    y1 value
     * @param y2    y2 value
     * @param color color
     */
    public Line(int x1, int x2, int y1, int y2, Color color) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
    }

    /**
     * Constructs line with specified start, end, and color.
     *
     * @param start start point
     * @param end   end point
     * @param color line color
     */
    public Line(Point start, Point end, Color color) {
        this(start.x, end.x, start.y, end.y, color);
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new LineEditor(this);
    }

    //***********************************
    //      GETTERS AND SETTERS
    //***********************************


    /**
     * Getter of the x1 value.
     *
     * @return x1 value
     */
    public int getX1() {
        return x1;
    }

    /**
     * Getter of the x2 value.
     *
     * @return x2 value
     */
    public int getX2() {
        return x2;
    }

    /**
     * Getter of the y1 value.
     *
     * @return y1 value
     */
    public int getY1() {
        return y1;
    }

    /**
     * Getter of the y2 value.
     *
     * @return y2 value
     */
    public int getY2() {
        return y2;
    }

    /**
     * Getter of the line color.
     *
     * @return color of the line
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets x1 value of the line.
     *
     * @param x1 value to set
     */
    public void setX1(int x1) {
        this.x1 = x1;
        notifyListeners();
    }

    /**
     * Sets x2 value of the line.
     *
     * @param x2 value to set
     */
    public void setX2(int x2) {
        this.x2 = x2;
        notifyListeners();
    }

    /**
     * Sets y1 value of the line.
     *
     * @param y1 value to set
     */
    public void setY1(int y1) {
        this.y1 = y1;
        notifyListeners();
    }

    /**
     * Sets y2 value of the line.
     *
     * @param y2 value to set
     */
    public void setY2(int y2) {
        this.y2 = y2;
        notifyListeners();
    }

    /**
     * Sets color of the line.
     *
     * @param color color to set
     */
    public void setColor(Color color) {
        this.color = color;
        notifyListeners();
    }

    @Override
    public String toString() {
        return String.format("Line(%d,%d)-(%d,%d)", x1, y1, x2, y2);
    }
}
