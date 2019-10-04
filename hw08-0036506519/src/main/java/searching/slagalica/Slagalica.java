package searching.slagalica;

import searching.algorithms.Transition;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents 'slagalica' with its initial configuration and all possible transitions
 * from that configuration.
 *
 * @author Stjepan Kovačić
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
        Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>,
        Predicate<KonfiguracijaSlagalice> {


    /**
     * Goal configuration.
     */
    private static final KonfiguracijaSlagalice GOAL =
            new KonfiguracijaSlagalice(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0});
    /**
     * Starting configuration.
     */
    private KonfiguracijaSlagalice starting;


    /**
     * Constructs slagalica with specified initial configuration.
     *
     * @param starting starting configuration
     */
    public Slagalica(KonfiguracijaSlagalice starting) {
        this.starting = starting;
    }


    @Override
    public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice konf) {
        int horizontalMove = 1;
        int verticalMove = (int) Math.sqrt(konf.getPolje().length);

        int[] array = konf.getPolje();
        int space = konf.indexOfSpace();

        int[] neighbours = {
                space - horizontalMove,
                space + horizontalMove,
                space - verticalMove,
                space + verticalMove
        };

        List<Transition<KonfiguracijaSlagalice>> list = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            if (neighbours[i] >= 0 && neighbours[i] < array.length) {
                list.add(new Transition<>(
                        new KonfiguracijaSlagalice(
                                switchPlaces(array, space, neighbours[i])), 1));
            }
        }

        return list;
    }

    /**
     * Helping method for switching places in the integer array.
     *
     * @param oldArray old array
     * @param index1   first index
     * @param index2   second index
     * @return array with switched places
     */
    private int[] switchPlaces(int[] oldArray, int index1, int index2) {
        int[] newArray = Arrays.copyOf(oldArray, oldArray.length);
        newArray[index1] = oldArray[index2];
        newArray[index2] = oldArray[index1];
        return newArray;
    }

    @Override
    public boolean test(KonfiguracijaSlagalice konf) {
        return konf.equals(GOAL);
    }

    @Override
    public KonfiguracijaSlagalice get() {
        return starting;
    }
}
