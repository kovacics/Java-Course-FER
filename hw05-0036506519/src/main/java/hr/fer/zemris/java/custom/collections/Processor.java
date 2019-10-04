package hr.fer.zemris.java.custom.collections;

/**
 * Represents processor which can do some work on given element.
 *
 * @author Stjepan Kovačić
 */
@FunctionalInterface
public interface Processor<T> {

    /**
     * Process given value.
     *
     * @param value Value to be processed
     */
    void process(T value);
}
