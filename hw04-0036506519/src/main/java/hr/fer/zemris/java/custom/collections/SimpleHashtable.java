package hr.fer.zemris.java.custom.collections;

import java.util.*;


/**
 * Class stores pairs of Objects which are parameterized by {@code K} and {@code V}.
 * Pair consists of a {@code key} and {@code value} where {@code SimpleHashtable} maps keys to values.
 *
 * <p>It <b>is not allowed</b> to have duplicates of keys in the {@code SimpleHashtable},
 * however multiple keys can map to the same value, which means that duplicates of values are legal.</p>
 * <p>
 * It <b>is not allowed</b> for {@code key} to be {@code null}, however {@code value} can be {@code null}.
 *
 * <p>Class implements all basic methods for working with {@code SimpleHashtable}.</p>
 *
 * @param <K> parameterized type for key
 * @param <V> parameterized type for value
 * @author Stjepan Kovačić
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

    /**
     * Default capacity of {@code SimpleHashtable} to be set, unless specified other.
     */
    public static final int DEFAULT_HASHTABLE_CAPACITY = 16;

    /**
     * Limit for hashtable's load. Load means percentage of table slots that are full.
     * <p>Any greater load would be too much and would result
     * in worse performance of the {@code SimpleHashtable}</p>.
     */
    public static final double LOAD_LIMIT = 0.75;

    /**
     * Array of the table entries.
     */
    private TableEntry<K, V>[] table;

    /**
     * Number of elements in the {@code SimpleHashtable}.
     */
    private int size;

    /**
     * Number of table slots that are full.
     */
    private int slotsFull;

    /**
     * Number of times that {@code SimpleHashtable} has been modified.
     */
    private long modificationCount;


    /**
     * Default constructor that allocates table with {@code DEFAULT_HASHTABLE_CAPACITY} capacity.
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable() {
        table = (TableEntry<K, V>[]) new TableEntry[DEFAULT_HASHTABLE_CAPACITY];
    }

    /**
     * Constructor specifying capacity of the constructed {@code SimpleHashtable}.
     * <p>If given capacity is not a power of 2 than capacity of the table is defined as
     * the next power of 2 that is greater than the given number.</p>
     *
     * @param capacity hashtable capacity
     */
    @SuppressWarnings("unchecked")
    public SimpleHashtable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity of the table must be positive number.");
        }
        int realCapacity = getNextPowerOfTwo(capacity);
        table = (TableEntry<K, V>[]) new TableEntry[realCapacity];
    }

    /**
     * Returns next <b>power of 2</b>, that is <b>smallest number greater</b> than this number
     * or number that is <b>equal</b> to the given number.
     *
     * @param x number for which to find next power of 2
     * @return given number if power of 2, otherwise next power of 2
     */
    private int getNextPowerOfTwo(int x) {
        if (x < 0) {
            throw new IllegalArgumentException();
        }
        return x == 0 ? 1 : (int) Math.pow(2, Math.ceil((Math.log(x) / Math.log(2))));
    }

    /**
     * Returns number of elements in this {@code SimpleHashtable}.
     *
     * @return size of the table
     */
    public int size() {
        return size;
    }

    /**
     * Returns capacity of the table.
     *
     * @return hashtable capacity
     */
    public int getCapacity() {
        return table.length;
    }

    /**
     * Generate slot for the next key based on {@code hashCode} method.
     *
     * @param key key for which to generate slot
     * @return index in the table
     * @throws NullPointerException if given key is {@code null}
     */
    private int generateSlot(Object key) {
        Objects.requireNonNull(key);
        return Math.abs(key.hashCode()) % table.length;
    }

    /**
     * Method creates new {@code TableEntry} with specified {@code key} and {@code value}
     * and adds that {@code TableEntry} in the table.
     * <p>If table already contains that {@code key},
     * then method only overwrites {@code value} of that Entry with given {@code value}.</p>
     *
     * @param key   key of the {@code TableEntry} that should be added in the table
     * @param value value of the {@code TableEntry} that should be added in the table
     * @throws NullPointerException if given key is {@code null}
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key);

        int slot = generateSlot(key);

        if (table[slot] == null) {
            table[slot] = new TableEntry<>(key, value);
            slotsFull++;
        } else {
            TableEntry<K, V> entry = table[slot];
            TableEntry<K, V> last = entry;
            while (entry != null) {
                if (entry.getKey().equals(key)) {
                    entry.setValue(value);
                    return;
                }
                last = entry;
                entry = entry.next;
            }
            last.next = new TableEntry<>(key, value); //if slot does not contain key
        }
        size++;
        modificationCount++;
        checkLoad();
    }

    /**
     * Method checks whether table is loaded too much.
     * If it is, then method calls method for reallocating table.
     */
    private void checkLoad() {
        if ((double) slotsFull / getCapacity() >= LOAD_LIMIT) {
            reallocateTable();
        }
    }

    /**
     * Method is used to double the hashtable capacity.
     * <p>Important thing is that this method <b>does not</b> create new instance
     * of the {@code SimpleHashtable}.</p> Method only allocates new array for entries and
     * then manipulate with references and methods to regenerate slot indexes for
     * entries and to put all entries again in the table(maybe on other slots).
     */
    @SuppressWarnings("unchecked")
    private void reallocateTable() {
        var oldTable = table;
        this.table = new TableEntry[oldTable.length * 2];
        size = slotsFull = 0;

        for (TableEntry<K, V> entry : oldTable) {
            while (entry != null) {
                put(entry.getKey(), entry.getValue());
                entry = entry.next;
            }
        }
    }

    /**
     * Returns value of the {@code TableEntry} with specified {@code key}.
     * If {@code SimpleHashtable} does not contain {@code TableEntry} with given {@code key}
     * then method returns {@code null}.
     * <p>Important thing to notice is that if given {@code key} is not present in the table, or if
     * given {@code key} maps to {@code null} value, the <b>result is the same</b>
     * ({@code null} is returned).</p>
     *
     * @param key key for which to return value
     * @return value of the {@code TableEntry} with given key,
     * or null if table doesn't contain such {@code TableEntry}
     * @throws NullPointerException if given key is {@code null}
     */
    public V get(Object key) {
        Objects.requireNonNull(key);
        var entry = getTableEntry(key);
        return entry == null ? null : entry.value;
    }

    /**
     * Method checks whether table contains {@code TableEntry} with given {@code key}.
     * <b><p>Average complexity of this method is {@code O(1)}</p></b>
     *
     * @param key key for which to checks presence in the table
     * @return {@code true} if table contains key, otherwise {@code false}
     * @throws NullPointerException if given key is {@code null}
     */
    public boolean containsKey(Object key) {
        Objects.requireNonNull(key);
        return getTableEntry(key) != null;
    }

    /**
     * Method checks whether table contains {@code TableEntry} with given {@code value}.
     * <b><p>Average complexity of this method is {@code O(n)}</p></b>
     *
     * @param value value for which to checks presence in the table
     * @return {@code true} if table contains value, otherwise {@code false}
     */
    public boolean containsValue(Object value) {
        for (int i = 0; i < getCapacity(); i++) {
            TableEntry<K, V> entry = table[i];

            while (entry != null) {
                if (entry.getValue() == null) {
                    return value == null;
                }
                if (entry.getValue().equals(value)) {
                    return true;
                }
                entry = entry.next;
            }
        }
        return false;
    }

    /**
     * Returns {@code TableEntry} with specified {@code key}.
     * If {@code SimpleHashtable} does not contain {@code TableEntry} with given {@code key}
     * then method returns {@code null}.
     *
     * @param key key for which to return entry
     * @return entry with specified key
     * @throws NullPointerException if given key is {@code null}
     */
    private TableEntry<K, V> getTableEntry(Object key) {
        Objects.requireNonNull(key);

        TableEntry<K, V> entry = table[generateSlot(key)];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return entry;
            }
            entry = entry.next;
        }
        return null;
    }

    /**
     * Removes {@code TableEntry} with given {@code key} from the table.
     *
     * @param key key of the {@code TableEntry} which should removed from the table
     * @throws NullPointerException if given key is {@code null}
     */
    public void remove(Object key) {
        Objects.requireNonNull(key);

        if (containsKey(key)) {
            int slot = generateSlot(key);
            if (table[slot].getKey().equals(key)) {
                table[slot] = table[slot].next;
                if (table[slot] == null) {
                    slotsFull--;
                }
            } else {
                for (var entry = table[slot]; entry != null && entry.next != null; entry = entry.next) {
                    if (entry.next.getKey().equals(key)) {
                        entry.next = entry.next.next;
                        size--;
                        modificationCount++;
                        return;
                    }
                }
            }
            size--;
            modificationCount++;
        }
    }

    /**
     * Checks whether table is empty.
     *
     * @return {@code true} if table is empty, {@code false} if not.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Removes all entries from this {@code SimpleHashtable}.
     */
    public void clear() {
        Arrays.fill(table, null);
        size = slotsFull = 0;
        modificationCount++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (int i = 0; i < getCapacity(); i++) {
            TableEntry<K, V> entry = table[i];
            while (entry != null) {
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                if (entry.next != null || i != table.length - 1) {
                    sb.append(", ");
                }
                entry = entry.next;
            }
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<TableEntry<K, V>> iterator() {
        return new HashtableIterator();
    }

    /**
     * Static class that represent one pair of {@code key} and {@code value}.
     * Class is parameterized with {@code K} and {@code V}.
     *
     * @param <K> parameterized type for key
     * @param <V> parameterized type for value
     * @author Stjepan Kovačić
     */
    public static class TableEntry<K, V> {
        /**
         * Key of the entry.
         */
        private K key;

        /**
         * Value of the entry.
         */
        private V value;

        /**
         * Reference to the next {@code TableEntry} in the same table slot.
         */
        private TableEntry<K, V> next;

        /**
         * Constructor specifying key and value of the {@code TableEntry}.
         *
         * @param key   entry key
         * @param value entry value
         * @throws NullPointerException if given key is {@code null}
         */
        public TableEntry(K key, V value) {
            this.key = Objects.requireNonNull(key);
            this.value = value;
        }

        /**
         * Returns value of the entry.
         *
         * @return entry value
         */
        public V getValue() {
            return value;
        }

        /**
         * Sets the value of the entry.
         *
         * @param value value of the entry to be set
         */
        public void setValue(V value) {
            this.value = value;
        }

        /**
         * Returns key of the entry.
         *
         * @return entry key
         */
        public K getKey() {
            return key;
        }
    }

    /**
     * Implementation of the {@link Iterator} class.
     * Used for iterating over {@code SimpleHashtable}.
     *
     * @author Stjepan Kovačić
     */
    private class HashtableIterator implements Iterator<SimpleHashtable.TableEntry<K, V>> {

        /**
         * Slot of the current entry in the table.
         */
        private int currentSlot;

        /**
         * Number of how many more entries can iterator return,
         * defined by the size at the time of the iterator creation.
         */
        private int canReturn;

        /**
         * Current entry to return.
         */
        private TableEntry<K, V> currentEntry;

        /**
         * Last returned entry.
         */
        private TableEntry<K, V> last;

        /**
         * Number of times that method remove has been called.
         */
        private int numberOfRemoveCalls;

        /**
         * Number of times that {@code SimpleHashtable} changed to the moment of
         * this iterator creation.
         */
        private long savedModificationCount;


        /**
         * Default constructor of the iterator.
         */
        public HashtableIterator() {
            savedModificationCount = modificationCount;
            currentEntry = table[0];
            canReturn = size;
        }

        /**
         * Checks whether there are more elements to return.
         *
         * @throws ConcurrentModificationException if table has been modified after the creation
         *                                         of this iterator
         */
        @Override
        public boolean hasNext() {
            if (savedModificationCount != modificationCount) {
                throw new ConcurrentModificationException("Hashtable changed inbeetween operations, " +
                        modificationCount + " != " + savedModificationCount);
            }
            return canReturn > 0;
        }

        /**
         * Returns next {@code TableEntry}.
         *
         * @throws ConcurrentModificationException if table has been modified after the creation
         *                                         of this iterator
         * @throws NoSuchElementException          if there is no more elements to get
         */
        @Override
        public TableEntry<K, V> next() {
            if (savedModificationCount != modificationCount) {
                throw new ConcurrentModificationException("Hashtable changed inbeetween operations" +
                        modificationCount + " != " + savedModificationCount);
            }
            numberOfRemoveCalls = 0;

            if (currentSlot >= getCapacity()) {
                throw new NoSuchElementException("No more elements.");
            }
            // if entry is null, then we need to move on next slot
            if (currentEntry == null) {
                currentSlot++;
                currentEntry = table[currentSlot];
                return next();
            } else {
                last = currentEntry;
                currentEntry = currentEntry.next;
                canReturn--;
                return last;
            }
        }

        /**
         * Removes last returned {@code TableEntry} from this {@code SimpleHashtable}.
         *
         * @throws ConcurrentModificationException if table has been modified after the creation
         *                                         of this iterator
         * @throws IllegalStateException           if method called two times in a row
         */
        public void remove() {
            if (savedModificationCount != modificationCount) {
                throw new ConcurrentModificationException("Hashtable changed inbeetween operations" +
                        modificationCount + " != " + savedModificationCount);
            }
            if (numberOfRemoveCalls > 0) {
                throw new IllegalStateException("Remove can be called only once "
                        + "after every next() call");
            }
            if (SimpleHashtable.this.containsKey(last.key)) {
                SimpleHashtable.this.remove(last.key);      //<- we called remove method which will change
                numberOfRemoveCalls++;                      // modificationCount of the hash table,
                savedModificationCount++;                   // but we want to enable removing with
            }                                               // remove method from iterator, so we increment
            // savedModCount so that modCount != savedModCount
            // wouldn't throw an exception
        }
    }
}
