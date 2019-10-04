package hr.fer.zemris.java.hw17.jvdraw.objects;

import hr.fer.zemris.java.hw17.jvdraw.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.awt.*;

/**
 * Class represents circle object.
 *
 * @author Stjepan Kovačić
 */
public class Circle extends GeometricalObject {

    /**
     * X value of the center.
     */
    private int x;

    /**
     * Y value of the center.
     */
    private int y;

    /**
     * Circle radius.
     */
    private int r;

    /**
     * Outline color of the circle.
     */
    private Color color;

    /**
     * Constructs circle with specified attributes.
     *
     * @param x     x value of the center
     * @param y     y value of the center
     * @param r     circle radius
     * @param color color of the circle
     */
    public Circle(int x, int y, int r, Color color) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.color = color;
    }

    /**
     * Constructs circle with specified center, radius and color.
     *
     * @param center center of the circle.
     * @param r      circle radius
     * @param color  color of the circle
     */
    public Circle(Point center, int r, Color color) {
        this(center.x, center.y, r, color);
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new CircleEditor(this);
    }

    //***********************************
    //      GETTERS AND SETTERS
    //***********************************

    /**
     * Getter of the x value.
     *
     * @return x value
     */
    public int getX() {
        return x;
    }

    /**
     * Getter of the y value.
     *
     * @return y value
     */
    public int getY() {
        return y;
    }

    /**
     * Getter of the radius value.
     *
     * @return radius value
     */
    public int getR() {
        return r;
    }

    /**
     * Getter of the circle color.
     *
     * @return circle color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets x value.
     *
     * @param x value to set
     */
    public void setX(int x) {
        this.x = x;
        notifyListeners();
    }

    /**
     * Sets y value.
     *
     * @param y value to set
     */
    public void setY(int y) {
        this.y = y;
        notifyListeners();
    }

    /**
     * Sets radius value.
     *
     * @param r value to set
     */
    public void setR(int r) {
        this.r = r;
        notifyListeners();
    }

    /**
     * Sets circle color.
     *
     * @param color color to set
     */
    public void setColor(Color color) {
        this.color = color;
        notifyListeners();
    }

    @Override
    public String toString() {
        return String.format("Circle(%d,%d),%d", x, y, r);
    }
}
