package hr.fer.zemris.java.custom.collections;

/**
 * Class represents stack.
 * It provides all basic methods for working with stack.
 *
 * @author Stjepan Kovačić
 */
public class ObjectStack {

    /**
     * Data structure used for storing stack's elements.
     * Adaptee for ObjectStack adapter.
     */
    private ArrayIndexedCollection collection;

    /**
     * Creates instance of the class, and creates instance of a adaptee.
     */
    public ObjectStack() {
        this.collection = new ArrayIndexedCollection();
    }

    /**
     * Returns number of elements on stack.
     *
     * @return size of the stack
     */
    public int size() {
        return collection.size();
    }

    /**
     * Pushes one element on the top of the stack.
     *
     * @param value Element to push on stack
     */
    public void push(Object value) {
        collection.add(value);
    }

    /**
     * Pops one element from the top of the stack.
     *
     * @return popped element
     */
    public Object pop() {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty, cannot pop");
        }
        Object popped = collection.get(collection.size() - 1);
        collection.remove(collection.size() - 1);

        return popped;
    }

    /**
     * Checks if stack is empty
     *
     * @return true if stack is empty, false if not
     */
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    /**
     * Returns element that is on the top of the stack,
     * but doesn't remove it from the stack.
     *
     * @return element from the top of the stack
     */
    public Object peek() {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty, cannot pop");
        }
        return collection.get(collection.size() - 1);
    }

    /**
     * Removes all elements from the stack.
     */
    public void clear() {
        collection.clear();
    }
}