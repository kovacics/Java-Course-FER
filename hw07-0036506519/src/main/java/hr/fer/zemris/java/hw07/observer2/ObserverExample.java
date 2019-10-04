package hr.fer.zemris.java.hw07.observer2;

import hr.fer.zemris.java.hw07.observer1.IntegerStorageObserver;

/**
 * Demo program for testing {@link IntegerStorageObserver} and
 * {@link hr.fer.zemris.java.hw07.observer1.IntegerStorage} classes.
 *
 * @author Stjepan Kovačić
 */
public class ObserverExample {

    /**
     * Entry point of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        IntegerStorage istorage = new IntegerStorage(20);

        istorage.addObserver(new SquareValue());
        istorage.addObserver(new DoubleValue(3));
        istorage.addObserver(new ChangeCounter());

        istorage.setValue(1);
        istorage.setValue(2);
        istorage.setValue(3);
    }
}
