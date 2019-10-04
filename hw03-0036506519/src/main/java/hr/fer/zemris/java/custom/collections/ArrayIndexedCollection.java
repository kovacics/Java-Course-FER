package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Represents array-backed collection of objects.
 *
 * <p>Duplicate elements are allowed.
 * Storage of null references is not allowed.</p>
 *
 * @author Stjepan Kovačić
 */
public class ArrayIndexedCollection implements List {

    /**
     * Default capacity to be set in basic constructor
     */
    public static final int DEFAULT_CAPACITY = 16;

    /**
     * Minimum capacity of the collection that can be set
     */
    public static final int MIN_INITIAL_CAPACITY = 1;

    /**
     * Number of elements in collection
     */
    private int size;

    /**
     * Array of collection's elements
     */
    private Object[] elements;

    /**
     * Counts how many times have collection been modified.
     */
    private long modificationCount = 0;


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
     * Allocates new array with the given size and copies all elements to the new
     * array.
     *
     * @param capacity size of the reallocated array
     * @return reallocated array with capacity size
     */
    private Object[] reallocateArray(int capacity) {
        modificationCount++;
        return Arrays.copyOf(this.elements, capacity);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * @throws NullPointerException if given object is null
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
        modificationCount++;
    }


    @Override
    public Object get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is out of bounds for collection.");
        }
        return elements[index];
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
        modificationCount++;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
            if (elements[i].equals(value)) return i;
        }
        return -1;
    }

    /**
     * @throws NullPointerException if given object is null
     */
    @Override
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

    @Override
    public boolean remove(Object value) {
        int index = indexOf(value);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }


    @Override
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
        modificationCount++;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public ElementsGetter createElementsGetter() {
        return new ArrayCollectionElementsGetter(this);
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


    /**
     * Private static implementation of the {@link ElementsGetter} interface.
     *
     * @author Stjepan Kovačić
     */
    private static class ArrayCollectionElementsGetter implements ElementsGetter {

         /**
         * Reference to the collection on which {@code ElementsGetter} works.
         */
        private ArrayIndexedCollection collection;

        /**
         * Index of the current element in the collection.
         */
        private int index;

         /**
         * Number of times that collection changed in some way (inserted, added, removed elements),
         * at the time of constructing this {@code ElementsGetter}.
         * Is used to check if collection changed any time
         * after constructing this {@code ElementsGetter}
         */
        private long savedModificationCount;

        /**
         * Creates instance of the class for given collection.
         *
         * @param collection collection for which to construct
         *                   {@code ArrayCollectionElementsGetter}
         */
        public ArrayCollectionElementsGetter(ArrayIndexedCollection collection) {
            this.collection = collection;
            savedModificationCount = collection.modificationCount;
        }


        @Override
        public boolean hasNextElement() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException("Collection changed "
                        + "inbetween getting elements.");
            }
            return index < collection.size;
        }

        @Override
        public Object getNextElement() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException("Collection changed "
                        + "inbetween getting elements.");
            }

            if (index > collection.size - 1) {
                throw new NoSuchElementException("No more elements to get.");
            }

            return collection.get(index++);
        }
    }
}
