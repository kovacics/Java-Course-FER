package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Implementation of {@code Tester} for testing of even integer numbers.
 *
 * @author Stjepan Kovačić
 */
public class EvenIntegerTester implements Tester {

    /**
     * Method tests if given {@code Object} is even integer.
     *
     * @return true if it is, false if not
     */
    public boolean test(Object obj) {
        if (!(obj instanceof Integer)) return false;
        Integer i = (Integer) obj;
        return i % 2 == 0;
    }

    /**
     * Main method of the program, being called first.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Tester t = new EvenIntegerTester();
        System.out.println(t.test("Ivo"));
        System.out.println(t.test(22));
        System.out.println(t.test(3));
    }
}
