package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject in the Observer pattern, stores one integer value.
 *
 * @author Stjepan Kovačić
 */
public class IntegerStorage {

    /**
     * Stored integer value.
     */
    private int value;

    /**
     * List of all registered observers.
     */
    private List<IntegerStorageObserver> observers;

    /**
     * Constructor specifying initial value of the subject.
     *
     * @param initialValue initial value to be set
     */
    public IntegerStorage(int initialValue) {
        this.value = initialValue;
        observers = new ArrayList<>();
    }

    /**
     * Adds observer in the list of registered observers.
     *
     * @param observer observer to register
     */
    public void addObserver(IntegerStorageObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes observer from the list.
     *
     * @param observer observer to remove
     */
    public void removeObserver(IntegerStorageObserver observer) {
        observers.remove(observer);
    }

    /**
     * Removes all registered observers from the list.
     */
    public void clearObservers() {
        observers.clear();
    }

    /**
     * Returns current value of the subject.
     *
     * @return current value of the subject
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets value of the subject.
     *
     * @param value value to be set
     */
    public void setValue(int value) {
        if (this.value != value) {

            if (observers != null) {
                int savedSize = observers.size();
                for (int i = 0; i < savedSize; i++) {
                    observers.get(i).valueChanged(new IntegerStorageChange(this, this.value, value));
                    this.value = value;
                    if (savedSize != observers.size()) {
                        savedSize--;
                        i--;
                    }
                }
            }
        }
    }
}
