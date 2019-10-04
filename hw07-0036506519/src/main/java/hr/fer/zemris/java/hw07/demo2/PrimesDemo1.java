package hr.fer.zemris.java.hw07.demo2;

/**
 * Demo program for testing {@link PrimesCollection} class.
 *
 * @author Stjepan Kovačić
 */
public class PrimesDemo1 {

    /**
     * Entry point of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
        for (Integer prime : primesCollection) {
            System.out.println("Got prime: " + prime);
        }
    }
}
