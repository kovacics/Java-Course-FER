package hr.fer.zemris.java.custom.collections;


/**
 * Represent tester for testing objects.
 *
 * @author Stjepan Kovačić
 */
@FunctionalInterface
public interface Tester<T> {

    /**
     * Tests given object.
     *
     * @param obj Object to test.
     * @return true if test succeeded, false if test failed.
     */
    boolean test(T obj);
}
