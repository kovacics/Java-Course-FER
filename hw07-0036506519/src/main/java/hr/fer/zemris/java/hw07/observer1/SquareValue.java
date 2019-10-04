package hr.fer.zemris.java.hw07.observer1;

/**
 * Concrete observer which prints to console square value of the subject.
 *
 * @author Stjepan Kovačić
 */
public class SquareValue implements IntegerStorageObserver {

    @Override
    public void valueChanged(IntegerStorage istorage) {
        System.out.println("Provided new value: " + istorage.getValue() +
                ", square is " + istorage.getValue() * istorage.getValue());
    }
}
