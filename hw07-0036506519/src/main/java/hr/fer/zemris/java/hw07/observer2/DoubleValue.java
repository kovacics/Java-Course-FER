package hr.fer.zemris.java.hw07.observer2;

/**
 * Concrete observer which prints to console double the current value of the subject.
 * Observer works only for n changes, where n is number given in constructor.
 * After n changes observer removes itself from the registered observers.
 *
 * @author Stjepan Kovačić
 */
public class DoubleValue implements IntegerStorageObserver {

    /**
     * Counter of subject changes.
     * Observer 'works' only for this number of changes.
     */
    private int counter;

    /**
     * Constructs observer with specified counter.
     *
     * @param counter observer counter
     */
    public DoubleValue(int counter) {
        this.counter = counter;
    }

    @Override
    public void valueChanged(IntegerStorageChange change) {
        System.out.println("Double value: " + change.getNewValue() * 2);
        if (--counter == 0) {
            change.getStorage().removeObserver(this);
        }
    }
}
