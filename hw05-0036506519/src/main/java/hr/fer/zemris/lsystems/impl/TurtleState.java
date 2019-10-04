package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.math.Vector2D;

import java.awt.*;

/**
 * Represent turtle state with its position, direction, color and stepSize.
 */
public class TurtleState {

    /**
     * Position of the turtle.
     */
    private Vector2D position;

    /**
     * Direction of the turtle.
     */
    private Vector2D direction;

    /**
     * Color of the turtle.
     */
    private Color color;

    /**
     * Step size of the turtle.
     */
    private double stepSize;

    /**
     * Constructor specifying all fields
     *
     * @param position  position of the turtle
     * @param direction direction of the turtle
     * @param color     color of the turtle
     * @param stepSize  step size of the turtle
     */
    public TurtleState(Vector2D position, Vector2D direction, Color color, double stepSize) {
        this.position = position;
        this.direction = direction;
        this.color = color;
        this.stepSize = stepSize;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public Vector2D getDirection() {
        return direction;
    }

    public void setDirection(Vector2D direction) {
        this.direction = direction;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getStepSize() {
        return stepSize;
    }

    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }

    /**
     * Construct new {@code TurtleState} with the same attributes.
     *
     * @return constructed {@code TurtleState}
     */
    public TurtleState copy() {
        return new TurtleState(position.copy(), direction.copy(), color, stepSize);
    }
}
