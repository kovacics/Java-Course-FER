package hr.fer.zemris.java.hw07.demo2;

/**
 * Demo program for testing {@link PrimesCollection} class.
 *
 * @author Stjepan Kovačić
 */
public class PrimesDemo2 {

    /**
     * Entry point of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(2);
        for (Integer prime : primesCollection) {
            for (Integer prime2 : primesCollection) {
                System.out.println("Got prime pair: " + prime + ", " + prime2);
            }
        }
    }
}
