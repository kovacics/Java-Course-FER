package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Interface represents visitor of the geometrical objects.
 */
public interface GeometricalObjectVisitor {

    /**
     * Method that visits line.
     *
     * @param line line
     */
    void visit(Line line);

    /**
     * Method that visits circle.
     *
     * @param circle circle
     */
    void visit(Circle circle);

    /**
     * Method that visits filled circle.
     *
     * @param filledCircle filled circle
     */
    void visit(FilledCircle filledCircle);
}