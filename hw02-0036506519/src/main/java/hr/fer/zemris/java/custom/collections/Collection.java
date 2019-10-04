package hr.fer.zemris.java.custom.collections;

import java.util.Objects;


/**
 * Represents collection of elements.
 *
 * @author Stjepan Kovačić
 */
public class Collection {

    /**
     * Default constructor.
     */
    protected Collection() {
    }

    /**
     * Checks if collection is empty.
     *
     * @return true if collection is empty, false if not
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns size of the collection.
     * In this implementation always returns zero,
     * and in all inherited classes should be overridden.
     *
     * @return zero
     */
    public int size() {
        return 0;
    }

    /**
     * Checks if collection contains given element.
     * In this implementation always returns false,
     * and in all inherited classes should be overridden.
     *
     * @param value element to add in the collection
     * @return false
     */
    public boolean contains(Object value) {
        return false;
    }

    /**
     * Removes element from the collection, and returns outcome.
     * In this implementation always returns false,
     * and in all inherited classes should be overridden.
     *
     * @param value Element to remove from collection.
     * @return false
     */
    public boolean remove(Object value) {
        return false;
    }

    /**
     * Convert collection to an array of objects.
     * In this implementation always throws exception.
     *
     * @return in this implementation, never returns nothing
     * @throws UnsupportedOperationException always
     */
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds all elements from given collection to this collection.
     * Uses local Processor class.
     *
     * @param other Collection whose elements get to be added in this collection.
     */
    public void addAll(Collection other) {
        Objects.requireNonNull(other);

        class AddDataProcessor extends Processor {

            @Override
            public void process(Object value) {
                add(value);
            }
        }

        AddDataProcessor processor = new AddDataProcessor();
        other.forEach(processor);
    }

    /**
     * Adds element in the collection.
     * In this implementation it doesn't to anything,
     * and in all inherited classes should be overridden.
     *
     * @param value element to add in the collection.
     */
    public void add(Object value) {

    }

    /**
     * In this implementation, does nothing
     * and in all inherited classes should be overridden.
     *
     * @param processor Processor used for doing some work on collection's elements
     */
    public void forEach(Processor processor) {

    }

    /**
     * Removes all elements from collection.
     * In this implementation, does nothing
     * and in all inherited classes should be overridden.
     */
    public void clear() {

    }
}
