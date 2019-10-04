package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;


/**
 * {@code Element} that represents double constant.
 *
 * @author Stjepan Kovačić
 */
public class ElementConstantDouble extends Element {

    /**
     * Element value.
     */
    private double value;

    /**
     * Constructs instance of the class with given value.
     *
     * @param value value of the element
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Getter of the element value.
     *
     * @return value of the element
     */
    public double getValue() {
        return value;
    }

    @Override
    public String asText() {
        return String.valueOf(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ElementConstantDouble))
            return false;
        ElementConstantDouble other = (ElementConstantDouble) obj;
        return Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
    }
}
