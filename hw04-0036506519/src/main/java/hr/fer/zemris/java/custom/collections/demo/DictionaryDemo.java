package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Dictionary;

/**
 * Demo class for testing {@link Dictionary} class.
 *
 * @author Stjepan Kovačić
 */
public class DictionaryDemo {

    /**
     * Main method of the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Dictionary<Integer, Integer> dictionary = new Dictionary<>();

        dictionary.put(10, 20);
        dictionary.put(20, 40);
        dictionary.put(30, 60);
        dictionary.put(40, 80);
        dictionary.put(50, 90);
        dictionary.put(50, 100);

        System.out.println(dictionary);

        System.out.println(dictionary.get(30));

    }
}
