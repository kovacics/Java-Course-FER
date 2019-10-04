package hr.fer.zemris.java.hw07.observer1;

/**
 * Concrete observer which prints to counts how many times subject has changed.
 *
 * @author Stjepan Kovačić
 */
public class ChangeCounter implements IntegerStorageObserver {

    /**
     * Counter of subject changes.
     */
    private int counter;

    @Override
    public void valueChanged(IntegerStorage istorage) {
        System.out.println("Number of value changes since tracking: " + ++counter);
    }
}
