package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of the {@link GeometricalObjectVisitor} class.
 * Visitor is used for calculating bounding box of the all objects in the model.
 *
 * @author Stjepan Kovačić
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

    /**
     * List of all x values.
     */
    private List<Integer> xs = new ArrayList<>();

    /**
     * List of all y values.
     */
    private List<Integer> ys = new ArrayList<>();

    @Override
    public void visit(Line line) {
        xs.add(line.getX1());
        xs.add(line.getX2());
        ys.add(line.getY1());
        ys.add(line.getY2());
    }

    @Override
    public void visit(Circle circle) {
        xs.add(circle.getX() - circle.getR());
        xs.add(circle.getX() + circle.getR());
        ys.add(circle.getY() - circle.getR());
        ys.add(circle.getY() + circle.getR());
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        xs.add(filledCircle.getX() - filledCircle.getR());
        xs.add(filledCircle.getX() + filledCircle.getR());
        ys.add(filledCircle.getY() - filledCircle.getR());
        ys.add(filledCircle.getY() + filledCircle.getR());
    }

    /**
     * Method returns bounding box based on all visited objects.
     * Method should be called after visiting objects.
     *
     * @return bounding box
     */
    public Rectangle getBoundingBox() {

        int xMin = xs.stream().min(Comparator.naturalOrder()).orElse(0);
        int xMax = xs.stream().max(Comparator.naturalOrder()).orElse(200);

        int yMin = ys.stream().min(Comparator.naturalOrder()).orElse(0);
        int yMax = ys.stream().max(Comparator.naturalOrder()).orElse(200);

        //if empty model, default dimensions 200x200

        return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
    }
}
