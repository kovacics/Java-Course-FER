package searching.algorithms;

/**
 * Represents transition from the (initial) state to other state.
 *
 * @author Stjepan Kovačić
 */
public class Transition<S> {

    /**
     * Current state.
     */
    private S state;

    /**
     * Cost of the current state
     * (path length from the initial state)
     */
    private double cost;

    /**
     * Constructs transition.
     *
     * @param state current state
     * @param cost  cost of the state
     */
    public Transition(S state, double cost) {
        this.state = state;
        this.cost = cost;
    }

    /**
     * Returns state of the transition.
     *
     * @return transition state
     */
    public S getState() {
        return state;
    }

    /**
     * Returns cost of the transition.
     *
     * @return transition cost
     */
    public double getCost() {
        return cost;
    }
}
