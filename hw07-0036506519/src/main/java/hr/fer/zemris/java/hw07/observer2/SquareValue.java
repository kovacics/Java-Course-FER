package hr.fer.zemris.java.hw07.observer2;

/**
 * Concrete observer which prints to console square value of the subject.
 *
 * @author Stjepan Kovačić
 */
public class SquareValue implements IntegerStorageObserver {

    @Override
    public void valueChanged(IntegerStorageChange change) {
        System.out.println("Provided new value: " + change.getNewValue() +
                ", square is " + change.getNewValue() * change.getNewValue());
    }
}
