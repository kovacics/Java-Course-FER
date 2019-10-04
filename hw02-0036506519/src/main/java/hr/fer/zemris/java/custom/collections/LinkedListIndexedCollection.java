package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Represents linked list-backed collection.
 *
 * <p>
 * Duplicate elements are allowed. Storage of null references is not allowed.
 * </p>
 *
 * @author Stjepan Kovačić
 */
public class LinkedListIndexedCollection extends Collection {

    /**
     * Number of elements in the collection.
     */
    private int size;
    /**
     * Reference to the first element in the collection.
     */
    private ListNode first;
    /**
     * Reference to the last element in the collection.
     */
    private ListNode last;

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
    public LinkedListIndexedCollection(Collection other) {
        Objects.requireNonNull(other);
        this.addAll(other);
    }

    /**
     * Adds given element to the collection. It adds it at the end of the list.
     *
     * @param value element to add into the collection.
     * @throws NullPointerException if given value is null.
     */
    public void add(Object value) {
        if (value == null) {
            throw new NullPointerException("Cannot add null reference into the collection.");
        }
        ListNode newNode = new ListNode(value);

        if (this.size == 0) {
            first = last = newNode;
        } else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    /**
     * Checks if collection contains specified element.
     *
     * @param value element whose presence is tested.
     * @return true if contains, false if not
     */
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    /**
     * Returns index of the first occurrence of the given element in the collection.
     *
     * @param value element whose index in the collection we search for
     * @return Index of element's first occurrence, if collection contains the
     * specified element. Otherwise, -1.
     */
    public int indexOf(Object value) {
        ListNode node = first;

        for (int i = 0; i < size; i++) {
            if (node.value.equals(value)) return i;
            node = node.next;
        }
        return -1;
    }

    /**
     * Gets element at the specified index in the collection
     * with complexity no greater than n/2 + 1.
     *
     * @param index index of element to return
     * @return element at the specified index in the collection
     */
    public Object get(int index) {
        return getNodeAtIndex(index).value;
    }

    /**
     * Retrieves node at the given index with complexity no greater than n/2 + 1.
     *
     * @param index index of the node which needs to be retrieved
     * @return node at the specified index in the collection
     * @throws IndexOutOfBoundsException if index is not in [0, size-1] range
     */
    private ListNode getNodeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Cannot get element, "
                    + "index is out of bounds for the collection.");
        }

        ListNode node;
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

    /**
     * Allocates new array of objects, and add to it all collection elements.
     *
     * @return array of collection's elements
     */
    public Object[] toArray() {
        Object[] elements = new Object[size];
        ListNode node = first;

        int i = 0;
        while (node != null) {
            elements[i++] = node.value;
            node = node.next;
        }
        return elements;
    }

    /**
     * Calls process method of the processor for each element in the collection.
     *
     * @param processor processor used for process method
     */
    @Override
    public void forEach(Processor processor) {
        ListNode node = first;
        while (node != null) {
            processor.process(node.value);
            node = node.next;
        }
    }

    /**
     * Removes all elements from the collection.
     */
    public void clear() {
        first = last = null;
        size = 0;
    }

    /**
     * If collection contains given element, it removes it from the collection.
     *
     * @param value element which needs to be removed from the collection
     * @return true if element was removed, false if wasn't (collection didn't
     * contain that element)
     */
    public boolean remove(Object value) {
        ListNode node = getNodeAtIndex(indexOf(value));
        return removeNode(node);
    }

    /**
     * Removes element of collection at the specified index.
     *
     * @param index index of element to be removed
     * @throws IndexOutOfBoundsException if index is not in [0, size-1] range.
     */
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
    private boolean removeNode(ListNode node) {
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
            return true;
        }
    }

    /**
     * Inserts element at the specified position in the collection.
     *
     * @param value    element to be inserted
     * @param position position(index) in the collection on which element should be
     *                 inserted.
     * @throws IndexOutOfBoundsException if given position is not in [0, size]
     *                                   range.
     * @throws NullPointerException      if given value is null
     */
    public void insert(Object value, int position) {
        Objects.requireNonNull(value, "Cannot insert null in the collection");

        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Cannot insert on that position.");
        }

        ListNode newNode = new ListNode(value);
        if (position != size) {
            ListNode oldNode = getNodeAtIndex(position);

            if (position != 0) {
                oldNode.prev.next = newNode;
            }
            newNode.prev = oldNode.prev;
            newNode.next = oldNode;
            oldNode.prev = newNode;

            if (position == 0) {
                first = newNode;
            }
        } else {
            this.add(value);
            return;
        }
        size++;
    }

    /**
     * Returns first element in the collection.
     *
     * @return reference to the first element in the collection
     */
    public ListNode getFirst() {
        return first;
    }

    /**
     * Returns last element in the colletion.
     *
     * @return reference to the last element in the collection
     */
    public ListNode getLast() {
        return last;
    }

    /**
     * Returns number of elements in the collection.
     *
     * @return size of the collection
     */
    public int size() {
        return size;
    }

    /**
     * Represents node of a list.
     * Node consists of the references to previous and next node,
     * and of the value of the node.
     */
    private static class ListNode {

        /**
         * Reference to the previous list node.
         */
        ListNode prev;

        /**
         * Reference to the next list node.
         */
        ListNode next;

        /**
         * Value of the node.
         */
        Object value;

        /**
         * Basic constructor for class,
         * sets references to null and value to the given value.
         *
         * @param value Value of the node
         */
        public ListNode(Object value) {
            prev = next = null;
            this.value = value;
        }
    }
}
