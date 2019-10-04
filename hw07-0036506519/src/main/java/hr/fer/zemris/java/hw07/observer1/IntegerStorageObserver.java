package hr.fer.zemris.java.hw07.observer1;

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
     * @param istorage subject whose value changed
     */
    void valueChanged(IntegerStorage istorage);
}
