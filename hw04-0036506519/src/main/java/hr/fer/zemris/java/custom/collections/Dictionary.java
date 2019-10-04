package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Class stores pairs of Objects which are parameterized by {@code K} and {@code V}.
 * Pair consists of a {@code key} and {@code value} where {@code Dictionary} maps keys to values.
 * <p>It is not allowed to have duplicates of keys in the {@code Dictionary}, however multiple
 * keys can map to the same value, which means that duplicates of values are legal.</p>
 * It is not allowed for {@code key} to be {@code null}, however {@code value} can be {@code null}.
 *
 * @param <K> parameterized type for key
 * @param <V> parameterized type for value
 * @author Stjepan Kovačić
 */
public class Dictionary<K, V> {

    /**
     * Internal collection used as adaptee for {@code Dictionary}.
     */
    private ArrayIndexedCollection<Entry<K, V>> entries;

    /**
     * Default constructor. Allocates collection for storing entries.
     */
    public Dictionary() {
        entries = new ArrayIndexedCollection<>();
    }

    /**
     * Checks if number of elements in dictionary is 0.
     *
     * @return true if collection is empty, false if not
     */
    public boolean isEmpty() {
        return entries.size() == 0;
    }

    /**
     * Returns number of elements in the dictionary.
     *
     * @return size of this collection.
     */
    public int size() {
        return entries.size();
    }

    /**
     * Removes all elements from the dictionary.
     */
    public void clear() {
        entries.clear();
    }

    /**
     * Method creates new  {@code Entry}  with given {@code key} and {@code value}
     * and adds that {@code Entry} in this {@code Dictionary}.
     * <p>If dictionary contains {@code Entry} with the same {@code key},
     * then method overwrites {@code value} of the Entry with new {@code value}.</p>
     *
     * @param key   key of the entry that should be added in the dictionary
     * @param value value of the entry that should be added in the dictionary
     */
    public void put(K key, V value) {
        Objects.requireNonNull(key);
        Entry<K, V> entry = new Entry<>(key, value);

        int index = entries.indexOf(entry);
        if (index == -1) {
            entries.add(entry);
        } else {
            entries.get(index).value = value;
        }
    }

    /**
     * Returns capacity of the {@code entries} collection.
     *
     * @return capacity of this {@code Dictionary}
     */
    public int getCapacity() {
        return entries.getCapacity();
    }

    /**
     * Returns value of the {@code Entry} with specified {@code key}.
     * If there is not entry with given {@code key}, then method returns {@code null} reference.
     *
     * @param key key of the entry whose value is returned
     * @return value of the entry with given {@code key}, or null if dictionary doesn't contain
     * entry with given value
     * @throws NullPointerException if given key is {@code null}.
     */
    public V get(Object key) {
        Objects.requireNonNull(key);

        for (int i = 0; i < size(); i++) {
            if (entries.get(i).getKey().equals(key)) {
                return entries.get(i).getValue();
            }
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size(); i++) {
            sb.append(entries.get(i)).append('\n');
        }
        return sb.toString();
    }

    /**
     * Private static class that represents one entry of the {@code Dictionary}.
     * Entry is parameterized with {@code K} and {@code V}.
     *
     * @param <K> key of the entry
     * @param <V> value of the entry
     * @author Stjepan Kovačić
     */
    private static class Entry<K, V> {
        /**
         * Key of the entry
         */
        private K key;

        /**
         * Value of the entry
         */
        private V value;

        /**
         * Constructor specifying key and value of the entry.
         *
         * @param key   key
         * @param value value
         */
        public Entry(K key, V value) {
            this.key = Objects.requireNonNull(key);
            this.value = value;
        }

        /**
         * Returns key of the entry.
         *
         * @return key
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns value of the entry.
         *
         * @return value
         */
        public V getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key);
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof Entry))
                return false;
            Entry<K, V> other = (Entry<K, V>) obj;
            return Objects.equals(key, other.key);
        }

        @Override
        public String toString() {
            return key + "->" + value;
        }
    }
}


