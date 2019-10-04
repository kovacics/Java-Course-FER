package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Implementation of the {@code Command} interface, with functionality of
 * rotating direction vector of the current {@code TurtleState} for some angle.
 */
public class RotateCommand implements Command {

    /**
     * Angle for which direction of the current {@code TurtleState} should be rotated.
     */
    private double angle;

    /**
     * Constructor specifying angle of the rotation.
     *
     * @param angle angle of the rotation
     */
    public RotateCommand(double angle) {
        this.angle = angle;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState current = ctx.getCurrentState();
        current.getDirection().rotate(Math.toRadians(angle));
    }
}
