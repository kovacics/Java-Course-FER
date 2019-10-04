package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Implementation of the {@code Command} interface, with functionality of
 * pushing copy of the current {@code TurtleState} to the context stack.
 */
public class PushCommand implements Command {

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.pushState(ctx.getCurrentState().copy());
    }
}
