package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * {@code Element} that represents function.
 *
 * @author Stjepan Kovačić
 */
public class ElementFunction extends Element {

    /**
     * Name of the function.
     */
    private String name;

    /**
     * Constructs instance of the class with given function name.
     *
     * @param name name of the function
     */
    public ElementFunction(String name) {
        this.name = name;
    }

    /**
     * Getter of the function name.
     *
     * @return name of the function
     */
    public String getName() {
        return name;
    }

    @Override
    public String asText() {
        return '@' + name;
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
        if (!(obj instanceof ElementFunction))
            return false;
        ElementFunction other = (ElementFunction) obj;
        return Objects.equals(name, other.name);
    }
}
