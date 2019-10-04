package hr.fer.zemris.java.custom.collections;


/**
 * Interface that represents list of elements, and defines basic methods for working with lists.
 *
 * @author Stjepan Kovačić
 */
public interface List<T> extends Collection<T> {

    /**
     * Gets element at the specified index in the collection.
     *
     * @param index index of element to return
     * @return element at the specified index in the collection
     * @throws IndexOutOfBoundsException if the given index is not in [0,size-1]
     */
    T get(int index);


    /**
     * Inserts element at the specified position in the collection.
     *
     * @param value    element to be inserted
     * @param position position(index) in the collection on which element should be
     *                 inserted.
     * @throws IndexOutOfBoundsException if given position is not in [0,size] range
     */
    void insert(T value, int position);

    /**
     * Returns index of the first occurrence of the given element in the collection.
     *
     * @param value element whose index in the collection we search for
     * @return Index of element's first occurrence, if collection contains the
     * specified element. Otherwise, -1.
     */
    int indexOf(Object value);

    /**
     * Removes element of collection at the specified index.
     *
     * @param index index of element to be removed
     * @throws IndexOutOfBoundsException if the given index is not in [0, size-1] range
     */
    void remove(int index);
}
