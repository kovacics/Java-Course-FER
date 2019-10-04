package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.PI;

/**
 * Represents complex number with all basic complex number related operations.
 *
 * @author Stjepan Kovačić
 */
public class Complex {

    /**
     * Complex number <code>0+i0</code>
     */
    public static final Complex ZERO = new Complex(0, 0);
    /**
     * Complex number <code>1+i0</code>
     */
    public static final Complex ONE = new Complex(1, 0);
    /**
     * Complex number <code>-1+i0</code>
     */
    public static final Complex ONE_NEG = new Complex(-1, 0);
    /**
     * Complex number <code>0+i1</code>
     */
    public static final Complex IM = new Complex(0, 1);
    /**
     * Complex number <code>0-i1</code>
     */
    public static final Complex IM_NEG = new Complex(0, -1);
    /**
     * Regex of the full complex number.
     */
    private static final String FULL_COMPLEX = "[+,-]?[0-9]+((\\.[0-9]+)|([0-9]*))[+,-]i[0-9]+((\\.[0-9]+)|([0-9]*))";
    /**
     * Regex of the full complex number with imaginary part with value +/- 1 (only i).
     */
    private static final String FULL_COMPLEX_IM_ONE = "[+,-]?[0-9]+((\\.[0-9]+)|([0-9]*))+[+,-]i";
    /**
     * Regex of the complex number with only real part.
     */
    private static final String ONLY_REAL = "[+,-]?[0-9]+((\\.[0-9]+)|([0-9]*))";
    /**
     * Regex of the complex number with only imaginary part.
     */
    private static final String ONLY_IM = "[+,-]?i[0-9]+((\\.[0-9]+)|([0-9]*))";
    /**
     * Regex of the complex number with only imaginary part with value +/- 1.
     */
    private static final String ONLY_IM_ONE = "[+,-]?i";
    /**
     * Real part of the complex number.
     */
    private final double re;
    /**
     * Imaginary part of the complex number.
     */
    private final double im;


    public Complex() {
        this(0, 0);
    }


    /**
     * Constructs complex number with specified real and imaginary part.
     *
     * @param re real part
     * @param im imaginary part
     */
    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Private method that creates complex number from specified magnitude and angle.
     *
     * @param magnitude magnitude of the complex number
     * @param angle     angle of the complex number
     * @return constructed complex number
     */
    private static Complex getFromMagnitudeAndAngle(double magnitude, double angle) {
        double real = magnitude * Math.cos(angle);
        double imaginary = magnitude * Math.sin(angle);

        return new Complex(real, imaginary);
    }

    /**
     * Helping method that parses complex numbers.
     *
     * @param input input string
     * @return parsed complex number
     * @throws RuntimeException if input is not parsable to complex number
     */
    public static Complex parse(String input) {

        input = input.replaceAll(" ", "");
        double real;
        double im;

        try {
            if (input.matches(FULL_COMPLEX)) {
                String[] parts = input.split("i");
                real = Double.parseDouble(parts[0].substring(0, parts[0].length() - 1));
                im = Double.parseDouble(parts[1]);
                im = parts[0].endsWith("-") ? -im : im;
            } else if (input.matches(FULL_COMPLEX_IM_ONE)) {
                String realString = input.replace("i", "");
                real = Double.parseDouble(realString.substring(0, realString.length() - 1));
                im = realString.endsWith("-") ? -1 : 1;
            } else if (input.matches(ONLY_REAL)) {
                real = Double.parseDouble(input);
                im = 0;
            } else if (input.matches(ONLY_IM)) {
                real = 0;
                im = Double.parseDouble(input.replace("i", ""));
            } else if (input.matches(ONLY_IM_ONE)) {
                real = 0;
                im = Double.parseDouble(input.replace("i", "1"));
            } else {
                throw new RuntimeException("Error while parsing, wrong input. Your input was : " + input);
            }
            return new Complex(real, im);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Cannot parse input to double: " + input);
        }
    }

    /**
     * Returns module of the complex number.
     *
     * @return module of the complex number
     */
    public double module() {
        return Math.hypot(re, im);
    }

    /**
     * Multiplies two complex numbers and returns result.
     *
     * @param c other complex number
     * @return resulting complex number
     */
    public Complex multiply(Complex c) {
        Objects.requireNonNull(c, "Cannot multiply by null");

        double imaginary = this.re * c.im + this.im * c.re;
        double real = this.re * c.re - this.im * c.im;

        return new Complex(real, imaginary);
    }

    /**
     * Divides two complex numbers and returns result.
     *
     * @param c other complex number
     * @return resulting complex number
     */
    public Complex divide(Complex c) {
        Objects.requireNonNull(c, "Cannot divide by null");

        double denominator = c.re * c.re + c.im * c.im;
        Complex nominator = this.multiply(c.conjugate());

        double real = nominator.re / denominator;
        double imaginary = nominator.im / denominator;

        return new Complex(real, imaginary);
    }

    /**
     * Private method that returns conjugate complex number.
     *
     * @return conjugate of this complex number
     */
    private Complex conjugate() {
        return new Complex(re, -im);
    }

    /**
     * Adds given complex number to this complex number and returns result.
     *
     * @param c other complex number
     * @return resulting complex number
     */
    public Complex add(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    /**
     * Subtracts given complex number from this complex number and returns result.
     *
     * @param c other complex number
     * @return resulting complex number
     */
    public Complex sub(Complex c) {
        return new Complex(re - c.re, im - c.im);
    }

    /**
     * Returns negate of this complex number.
     *
     * @return negate of the complex number
     */
    public Complex negate() {
        return new Complex(-re, -im);
    }

    /**
     * Raises this complex number to the given power.
     *
     * @param n power
     * @return resulting complex number
     */
    public Complex power(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Cannot raise complex number on negative power");
        }
        return getFromMagnitudeAndAngle(Math.pow(module(), n), n * getAngle());
    }

    /**
     * Private method that returns angle of the complex number.
     *
     * @return angle of this complex number
     */
    private double getAngle() {
        double angle = Math.atan2(im, re);

        // from [-pi,pi] to [0,2pi]
        return angle < 0 ? 2 * PI + angle : angle;
    }

    /**
     * Returns list of all roots of this complex number.
     *
     * @param n the root
     * @return list of all roots
     */
    public List<Complex> root(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Can calculate n-th root only for n > 0. "
                    + "Given n = " + n);
        }
        double magnitude = Math.pow(module(), 1.0 / n);

        List<Complex> roots = new ArrayList<>();
        for (int k = 0; k < n; k++) {
            double angle = (this.getAngle() + 2 * PI * k) / n;
            roots.add(getFromMagnitudeAndAngle(magnitude, angle));
        }
        return roots;
    }

    public double getRe() {
        return re;
    }

    public double getIm() {
        return im;
    }

    @Override
    public String toString() {
        return "(" + re + (im < 0 ? "-" : "+") + "i" + Math.abs(im) + ")";
    }
}
