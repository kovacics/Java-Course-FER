package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface representing one command which can be executed using
 * current {@code Context}.
 */
public interface Command {

    /**
     * Executes specific command using current {@code Context}.
     *
     * @param ctx     {@code Context} that contains all previous {@code TurtleState}.
     * @param painter {@code Painter} used if command draws on screen
     */
    void execute(Context ctx, Painter painter);
}
