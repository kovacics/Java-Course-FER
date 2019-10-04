package hr.fer.zemris.java.hw02;

import static java.lang.Math.*;


/**
 * Represents complex number with its real and imaginary part, and also
 * magnitude and angle.
 *
 * @author Stjepan Kovačić
 */
public class ComplexNumber {

    /**
     * Delta value used in equals method for double values.
     */
    public static final double EPSILON = 1E-6;

    /**
     * Real part of this complex number
     */
    private double real;

    /**
     * Imaginary part of this complex number
     */
    private double imaginary;

    /**
     * Magnitude of this complex number
     */
    private double magnitude;

    /**
     * Angle of this complex number
     */
    private double angle;

    /**
     * Creates instance of the class with given real and imaginary parts.
     * Also calculates and sets magnitude and angle of the ComplexNumber.
     *
     * @param real      Real part of the ComplexNumber
     * @param imaginary Imaginary part of the ComplexNumber
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
        this.magnitude = pow(real * real + imaginary * imaginary, 0.5);
        this.angle = atan2(imaginary, real);

        // from [-pi,pi] to [0,2pi]
        angle = angle < 0 ? 2 * PI + angle : angle;
    }

    /**
     * Returns new complex number with only real part.
     *
     * @param real Real part of the complex number.
     * @return New ComplexNumber with given real part
     * and imaginary part set to zero.
     */
    public static ComplexNumber fromReal(double real) {
        return new ComplexNumber(real, 0);
    }

    /**
     * Returns new complex number with only imaginary part.
     *
     * @param imaginary Imaginary part of the complex number
     * @return New ComplexNumber with given imaginary part
     * and real part set to zero
     */
    public static ComplexNumber fromImaginary(double imaginary) {
        return new ComplexNumber(0, imaginary);
    }

