package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Class used for storing all {@code TurtleState} states.
 * Implements all basic method for working with stored states.
 */
public class Context {

    /**
     * Structure used for storing states.
     * Every next state gets on the top of the stack, so at any point
     * current state is on the top of the stack.
     */
    private ObjectStack<TurtleState> states;

    /**
     * Default constructor, allocates {@code ObjectStack} for storing states.
     */
    public Context() {
        this.states = new ObjectStack<>();
    }

    /**
     * Returns state from the top of the stack,
     * which actually is current {@code TurtleState}.
     * It <b>doesn't</b> remove current state from the stack.
     *
     * @return current state
     */
    public TurtleState getCurrentState() {
        return states.peek();
    }

    /**
     * Pushes(adds on the top) given state on the stack.
     *
     * @param state state to be pushed on the stack.
     */
    public void pushState(TurtleState state) {
        states.push(state);
    }

    /**
     * Removes state from the top of the stack.
     */
    public void popState() {
        states.pop();
    }
}
