package hr.fer.zemris.java.hw07.observer1;

/**
 * Demo program for testing {@link IntegerStorageObserver} and
 * {@link IntegerStorage} classes.
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

        IntegerStorageObserver observer = new SquareValue();
        istorage.addObserver(observer);

        istorage.setValue(5);
        istorage.setValue(2);
        istorage.setValue(25);

        // FIRST EXAMPLE
       /* istorage.removeObserver(observer);

        istorage.addObserver(new ChangeCounter());
        istorage.addObserver(new DoubleValue(5));*/

        // SECOND EXAMPLE
        istorage.removeObserver(observer);
        istorage.addObserver(new ChangeCounter());
        istorage.addObserver(new DoubleValue(1));
        istorage.addObserver(new DoubleValue(2));
        istorage.addObserver(new DoubleValue(2));

        istorage.setValue(13);
        istorage.setValue(22);
        istorage.setValue(15);
    }
}
