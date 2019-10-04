package hr.fer.zemris.java.hw17.jvdraw.visitors;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Implementation of the {@link GeometricalObjectVisitor} class.
 * Visitor used for saving action.
 * Visitor builds final output string.
 *
 * @author Stjepan Kovačić
 */
public class GeometricalObjectOutputStringBuilder implements GeometricalObjectVisitor {

    private String finalString = "";

    @Override
    public void visit(Line line) {
        String s = String.format("LINE %d %d %d %d %d %d %d%n", line.getX1(), line.getY1(), line.getX2(), line.getY2(),
                line.getColor().getRed(), line.getColor().getGreen(), line.getColor().getBlue());

        finalString += s;
    }

    @Override
    public void visit(Circle circle) {
        String s = String.format("CIRCLE %d %d %d %d %d %d%n", circle.getX(), circle.getY(), circle.getR(),
                circle.getColor().getRed(), circle.getColor().getGreen(), circle.getColor().getBlue());

        finalString += s;
    }

    @Override
    public void visit(FilledCircle filledCircle) {
        String s = String.format("FCIRCLE %d %d %d %d %d %d %d %d %d%n", filledCircle.getX(), filledCircle.getY(), filledCircle.getR(),
                filledCircle.getOutlineColor().getRed(), filledCircle.getOutlineColor().getGreen(), filledCircle.getOutlineColor().getBlue(),
                filledCircle.getAreaColor().getRed(), filledCircle.getAreaColor().getGreen(), filledCircle.getAreaColor().getBlue());

        finalString += s;
    }

    /**
     * Getter of the final output string.
     * Method should be called after visiting all objects.
     *
     * @return final string
     */
    public String getFinalString() {
        return finalString;
    }
}
