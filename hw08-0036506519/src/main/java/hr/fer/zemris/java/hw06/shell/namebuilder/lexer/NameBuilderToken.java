package hr.fer.zemris.java.hw06.shell.namebuilder.lexer;

/**
 * Token produced by {@link NameBuilderLexer} class.
 *
 * @author Stjepan Kovačić
 */
public class NameBuilderToken {

    /**
     * Type of the token.
     */
    private final NameBuilderTokenType type;

    /**
     * Value of the token.
     */
    private final Object value;

    /**
     * Constructs token.
     *
     * @param type  type of the token
     * @param value value of the token
     */
    public NameBuilderToken(NameBuilderTokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Returns type of the token.
     *
     * @return token type
     */
    public NameBuilderTokenType getType() {
        return type;
    }

    /**
     * Returns value of the token.
     *
     * @return token value
     */
    public Object getValue() {
        return value;
    }
}
