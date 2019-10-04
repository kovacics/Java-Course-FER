package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * {@code Element} that represents string.
 *
 * @author Stjepan Kovačić
 */
public class ElementString extends Element {

    /**
     * Value of the element.
     */
    private String value;

    /**
     * Constructs instance of the class with given value.
     *
     * @param value value of the string
     */
    public ElementString(String value) {
        this.value = value;
    }

    /**
     * Getter of the string value.
     *
     * @return value of the element
     */
    public String getValue() {
        return value;
    }

    @Override
    public String asText() {

        String result = value;
        result = result.replace("\\", "\\\\");
        result = result.replace("\"", "\\\"");

        return '\"' + result + '\"';
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
        if (!(obj instanceof ElementString))
            return false;
        ElementString other = (ElementString) obj;
        return Objects.equals(value, other.value);
    }
}
