package coloring.algorithms;

import java.util.Objects;

/**
 * Represents one pixel of the picture.
 *
 * @author Stjepan Kovačić
 */
public class Pixel {

    /**
     * X-coordinate of the pixel.
     */
    public int x;

    /**
     * Y-coordinate of the pixel.
     */
    public int y;

    /**
     * Constructs pixel with given coordinates.
     *
     * @param x x value
     * @param y y value
     */
    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return x == pixel.x &&
                y == pixel.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
