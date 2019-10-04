package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents array-backed collection of objects.
 *
 * <p>Duplicate elements are allowed.
 * Storage of null references is not allowed.</p>
 *
 * @author Stjepan Kovačić
 */
public class ArrayIndexedCollection extends Collection {

    /**
     * Default capacity to be set in basic constructor.
     */
    public static final int DEFAULT_CAPACITY = 16;

    /**
     * Minimum capacity of the collection that can be set.
     */
    public static final int MIN_INITIAL_CAPACITY = 1;

    /**
     * Number of elements in collection.
     */
    private int size;

    /**
     * Array of collection's elements.
     */
    private Object[] elements;


    /**
     * Creates instance of the class. Sets capacity of the collection to 16 and
     * preallocate memory for array of elements.
     */
    public ArrayIndexedCollection() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Creates instance of the class. Sets capacity of the collection to the
     * capacity which is method's parameter.
     *
     * @param capacity size of array to be preallocate
     * @throws IllegalArgumentException if given capacity is less that 1
     */
    public ArrayIndexedCollection(int capacity) {
        if (capacity < MIN_INITIAL_CAPACITY) {
            throw new IllegalArgumentException("Initial capacity must be greater or equals " + MIN_INITIAL_CAPACITY + ".");
        }
        elements = new Object[capacity];
    }

    /**
     * Creates instance of the class. Sets capacity of the collection to 16 and
     * preallocate memory for array of elements. After allocation, method copy
     * elements of the given collection to the newly created Collection.
     *
     * @param other collection whose elements to copy in the newly created collection
     * @throws NullPointerException if given collection is null
     */
    public ArrayIndexedCollection(Collection other) {
        this(other, other.size());
    }

    /**
     * Creates instance of the class. Sets capacity of the collection to the
     * initialCapacity which is method's parameter. Also preallocates memory for
     * array of elements. After allocation, method copy elements of the given
     * collection to the newly created collection.
     *
     * @param other           collection whose elements to copy in the newly created
     *                        collection
     * @param initialCapacity size of the array to be preallocated
     * @throws NullPointerException if given collection is null
     */
    public ArrayIndexedCollection(Collection other, int initialCapacity) {
        this(initialCapacity);
        Objects.requireNonNull(other);

        if (other.size() > initialCapacity) {
            elements = reallocateArray(other.size());
        }

        this.addAll(other);
    }

    /**
     * Reallocates new array with the given size and copies all elements to the new array.
     *
     * @param capacity size of the reallocated array
     * @return reallocated array with capacity size
     */
    private Object[] reallocateArray(int capacity) {
        return Arrays.copyOf(this.elements, capacity);
    }

    /**
     * Adds given object into the collection. If the array is full, it reallocates
     * the array by doubling its size.
     *
     * @throws NullPointerException if given Object is null
     */
    @Override
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null into the collection.");
        }
        if (size >= getCapacity()) {
            elements = reallocateArray(getCapacity() * 2);
        }

        elements[size] = value;
        size++;
    }

    /**
     * Returns element at the given index in the collection.
     *
     * @param index index of the element to get
     * @return element at the given index
     * @throws IndexOutOfBoundsException if the given index is not in [0,size-1] range
     */
    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds for collection.");
        }
        return elements[index];
    }

    /**
     * Method removes every element from the collection.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Checks if Collection contains given Object.
     *
     * @param value Object to check if exist in the collection
     * @return true if contains, false otherwise
     */
    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    /**
     * Search for the given element in the collection, and returns index of the element's first occurrence.
     *
     * @param value Object for which to find index
     * @return -1 if collection does not contain the Object, index of the Object if contains
     */
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) return i;
        }
        return -1;
    }

    /**
     * Inserts the Object at the given position.
     *
     * @param value    object to insert
     * @param position position of the element insertion
     * @throws IndexOutOfBoundsException if passed position is not in [0,size] range
     */
    public void insert(Object value, int position) {
        Objects.requireNonNull(value, "Cannot insert null in the collection");

        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Can't insert here. Index is out of bounds for collection.");
        }

        add(value);
        for (int i = size - 1; i > position; i--) {
            elements[i] = elements[i - 1];
        }
        elements[position] = value;
    }

    /**
     * Removes given Object from the Collection.
     *
     * @param value Object to remove from collection
     * @return true if the Object is removed, false if not
     */
    @Override
    public boolean remove(Object value) {
        int index = indexOf(value);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * Removes element at the given index.
     *
     * @param index index of the element to remove
     * @throws IndexOutOfBoundsException if the given index is not in [0, size-1] range
     */
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Can't remove from that index, because it's out of bounds");
        }

        while (index < size - 1) {
            elements[index] = elements[index + 1];
            index++;
        }
        elements[size - 1] = null;
        size--;
    }

    /**
     * Allocates new array and copy to the array all elements of this collection.
     *
     * @return new array of Objects with all collection elements
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    /**
     * Calls process method of the given Processor for each element of the collection.
     *
     * @param processor processor used for process method
     */
    public void forEach(Processor processor) {
        for (int i = 0; i < size; i++) {
            processor.process(elements[i]);
        }
    }

    /**
     * Returns number of element in the collection.
     *
     * @return size of the Collection
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns elements of the collection.
     *
     * @return collection elements
     */
    public Object[] getElements() {
        return elements;
    }

    /**
     * Returns capacity of the collection.
     *
     * @return collection capacity
     */
    public int getCapacity() {
        return elements.length;
    }
}
