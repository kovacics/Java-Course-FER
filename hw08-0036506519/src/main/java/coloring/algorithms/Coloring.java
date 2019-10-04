package coloring.algorithms;

import marcupic.opjj.statespace.coloring.Picture;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents coloring action on some picture.
 *
 * @author Stjepan Kovačić
 */
public class Coloring implements Function<Pixel, List<Pixel>>, Consumer<Pixel>, Predicate<Pixel>, Supplier<Pixel> {

    /**
     * Starting pixel.
     */
    private Pixel reference;

    /**
     * Reference to the picture which is being colored.
     */
    private Picture picture;

    /**
     * Filling color.
     */
    private int fillColor;

    /**
     * Color of the reference(starting) pixel.
     */
    private int refColor;

    /**
     * Constructs coloring.
     *
     * @param reference reference pixel
     * @param picture   picture
     * @param fillColor filling color
     */
    public Coloring(Pixel reference, Picture picture, int fillColor) {
        this.reference = reference;
        this.picture = picture;
        this.fillColor = fillColor;
        this.refColor = picture.getPixelColor(reference.x, reference.y);
    }


    @Override
    public void accept(Pixel pixel) {
        picture.setPixelColor(pixel.x, pixel.y, fillColor);
    }

    @Override
    public List<Pixel> apply(Pixel pixel) {
        LinkedList<Pixel> list = new LinkedList<>();
        list.add(new Pixel(pixel.x, pixel.y + 1));
        list.add(new Pixel(pixel.x, pixel.y - 1));
        list.add(new Pixel(pixel.x + 1, pixel.y));
        list.add(new Pixel(pixel.x - 1, pixel.y));
        return list;
    }

    @Override
    public boolean test(Pixel pixel) {
        return picture.getPixelColor(pixel.x, pixel.y) == refColor;
    }

    @Override
    public Pixel get() {
        return reference;
    }
}
