package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

import java.awt.*;

/**
 * Implementation of the {@code Command} interface, with functionality of
 * changing color of the current {@code TurtleState}.
 */
public class ColorCommand implements Command {

    /**
     * Color to be set for the current {@code TurtleState}.
     */
    private Color color;

    /**
     * Initialize color which should be set for the current {@code TurtleState}.
     *
     * @param color color of the {@code TurtleState}
     */
    public ColorCommand(Color color) {
        this.color = color;
    }

    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState current = ctx.getCurrentState();
        current.setColor(color);
    }
}
