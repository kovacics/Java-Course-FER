package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * {@code Element} that represents variable.
 *
 * @author Stjepan Kovačić
 */
public class ElementVariable extends Element {

    /**
     * Name of the variable.
     */
    private String name;

    /**
     * Constructs instance of the class with given name.
     *
     * @param name name of the variable
     */
    public ElementVariable(String name) {
        this.name = name;
    }

    /**
     * Getter of the variable name.
     *
     * @return name of the variable
     */
    public String getName() {
        return name;
    }

    @Override
    public String asText() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ElementVariable))
            return false;
        ElementVariable other = (ElementVariable) obj;
        return Objects.equals(name, other.name);
    }
}
