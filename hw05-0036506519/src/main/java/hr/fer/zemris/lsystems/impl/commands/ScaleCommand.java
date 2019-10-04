package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Implementation of the {@code Command} interface, with functionality of
 * scaling step size of the current {@code TurtleState} for some factor.
 */
public class ScaleCommand implements Command {

    /**
     * Factor for scaling of a current {@code TurtleState} step size.
     */
    private double factor;

    /**
     * Constructor specifying factor of the scaling.
     *
     * @param factor scaling factor
     */
    public ScaleCommand(double factor) {
        this.factor = factor;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState current = ctx.getCurrentState();
        current.setStepSize(current.getStepSize() * factor);
    }
}
