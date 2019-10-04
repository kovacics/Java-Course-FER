package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

import java.awt.*;

/**
 * Implementation of the {@link GeometricalObjectVisitor} class.
 * Visitor is used for drawing objects.
 *
 * @author Stjepan Kovačić
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

    /**
     * Graphics object used for drawing.
     */
    private Graphics2D g2d;

    /**
     * Constructs painter visitor with specified graphics object.
     *
     * @param g2d graphics object
     */
    public GeometricalObjectPainter(Graphics2D g2d) {
        this.g2d = g2d;
    }

    @Override
    public void visit(Line line) {
        g2d.setColor(line.getColor());
        g2d.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
    }

    @Override
    public void visit(Circle circle) {
        g2d.setColor(circle.getColor());
        int r = circle.getR();
        g2d.drawOval(circle.getX() - r, circle.getY() - r, 2 * r, 2 * r);
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        g2d.setColor(filledCircle.getOutlineColor());
        int r = filledCircle.getR();
        g2d.setStroke(new BasicStroke(4));
        g2d.drawOval(filledCircle.getX() - r, filledCircle.getY() - r, 2 * r, 2 * r);

        g2d.setColor(filledCircle.getAreaColor());
        g2d.fillOval(filledCircle.getX() - r, filledCircle.getY() - r, 2 * r, 2 * r);
        g2d.setStroke(new BasicStroke(1)); // reset stroke to normal
    }
}
