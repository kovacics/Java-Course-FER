package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Interface represent collection of elements, and also defines all basic methods for working
 * with collection.
 *
 * @author Stjepan Kovačić
 */
public interface Collection<T> {

    /**
     * Returns number of elements in the collection.
     *
     * @return size of the collection
     */
    int size();


    /**
     * Adds element in the collection.
     *
     * @param value Element which should be added in the collection.
     */
    void add(T value);


    /**
     * Checks if this collection contains given element.
     *
     * @param value element for which to check existence in this collection.
     * @return true if contains, false if not
     */
    boolean contains(Object value);


    /**
     * Removes element from the collection,
     * and returns outcome of the removal operation.
     *
     * @param value element to remove from collection.
     * @return true if removed, false if not(if collection didn't contain element)
     */
    boolean remove(T value);


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
    ElementsGetter<T> createElementsGetter();

    //**********************************
    //       DEFAULT METHODS
    //**********************************


    /**
     * Checks if number of elements in the collection is 0.
     *
     * @return true if collection is empty, false if not
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Adds all elements from given collection to this collection.
     *
     * @param other Collection whose elements get to be added in this collection.
     * @throws NullPointerException if given collection is {@code null} reference
     */
    default void addAll(Collection<? extends T> other) {
        Objects.requireNonNull(other);
        other.forEach(this::add);
    }


    /**
     * Method adds to this collection all elements of given collection
     * which were accepted by tester.
     *
     * @param col    collection whose elements should be tested and added to collection
     * @param tester tester for given collection's elements
     * @throws NullPointerException if any of passed parameters is a {@code null} reference
     */
    default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
        Objects.requireNonNull(col);
        Objects.requireNonNull(tester);

        ElementsGetter<? extends T> getter = col.createElementsGetter();

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
    default void forEach(Processor<? super T> p) {
        Objects.requireNonNull(p);

        ElementsGetter<T> getter = this.createElementsGetter();
        getter.processRemaining(p); // iterator's index is surely on the first element
    }
}
