package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Implementation of the {@code Command} interface, with functionality of
 * popping last {@code TurtleState} from the context stack.
 */
public class PopCommand implements Command {

    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.popState();
    }
}
