package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Class represents special map that can map key to multiple values.
 *
 * @author Stjepan Kovačić
 */
public class ObjectMultistack {


    /**
     * Map for storing pairs.
     */
    private Map<String, MultistackEntry> map = new HashMap<>();


    /**
     * Puts value on top of the stack of the given key.
     *
     * @param keyName      key of the map
     * @param valueWrapper value
     * @throws NullPointerException if arguments are null
     */
    public void push(String keyName, ValueWrapper valueWrapper) {
        Objects.requireNonNull(keyName);
        Objects.requireNonNull(valueWrapper);

        map.put(keyName, new MultistackEntry(valueWrapper, map.get(keyName)));
    }

    /**
     * Pops value from the top of the stack of the given key.
     *
     * @param keyName key of the map
     * @return value
     * @throws NullPointerException if argument is null
     */
    public ValueWrapper pop(String keyName) {
        Objects.requireNonNull(keyName);

        var entry = map.get(keyName);

        if (entry == null) {
            throw new EmptyStackException("Stack for given key is empty");
        }

        ValueWrapper value = entry.value;
        map.put(keyName, entry.next);

        return value;
    }

    /**
     * Returns value from the top of the stack of the given key.
     *
     * @param keyName key of the map
     * @return value
     * @throws NullPointerException if argument is null
     */
    public ValueWrapper peek(String keyName) {
        Objects.requireNonNull(keyName);
        return map.get(keyName).value;
    }


    /**
     * Checks if stack for given key is empty.
     *
     * @param keyName key of the map
     * @return true if stack is empty, false otherwise
     * @throws NullPointerException if argument is null
     */
    public boolean isEmpty(String keyName) {
        Objects.requireNonNull(keyName);
        return map.get(keyName) == null;
    }


    /**
     * Private static class representing one entry of the 'stack'.
     */
    private static class MultistackEntry {

        /**
         * Entry value.
         */
        private ValueWrapper value;

        /**
         * Next entry.
         */
        private MultistackEntry next;

        /**
         * Constructs entry with specified value and next entry reference.
         *
         * @param value entry value
         * @param next  next entry reference
         */
        public MultistackEntry(ValueWrapper value, MultistackEntry next) {
            this.value = value;
            this.next = next;
        }
    }
}
