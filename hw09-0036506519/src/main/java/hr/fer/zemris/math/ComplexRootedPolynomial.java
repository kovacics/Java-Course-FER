package hr.fer.zemris.math;

/**
 * Represents complex polynomial in
 * <i>z0*(z-z1)*(z-z2)*...*(z-zn)</i> form.
 *
 * @author Stjepan Kovačić
 */
public class ComplexRootedPolynomial {

    /**
     * Polynomial constant.
     */
    private Complex constant;

    /**
     * Polynomial roots.
     */
    private Complex[] roots;

    /**
     * Constructs polynomial with specified constant and roots.
     *
     * @param constant polynomial constant
     * @param roots    polynomial roots
     */
    public ComplexRootedPolynomial(Complex constant, Complex... roots) {
        this.constant = constant;
        this.roots = roots;
    }

    /**
     * Computes polynomial value for given complex number.
     *
     * @param z input complex number
     * @return resulting complex number value
     */
    public Complex apply(Complex z) {
        Complex result = constant;
        for (Complex root : roots) {
            result = result.multiply(z.sub(root));
        }
        return result;
    }

    /**
     * Converts polynomial from {@code ComplexRootedPolynomial} form to
     * {@code ComplexPolynomial} form.
     *
     * @return polynomial in {@code ComplexPolynomial} form.
     */
    public ComplexPolynomial toComplexPolynom() {
        ComplexPolynomial cp = new ComplexPolynomial(constant);

        for (Complex root : roots) {
            cp = cp.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
        }

        return cp;
    }

    /**
     * Returns index of the root that is closest to the given complex number,
     * and within threshold.
     *
     * @param z         complex number
     * @param threshold maximal difference
     * @return index of the root
     */
    public int indexOfClosestRootFor(Complex z, double threshold) {
        double difference = z.sub(roots[0]).module();
        int closestRoot = -1;

        for (int i = 1; i < roots.length; i++) {
            double currentDifference = roots[i].sub(z).module();
            if (currentDifference <= threshold && currentDifference < difference) {
                closestRoot = i;
                difference = currentDifference;
            }
        }

        return closestRoot;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(constant);

        for (Complex root : roots) {
            sb.append("*(z-").append(root).append(")");
        }
        return sb.toString();
    }
}
