package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * {@code Element} that represents integer constant.
 *
 * @author Stjepan Kovačić
 */
public class ElementConstantInteger extends Element {

    /**
     * Value of the Element
     */
    private int value;

    /**
     * Constructs instance of the class with given {@code value}
     *
     * @param value value of the element
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Getter of the element value.
     *
     * @return value of the element
     */
    public int getValue() {
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
        if (!(obj instanceof ElementConstantInteger))
            return false;
        ElementConstantInteger other = (ElementConstantInteger) obj;
        return value == other.value;
    }
}
