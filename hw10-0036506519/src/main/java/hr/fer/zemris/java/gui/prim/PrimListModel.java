package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@link ListModel} interface for listing primes.
 *
 * @author Stjepan Kovačić
 */
public class PrimListModel implements ListModel<Integer> {

    /**
     * List of all primes.
     */
    private List<Integer> primes = new ArrayList<>();

    /**
     * List of all listeners.
     */
    private List<ListDataListener> listeners = new ArrayList<>();

    /**
     * Last returned prime.
     */
    private int last = 1;

    /**
     * Constructs list model.
     */
    public PrimListModel() {
        primes.add(1);
    }

    @Override
    public int getSize() {
        return primes.size();
    }

    @Override
    public Integer getElementAt(int index) {
        return primes.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /**
     * Method generates next prime and adds it to the list.
     */
    public void next() {
        do {
            last++;
        } while (!isPrime(last));

        primes.add(last);

        ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize());
        listeners.forEach((listener) -> listener.intervalAdded(event));
    }

    /**
     * Helping method that checks if number is prime.
     *
     * @param x number to check
     * @return true if number is prime, otherwise false
     */
    private boolean isPrime(int x) {
        for (int i = 2; i <= Math.sqrt(x); i++) {
            if (x % i == 0) return false;
        }
        return true;
    }
}
