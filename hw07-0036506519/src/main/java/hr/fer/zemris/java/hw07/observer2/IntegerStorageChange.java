package hr.fer.zemris.java.hw07.observer2;

/**
 * Class represents change of the subject.
 *
 * @author Stjepan Kovačić
 */
public class IntegerStorageChange {

    /**
     * Subject that changed.
     */
    private IntegerStorage storage;

    /**
     * Old value of the subject(before the change).
     */
    private int oldValue;

    /**
     * New value of the subject(after the change).
     */
    private int newValue;

    /**
     * Constructs new change with specified subject, old value and new value of the subject.
     *
     * @param storage  subject whose value changed
     * @param oldValue old value of the subject
     * @param newValue new value of the subject
     */
    public IntegerStorageChange(IntegerStorage storage, int oldValue, int newValue) {
        this.storage = storage;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    /**
     * Returns subject of the change.
     *
     * @return subject
     */
    public IntegerStorage getStorage() {
        return storage;
    }

    /**
     * Returns old value of the subject.
     *
     * @return subject old value
     */
    public int getOldValue() {
        return oldValue;
    }

    /**
     * Returns new value of the subject.
     *
     * @return subject new value
     */
    public int getNewValue() {
        return newValue;
    }
}
