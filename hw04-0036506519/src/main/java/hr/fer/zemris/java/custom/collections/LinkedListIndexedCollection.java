package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * Represents linked list-backed collection with implementation of all basic methods for working
 * with this collection.
 *
 * <p>
 * Duplicate elements are allowed. Storage of null references is not allowed.
 * </p>
 *
 * @author Stjepan Kovačić
 */
public class LinkedListIndexedCollection<T> implements List<T> {

    /**
     * Number of elements in the collection.
     */
    private int size;

    /**
     * Reference to the first element in the collection.
     */
    private ListNode<T> first;

    /**
     * Reference to the last element in the collection.
     */
    private ListNode<T> last;

    /**
     * Counts how many times has collection been modified.
     */
    private long modificationCount;


    /**
     * Creates new instance of the class, an empty collection. Sets references to
     * null, and value to 0.
     */
    public LinkedListIndexedCollection() {
        first = last = null;
        size = 0;
    }

    /**
     * Creates new instance of the class and then copy all elements from given
     * collection.
     *
     * @param other other collection
     */
    public LinkedListIndexedCollection(Collection<? extends T> other) {
        this.addAll(other);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * @throws NullPointerException if given object is null
     */
    @Override
    public void add(T value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null reference into the collection.");
        }
        ListNode<T> newNode = new ListNode<>(value);

        if (this.size == 0) {
            first = last = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
        modificationCount++;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(Object value) {
        ListNode<T> node = first;

        for (int i = 0; i < size; i++) {
            if (node.value.equals(value)) return i;
            node = node.next;
        }

        return -1;
    }

    @Override
    public T get(int index) {
        return getNodeAtIndex(index).value;
    }

    /**
     * Retrieves node at the given index with complexity no greater than n/2 + 1.
     *
     * @param index index of the node which needs to be retrieved
     * @return node at the specified index in the collection
     * @throws IndexOutOfBoundsException if index is not in [0, size-1] range
     */
    private ListNode<T> getNodeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot get element, "
                    + "index is out of bounds for the collection.");
        }

        ListNode<T> node;
        if (index < this.size / 2) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = 0; i < this.size - index - 1; i++) {
                node = node.prev;
            }
        }
        return node;
    }

    @Override
    public boolean remove(Object value) {
        ListNode<T> node = getNodeAtIndex(indexOf(value));
        return removeNode(node);
    }


    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot remove from that index, "
                    + "it is out of bounds");
        }
        removeNode(getNodeAtIndex(index));
    }

    /**
     * Method removes node from the collection.
     *
     * @param node node to remove
     * @return true if removed, false otherwise
     */
    private boolean removeNode(ListNode<T> node) {
        if (node == null) {
            return false;
        } else {
            if (node == first) {
                first = first.next;
                first.prev = null;
            } else if (node == last) {
                last = last.prev;
                last.next = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            size--;
            modificationCount++;
            return true;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] elements = new Object[size];

        ListNode<T> node = first;

        int i = 0;
        while (node != null) {
            elements[i++] = node.value;
            node = node.next;
        }
        return elements;
    }

    @Override
    public void clear() {
        first = last = null;
        size = 0;
        modificationCount++;
    }

    /**
     * @throws NullPointerException if given element is null
     */
    @Override
    public void insert(T value, int position) {
        Objects.requireNonNull(value, "Cannot insert null in the collection");

        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Cannot insert on that position.");
        }

        ListNode<T> newNode = new ListNode<>(value);
        if (position != size) {
            ListNode<T> oldNode = getNodeAtIndex(position);

            if (position != 0) {
                oldNode.prev.next = newNode;
            }
            newNode.prev = oldNode.prev;
            newNode.next = oldNode;
            oldNode.prev = newNode;

            if (position == 0) {
                first = newNode;
            }
            size++;
            modificationCount++;
        } else {
            this.add(value);
        }
    }

    @Override
    public ElementsGetter<T> createElementsGetter() {
        return new LinkedListCollectionElementsGetter<>(this);
    }

    /**
     * Returns first element in the collection.
     *
     * @return reference to the first element in the collection
     */
    public ListNode<T> getFirst() {
        return first;
    }

    /**
     * Returns last element in the colletion.
     *
     * @return reference to the last element in the collection
     */
    public ListNode<T> getLast() {
        return last;
    }

    /**
     * Private static implementation of the {@code ElementsGetter} interface.
     *
     * @author Stjepan Kovačić
     */
    private static class LinkedListCollectionElementsGetter<E> implements ElementsGetter<E> {

        /**
         * Reference to the collection that {@code ElementsGetter} uses.
         */
        private LinkedListIndexedCollection<E> collection;

        /**
         * Current node to return.
         */
        private ListNode<E> node;

        /**
         * Number of times that collection changed in some way (inserted, added, removed elements),
         * at the time of constructing this {@code ElementsGetter}.
         * Is used to check if collection changed any time
         * after constructing this {@code ElementsGetter}
         */
        private long savedModificationCount;


        /**
         * Basic constructor with reference to the collection as parameter.
         *
         * @param collection collection for which to construct an {@code ElementsGetter}.
         */
        public LinkedListCollectionElementsGetter(LinkedListIndexedCollection<E> collection) {
            this.collection = collection;
            node = collection.first;
            savedModificationCount = collection.modificationCount;
        }

        @Override
        public boolean hasNextElement() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException("Collection changed "
                        + "inbetween getting elements.");
            }
            return node != null;
        }

        @Override
        public E getNextElement() {
            if (savedModificationCount != collection.modificationCount) {
                throw new ConcurrentModificationException("Collection changed "
                        + "inbetween getting elements.");
            }

            if (!hasNextElement()) {
                throw new NoSuchElementException("No more elements to get.");
            }

            ListNode<E> current = node;
            node = node.next;
            return current.value;
        }
    }

    /**
     * Represents node of a list.
     * Node consists of the references to previous and next node,
     * and of the value of the node.
     */
    private static class ListNode<E> {

        /**
         * Reference to the previous list node.
         */
        private ListNode<E> prev;

        /**
         * Reference to the next list node.
         */
        private ListNode<E> next;

        /**
         * Value of the node.
         */
        private E value;

        /**
         * Basic constructor for class,
         * sets references to null and value to the given value.
         *
         * @param value Value of the node
         */
        public ListNode(E value) {
            prev = next = null;
            this.value = value;
        }
    }
}
