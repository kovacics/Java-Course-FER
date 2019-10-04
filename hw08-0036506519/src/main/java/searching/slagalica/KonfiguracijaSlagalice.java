package searching.slagalica;

import java.util.Arrays;

/**
 * Represents current configuration of the 'slagalica'.
 *
 * @author Stjepan Kovačić
 */
public class KonfiguracijaSlagalice {

    /**
     * Size of the array.
     */
    private static final int SIZE = 9;

    /**
     * Array of the field values.
     */
    private int[] polje;


    /**
     * Constructor specifying array.
     *
     * @param fields array of the field values.
     */
    public KonfiguracijaSlagalice(int[] fields) {
        this.polje = fields;
    }

    /**
     * Returns copy of the fields array.
     *
     * @return array of the field values
     */
    public int[] getPolje() {
        return Arrays.copyOf(polje, polje.length);
    }

    /**
     * Returns index of the space(empty) field.
     *
     * @return space field index
     */
    public int indexOfSpace() {
        for (int i = 0; i < SIZE; i++) {
            if (polje[i] == 0) return i;
        }
        return -1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KonfiguracijaSlagalice that = (KonfiguracijaSlagalice) o;
        return Arrays.equals(polje, that.polje);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(polje);
    }

    @Override
    public String toString() {
        int dimension = (int) Math.sqrt(SIZE);
        StringBuilder table = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            table.append(polje[i] == 0 ? "*" : polje[i]).append(" ");
            if (i % dimension == dimension - 1 && i != SIZE - 1) {
                table.append('\n');
            }
        }
        return table.toString();
    }
}
