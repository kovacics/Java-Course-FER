package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Interface represent collection.
 *
 * @author Stjepan Kovačić
 */
public interface Collection {

    /**
     * Returns size of the collection.
     *
     * @return size of the collection
     */
    int size();


    /**
     * Adds element in the collection.
     *
     * @param value Element to add in the collection.
     */
    void add(Object value);

    /**
     * Checks if collection contains given element.
     *
     * @param value Element for which to check existence in the collection
     * @return true if contains, false if not
     */
    boolean contains(Object value);

    /**
     * Removes element from the collection,
     * and returns outcome of the removal operation.
     *
     * @param value Element to remove from collection.
     * @return true if removed, false if not(if collection didn't contain element)
     */
    boolean remove(Object value);

    /**
     * Converts collection to an array of objects.
     *
     * @return array of collection's objects
     */
    Object[] toArray();

    /**
     * Removes all elements from the collection.
     */
    void clear();


    /**
     * Creates instance of the <code>ElementsGetter</code> class.
     *
     * @return created <code>ElementsGetter</code>
     */
    ElementsGetter createElementsGetter();

    //**********************************
    //       DEFAULT METHODS
    //**********************************

    /**
     * Checks if collection is empty.
     *
     * @return true if collection is empty, false if not
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Adds all elements from given collection to this collection.
     * Uses local Processor class.
     *
     * @param other Collection whose elements get to be added in this collection.
     */
    default void addAll(Collection other) {
        Objects.requireNonNull(other);
        other.forEach(this::add);
    }


    /**
     * Method adds to this collection all elements of given collection
     * which were accepted by tester.
     *
     * @param col    collection whose elements should be tested and added to collection
     * @param tester tester for given collection's elements
     */
    default void addAllSatisfying(Collection col, Tester tester) {
        Objects.requireNonNull(col);
        Objects.requireNonNull(tester);

        ElementsGetter getter = col.createElementsGetter();

        getter.processRemaining(value -> {
            if (tester.test(value)) {
                add(value);
            }
        });
    }

    /**
     * Calls process method of the processor for each collection's element
     *
     * @param p Processor used for doing some work on collection's elements
     * @throws NullPointerException if given {@code Processor} is {@code null} reference
     */
    default void forEach(Processor p) {
        Objects.requireNonNull(p);

        ElementsGetter getter = this.createElementsGetter();
        getter.processRemaining(p); // iterator's index is surely on the first element
    }
}
