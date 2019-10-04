package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * Represent collection of the prime numbers.
 *
 * @author Stjepan Kovačić
 */
public class PrimesCollection implements Iterable<Integer> {

    /**
     * Number of stored primes.
     */
    private int size;

    /**
     * Constructor specifying collections size.
     *
     * @param size number of primes
     */
    public PrimesCollection(int size) {
        this.size = size;
    }


    @Override
    public Iterator<Integer> iterator() {
        return new PrimesIterator();
    }

    /**
     * Private iterator class specific for the {@code PrimesCollection} class.
     */
    private class PrimesIterator implements Iterator<Integer> {

        /**
         * Current index of the prime.
         */
        private int index;

        /**
         * Last returned prime.
         */
        private int last = 1;


        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Integer next() {
            while (true) {
                last++;
                if (isPrime(last)) {
                    index++;
                    return last;
                }
            }
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
}
