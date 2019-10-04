package hr.fer.zemris.math;

import java.util.Objects;


/**
 * Represents vector from the origin of the coordinate system to the point
 * with {@code x} and {@code y} coordinates. Class implements all basic operations
 * with vectors.
 *
 * @author Stjepan Kovačić
 */
public class Vector2D {

    /**
     * Maximal difference between two equal double values.
     */
    public static final double EPSILON = 1E-6;
    /**
     * x coordinate of the vector
     */
    private double x;
    /**
     * y coordinate of the vector
     */
    private double y;

    /**
     * Constructs new {@code Vector2D} with given components {@code x} and {@code y}.
     *
     * @param x the x coordinate of the vector
     * @param y the y coordinate of the vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns angle of this vector in the [-pi,pi] range.
     *
     * @return angle of the vector
     */
    private double getAngle() {
        return Math.atan2(y, x);
    }

    /**
     * Returns length of this vector.
     *
     * @return length of the vector
     */
    private double getLength() {
        return Math.hypot(x, y);
    }

    /**
     * Translates this vector for the given {@code Vector2D} offset.
     *
     * @param offset Vector which is added to this vector to get translation
     * @throws NullPointerException if given offset vector is a {@code null} reference
     */
    public void translate(Vector2D offset) {
        Objects.requireNonNull(offset);

        this.x += offset.x;
        this.y += offset.y;
    }

    /**
     * Returns new {@code Vector2D} which is result of the translation of this vector for the
     * given offset.
     *
     * @param offset vector which is added to this vector to get translated one
     * @return translated vector
     * @throws NullPointerException if given offset vector is a {@code null} reference
     */
    public Vector2D translated(Vector2D offset) {
        Objects.requireNonNull(offset);
        return new Vector2D(this.x + offset.x, this.y + offset.y);
    }

    /**
     * Rotates this vector for a given angle.
     *
     * @param angle angle of the rotation
     */
    public void rotate(double angle) {
        double newAngle = this.getAngle() + angle;
        double newX = getLength() * Math.cos(newAngle);
        double newY = getLength() * Math.sin(newAngle);

        this.x = newX;
        this.y = newY;
    }

    /**
     * Returns new {@code Vector2D} which is result of rotating this vector for a given angle.
     *
     * @param angle angle of the rotation
     * @return rotated vector
     */
    public Vector2D rotated(double angle) {
        Vector2D vector = copy();
        vector.rotate(angle);
        return vector;
    }

    /**
     * Scales this vector for some given factor. Angle of the vector stays the same, only
     * x and y coordinates change proportionally.
     *
     * @param scaler factor of the scaling
     */
    public void scale(double scaler) {
        double newLength = getLength() * scaler;
        double oldAngleSaved = getAngle();

        x = newLength * Math.cos(oldAngleSaved);
        y = newLength * Math.sin(oldAngleSaved);
    }

    /**
     * Returns new {@code Vector2D} which is result of scaling this vector
     * for given scaling factor. Angle of the scaled vector stays the same, only coordinates
     * change proportionally.
     *
     * @param scaler factor of the scaling
     * @return scaled vector
     */
    public Vector2D scaled(double scaler) {
        Vector2D vector = copy();
        vector.scale(scaler);
        return vector;
    }

    /**
     * Returns {@code Vector2D} with the same coordinates.
     *
     * @return copy of this vector
     */
    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    /**
     * Public getter for the x coordinate of this vector.
     *
     * @return x coordinate of the vector
     */
    public double getX() {
        return x;
    }

    /**
     * Public getter for the y coordinate of this vector.
     *
     * @return y coordinate of the vector
     */
    public double getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector2D))
            return false;
        Vector2D other = (Vector2D) obj;
        return Math.abs(x - other.x) < EPSILON && Math.abs(y - other.y) < EPSILON;
    }

    @Override
    public String toString() {
        return "(" + String.format("%.6f", x) + "  ,  " + String.format("%.6f", y) + ")";
    }
}
