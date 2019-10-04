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
    private ArrayIndexedCollection children;

    /**
     * Adds given child to an internally managed collection of children
     *
     * @param child child node to add in the collection
     */
    public void addChildNode(Node child) {
        if (children == null) {
            children = new ArrayIndexedCollection();
        }
        children.add(child);
    }

    /**
     * Returns number of direct node children.
     *
     * @return number of direct children
     */
    public int numberOfChildren() {
        if (children == null) return 0;
        else return children.size();
    }

    /**
     * Returns child at the given index.
     *
     * @param index index of child which should be returned
     * @return child on given index
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    public Node getChild(int index) {
        if (index >= numberOfChildren()) {
            throw new IndexOutOfBoundsException("Cannot get child from given index.");
        }
        return (Node) children.get(index);
    }
}
