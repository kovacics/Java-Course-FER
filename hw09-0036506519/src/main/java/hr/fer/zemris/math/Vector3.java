package hr.fer.zemris.math;

/**
 * Class represents vector with 3 components(dimensions).
 *
 * @author Stjepan Kovačić
 */
public class Vector3 {

    /**
     * X value of the vector.
     */
    private final double x;

    /**
     * Y value of the vector.
     */
    private final double y;

    /**
     * Z value of the vector.
     */
    private final double z;

    /**
     * Constructs vector with specified values.
     *
     * @param x x value
     * @param y y value
     * @param z z value
     */
    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns norm(length) of the vector.
     *
     * @return norm of this vector
     */
    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }


    /**
     * Returns normalized vector of this vector (same angle, length is 1).
     *
     * @return normalized vector
     */
    public Vector3 normalized() {
        return scale(1 / norm());
    }


    /**
     * Adds two vectors and returns resulting vector.
     *
     * @param other vector to add on this vector
     * @return resulting vector of addition
     */
    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }


    /**
     * Subtracts given vector from this vector and returns result.
     *
     * @param other vector to subtract from this vector
     * @return resulting vector of subtraction
     */
    public Vector3 sub(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, z - other.z);
    }


    /**
     * Calculates dot product of the two vectors.
     *
     * @param other other vector for dot product
     * @return result of dot product
     */
    public double dot(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }


    /**
     * Calculates cross product of two vectors (this x other).
     *
     * @param other second argument in cross product
     * @return cross product of the vectors
     */
    public Vector3 cross(Vector3 other) {
        double newX = this.y * other.z - this.z * other.y;
        double newY = this.z * other.x - this.x * other.z;
        double newZ = this.x * other.y - this.y * other.x;

        return new Vector3(newX, newY, newZ);
    }


    /**
     * Scale this vector for given factor.
     *
     * @param s scaler
     * @return scaled vector
     */
    public Vector3 scale(double s) {
        return new Vector3(x * s, y * s, z * s);
    }


    /**
     * Returns cosine of the angle between two vectors.
     *
     * @param other other vector
     * @return cosine of the angle
     */
    public double cosAngle(Vector3 other) {
        return dot(other) / (norm() * other.norm());
    }


    /**
     * Returns first(x) component of the vector.
     *
     * @return x value of the vector
     */
    public double getX() {
        return x;
    }

    /**
     * Returns second(y) component of the vector.
     *
     * @return y value of the vector
     */
    public double getY() {
        return y;
    }

    /**
     * Returns third(z) component of the vector.
     *
     * @return z value of the vector
     */
    public double getZ() {
        return z;
    }


    /**
     * Converts vector into array.
     *
     * @return array of vector components
     */
    public double[] toArray() {
        return new double[]{x, y, z};
    }

    @Override
    public String toString() {
        return String.format("(%.6f, %.6f, %.6f)", x, y, z);
    }
}

