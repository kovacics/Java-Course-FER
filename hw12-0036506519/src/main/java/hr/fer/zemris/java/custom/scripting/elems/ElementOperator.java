package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * {@code Element} that represents operator.
 *
 * @author Stjepan Kovačić
 */
public class ElementOperator extends Element {

    /**
     * Symbol of the operator.
     */
    private String symbol;

    /**
     * Constructs instance of the class with given symbol.
     *
     * @param symbol operator symbol
     */
    public ElementOperator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Getter of the symbol.
     *
     * @return symbol of the operator
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String asText() {
        return symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof ElementOperator))
            return false;
        ElementOperator other = (ElementOperator) obj;
        return Objects.equals(symbol, other.symbol);
    }
}
