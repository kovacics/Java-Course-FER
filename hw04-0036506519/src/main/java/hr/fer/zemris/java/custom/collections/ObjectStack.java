package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Class represents stack.
 * It provides all basic methods for working with stack.
 *
 * @author Stjepan Kovačić
 */
public class ObjectStack<T> {

    /**
     * Data structure used for storing stack's elements.
     * Adaptee for ObjectStack adapter.
     */
    private ArrayIndexedCollection<T> collection;

    /**
     * Creates instance of the class, and creates instance of an adaptee.
     */
    public ObjectStack() {
        this.collection = new ArrayIndexedCollection<>();
    }

    /**
     * Checks if stack is empty.
     *
     * @return true if stack is empty, false if not
     */
    public boolean isEmpty() {
        return size() == 0;
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
    public void push(T value) {
        Objects.requireNonNull(value, "Cannot push null on stack.");
        collection.add(value);
    }

    /**
     * Pops one element from the top of the stack. element gets removed from the stack,
     * and method returns that element.
     *
     * @return popped element
     * @throws EmptyStackException if stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException("Stack is empty, cannot pop");
        }
        T popped = collection.get(collection.size() - 1);
        collection.remove(collection.size() - 1);

        return popped;
    }

    /**
     * Returns element that is on the top of the stack,
     * but doesn't remove it from the stack.
     *
     * @return element from the top of the stack
     */
    public T peek() {
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