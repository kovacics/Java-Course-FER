package hr.fer.zemris.java.hw07.observer2;

/**
 * Interface represents observer in the Observer pattern.
 * All concrete observers should implement this interface.
 *
 * @author Stjepan Kovačić
 */
public interface IntegerStorageObserver {

    /**
     * Does some work after getting notified that subject value changed.
     *
     * @param change change of the subject
     */
    void valueChanged(IntegerStorageChange change);
}