    /**
     * Returns new complex number with given magnitude and angle.
     *
     * @param magnitude magnitude of the complex number
     * @param angle     angle of the complex number
     * @return New ComplexNumber with given magnitude and angle
     */
    public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
        double real = magnitude * Math.cos(angle);
        double imaginary = magnitude * Math.sin(angle);

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Adds other ComplexNumber to this and returns resulting ComplexNumber.
     *
     * @param other ComplexNumber which should be added
     * @return Resulting ComplexNumber of addition of two ComplexNumbers
     */
    public ComplexNumber add(ComplexNumber other) {
        if (other == null) {
            throw new NullPointerException("Complex number cannot be a null.");
        }

        double imaginary = this.imaginary + other.imaginary;
        double real = this.real + other.real;

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Subtracts two ComplexNumbers and returns resulting ComplexNumber.
     *
     * @param other ComplexNumber which should be subtracted from this ComplexNumber
     * @return Resulting ComplexNumber of subtraction of two ComplexNumbers
     */
    public ComplexNumber sub(ComplexNumber other) {
        if (other == null) {
            throw new NullPointerException("Complex number cannot be a null.");
        }

        double imaginary = this.imaginary - other.imaginary;
        double real = this.real - other.real;

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Divides two ComplexNumbers and returns resulting ComplexNumber.
     *
     * @param other ComplexNumber to divide by (denominator)
     * @return Resulting ComplexNumber of division
     */
    public ComplexNumber div(ComplexNumber other) {
        if (other == null) {
            throw new NullPointerException("Cannot divide by a null.");
        }

        double denominator = other.real * other.real + other.imaginary * other.imaginary;
        ComplexNumber nominator = this.mul(other.conjugate());

        double real = nominator.getReal() / denominator;
        double imaginary = nominator.getImaginary() / denominator;

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Multiplies two ComplexNumbers and returns resulting ComplexNumber.
     *
     * @param other ComplexNumber used in multiplication
     * @return Resulting ComplexNumber of multiplication
     */
    public ComplexNumber mul(ComplexNumber other) {
        if (other == null) {
            throw new NullPointerException("Cannot multiply by a null.");
        }

        double imaginary = this.real * other.imaginary + this.imaginary * other.real;
        double real = this.real * other.real - this.imaginary * other.imaginary;

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Returns conjugate of the this ComplexNumber.
     *
     * @return Conjugate of this ComplexNumber.
     */
    public ComplexNumber conjugate() {
        return new ComplexNumber(this.real, -this.imaginary);
    }

    /**
     * Raises this ComplexNumber to the given power.
     *
     * @param n Power on which to raise ComplexNumber
     * @return Resulting ComplexNumber
     */
    public ComplexNumber power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot raise complex number on negative power");
        }
        return fromMagnitudeAndAngle(pow(magnitude, n), n * getAngle());
    }

    /**
     * Parses given String to the ComplexNumber.
     *
     * @param s String representation of the complex number.
     * @return Parsed ComplexNumber
     * @throws NullPointerException     if given String is null reference
     * @throws IllegalArgumentException if given String is empty
     */
    public static ComplexNumber parse(String s) {

        if (s == null) {
            throw new NullPointerException("Cannot parse a null.");
        }
        if (s.isBlank()) {
            throw new IllegalArgumentException("Cannot parse an empty string.");
        }

        double imaginary = 0;
        double real = 0;

        // removing all white spaces
        s = s.replaceAll("\\s", "");

        if (s.contains("i") && !s.endsWith("i")) {
            throw new IllegalArgumentException("Cannot parse given string " + s);
        }

        // fixing "special" cases
        s = s.replaceAll("-i", "-1i");
        s = s.replaceAll("^i", "1i");
        s = s.replaceAll("\\+i", "+1i");

        // splitting real part and imaginary part
        String regex = "i|(?=[-,+])";
        String[] parts = s.split(regex);

        try {
            // only real or only imaginary part, need to find out
            if (parts.length == 1) {
                if (s.contains("i")) {
                    imaginary = Double.parseDouble(parts[0]);
                } else {
                    real = Double.parseDouble(parts[0]);
                }
            }
            // both real and imaginary, respectively
            else if (parts.length == 2 && s.contains("i")) {
                real = Double.parseDouble(parts[0]);
                imaginary = Double.parseDouble(parts[1]);
            }
            // more than 2 parts, input was wrong
            else {
                throw new IllegalArgumentException("Cannot parse given string"
                        + " to complex number : " + s);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse given string "
                    + "to complex number : " + s);
        }

        return new ComplexNumber(real, imaginary);
    }

    /**
     * Finds n-th roots of this ComplexNumber.
     *
     * @param n the root
     * @return ComplexNumber array of all roots
     */
    public ComplexNumber[] root(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Can calculate n-th root only for n > 0. "
                    + "Given n = " + n);
        }
        double magnitude = pow(this.magnitude, 1.0 / n);

        ComplexNumber[] roots = new ComplexNumber[n];
        for (int k = 0; k < n; k++) {
            double angle = (this.getAngle() + 2 * PI * k) / n;
            roots[k] = fromMagnitudeAndAngle(magnitude, angle);
        }
        return roots;
    }


    /**
     * Returns angle of the complex number.
     *
     * @return Angle of this complex number
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Returns magnitude of the complex number.
     *
     * @return Magnitude of this complex number
     */
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Returns real part of the complex number.
     *
     * @return Real part of this complex number
     */
    public double getReal() {
        return real;
    }

    /**
     * Returns imaginary part of the complex number.
     *
     * @return Imaginary part of this complex number
     */
    public double getImaginary() {
        return imaginary;
    }

    @Override
    public String toString() {
        return String.format("%.6f %s %.6fi", real, imaginary < 0 ? "-" : "+", Math.abs(imaginary));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(imaginary);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(real);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ComplexNumber) {
            ComplexNumber other = (ComplexNumber) obj;
            return (Math.abs(this.real - other.real) < EPSILON &&
                    Math.abs(this.imaginary - other.imaginary) < EPSILON);
        }
        return false;
    }
}
