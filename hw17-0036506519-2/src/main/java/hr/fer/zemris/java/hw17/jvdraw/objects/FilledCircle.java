package hr.fer.zemris.java.hw17.jvdraw.objects;

import hr.fer.zemris.java.hw17.jvdraw.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import java.awt.*;

/**
 * Class represnets filled circle.
 *
 * @author Stjepan Kovačić
 */
public class FilledCircle extends GeometricalObject {

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
    private Color outlineColor;

    /**
     * Area color of the circle.
     */
    private Color areaColor;

    /**
     * Constructs filled circle with given attributes.
     *
     * @param x            x value
     * @param y            y value
     * @param r            radius
     * @param outlineColor outline color
     * @param areaColor    area color
     */
    public FilledCircle(int x, int y, int r, Color outlineColor, Color areaColor) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.outlineColor = outlineColor;
        this.areaColor = areaColor;
    }

    /**
     * Constructs filled circle with given center, radius and colors.
     *
     * @param center       center point
     * @param r            radius
     * @param outlineColor outline color
     * @param areaColor    area color
     */
    public FilledCircle(Point center, int r, Color outlineColor, Color areaColor) {
        this(center.x, center.y, r, outlineColor, areaColor);
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        return new FilledCircleEditor(this);
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
     * Getter of the circle outline color.
     *
     * @return outline color
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * Getter of the circle area color.
     *
     * @return area color
     */
    public Color getAreaColor() {
        return areaColor;
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
     * Sets outline color of the circle.
     *
     * @param outlineColor color to set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
        notifyListeners();
    }

    /**
     * Sets area color of the circle.
     *
     * @param areaColor color to set
     */
    public void setAreaColor(Color areaColor) {
        this.areaColor = areaColor;
        notifyListeners();
    }

    @Override
    public String toString() {
        String hexRGB = String.format("#%02x%02x%02x", areaColor.getRed(), areaColor.getGreen(), areaColor.getBlue());
        return String.format("Filled circle(%d,%d),%d,%s", x, y, r, hexRGB);
    }
}
