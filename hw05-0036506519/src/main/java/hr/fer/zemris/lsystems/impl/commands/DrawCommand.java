package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Implementation of the {@code Command} interface, with functionality of
 * changing position of the current {@code TurtleState} and drawing path line on the screen.
 */
public class DrawCommand implements Command {

    /**
     * Step of the current move.
     */
    private double step;

    /**
     * Constructor specifying step of the current move.
     *
     * @param step length of the move
     */
    public DrawCommand(double step) {
        this.step = step;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState currentState = ctx.getCurrentState();
        Vector2D startPoint = currentState.getPosition();
        Vector2D endPoint = startPoint.translated(currentState.getDirection().scaled(step * currentState.getStepSize()));
        currentState.setPosition(endPoint);
        painter.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY(), currentState.getColor(), 1f);
    }
}
