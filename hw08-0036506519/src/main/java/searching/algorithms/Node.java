package searching.algorithms;


/**
 * Represents node with additional information of the state.
 *
 * @author Stjepan Kovačić
 */
public class Node<S> {

    /**
     * Current state.
     */
    private S state;

    /**
     * Parent node (with parent state).
     */
    private Node<S> parent;

    /**
     * Cost of the current state
     * (path length from initial state to for this state).
     */
    private double cost;

    /**
     * Constructs node with specified parent, state and cost.
     *
     * @param parent parent node
     * @param state  current state
     * @param cost   cost of this state
     */
    public Node(Node<S> parent, S state, double cost) {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
    }

    /**
     * Returns state of the node.
     *
     * @return node state
     */
    public S getState() {
        return state;
    }

    /**
     * Returns cost of the node.
     *
     * @return node cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Returns parent of the node.
     *
     * @return node parent
     */
    public Node<S> getParent() {
        return parent;
    }
}
