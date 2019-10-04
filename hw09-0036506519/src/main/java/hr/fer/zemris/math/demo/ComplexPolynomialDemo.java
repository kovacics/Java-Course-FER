package hr.fer.zemris.math.demo;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Demo program for {@link ComplexPolynomial} and {@link ComplexRootedPolynomial} classes.
 *
 * @author Stjepan Kovačić
 */
public class ComplexPolynomialDemo {

    /**
     * Main method of the program.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {
        ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
                new Complex(2, 0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
        );
        ComplexPolynomial cp = crp.toComplexPolynom();
        System.out.println(crp);
        System.out.println(cp);
        System.out.println(cp.derive());

       /* Complex c = new Complex(2, 1);
        Complex c2 = new Complex(2, -1);
        System.out.println(c.divide(c2));*/
    }
}
