package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Represent complex polynomial in
 * <i>zn * z^n + zn-1 * z^n-1 + ... + z2 * z^2 + z1 * z + z0</i> form.
 *
 * @author Stjepan Kovačić
 */
public class ComplexPolynomial {

    /**
     * Factors(coefficients) of the polynomial.
     */
    private Complex[] factors;

    /**
     * Constructs polynomial with specified factors.
     *
     * @param factors polynomial factors
     */
    public ComplexPolynomial(Complex... factors) {
        this.factors = factors;
    }

    /**
     * Returns order of the polynomial.
     *
     * @return polynomial order
     */
    public short order() {
        return (short) (factors.length - 1);
    }

    /**
     * Multiplies two polynomials and returns result.
     *
     * @param p other polynomial
     * @return resulting polynomial
     */
    public ComplexPolynomial multiply(ComplexPolynomial p) {
        Complex[] newFactors = new Complex[this.order() + p.order() + 1];
        Arrays.fill(newFactors, Complex.ZERO);

        Complex[] otherFactors = p.getFactors();

        for (int i = 0; i < factors.length; i++) {
            for (int j = 0; j < otherFactors.length; j++) {
                newFactors[i + j] = newFactors[i + j].add(factors[i].multiply(otherFactors[j]));
            }
        }

        return new ComplexPolynomial(newFactors);
    }

    /**
     * Calculates first derivative of the complex polynomial.
     *
     * @return first derivative of this polynomial
     */
    public ComplexPolynomial derive() {
        Complex[] newFactors = new Complex[this.factors.length - 1];
        for (int i = 0; i < newFactors.length; i++) {
            newFactors[i] = factors[i + 1].multiply(new Complex(i + 1, 0));
        }
        return new ComplexPolynomial(newFactors);
    }

    /**
     * Method computes polynomial value for given complex number.
     *
     * @param z input complex number
     * @return resulting complex number value
     */
    public Complex apply(Complex z) {
        Complex result = factors[0];
        for (int i = 1; i < factors.length; i++) {
            result = result.add(z.power(i).multiply(factors[i]));
        }
        return result;
    }

    /**
     * Returns factor array.
     *
     * @return factors
     */
    public Complex[] getFactors() {
        return factors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = factors.length - 1; i >= 1; i--) {
            sb.append(factors[i]).append("*z^").append(i).append("+");
        }
        sb.append(factors[0]);
        return sb.toString();
    }
}
