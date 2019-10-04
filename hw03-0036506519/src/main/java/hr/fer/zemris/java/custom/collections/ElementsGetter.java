package hr.fer.zemris.java.custom.collections;


import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Interface that represents getter of some elements.
 *
 * @author Stjepan Kovačić
 */
public interface ElementsGetter {

    /**
     * Checks if ElementsGetter has more elements to get
     *
     * @return true if there is more element, false if not
     * @throws ConcurrentModificationException if elements have been modified while getting elements
     */
    boolean hasNextElement();

    /**
     * Gets the next element.
     *
     * @return next element
     * @throws NoSuchElementException          if there is no more elements to get
     * @throws ConcurrentModificationException if elements have been modified while getting elements
     */
    Object getNextElement();


    /**
     * Calls process method of the given Processor for all remaining elements.
     *
     * @param p Processor whose method process will get called on elements.
     * @throws NullPointerException if given processor is null
     */
    default void processRemaining(Processor p) {
        Objects.requireNonNull(p, "Processor cannot be null");
        while (hasNextElement()) {
            p.process(getNextElement());
        }
    }
}
