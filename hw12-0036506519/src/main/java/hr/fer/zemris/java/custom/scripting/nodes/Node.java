package hr.fer.zemris.java.custom.scripting.nodes;


import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all nodes.
 *
 * @author Stjepan Kovačić
 */
public abstract class Node {

    /**
     * Children nodes of the node.
     */
    private ArrayIndexedCollection<Node> children;

    /**
     * Adds given child to an internally managed collection of children
     *
     * @param child child node to add in the collection
     */
    public void addChildNode(Node child) {
        if (children == null) {
            children = new ArrayIndexedCollection<>();
        }
        children.add(child);
    }

    /**
     * Returns number of direct node children.
     *
     * @return number of direct children
     */
    public int numberOfChildren() {
        return children.size();
    }

    /**
     * Returns child at the given index.
     *
     * @param index index of child which should be returned
     * @return child on given index
     * @throws IndexOutOfBoundsException if index is less than 0
     */
    public Node getChild(int index) {
        return children.get(index);
    }

    /**
     * Method that accepts visitor and calls needed visitor method.
     *
     * @param visitor visitor of the nodes
     */
    public abstract void accept(INodeVisitor visitor);
}
