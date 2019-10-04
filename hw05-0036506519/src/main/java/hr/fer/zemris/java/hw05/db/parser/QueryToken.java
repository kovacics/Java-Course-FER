package hr.fer.zemris.java.hw05.db.parser;

/**
 * Represents sequence of one or more characters that is produced by
 * {@link QueryLexer class}.
 * Token has its type and value.
 */
public class QueryToken {

    /**
     * Type of the token.
     */
    private final QueryTokenType type;

    /**
     * Value of the token.
     */
    private final Object value;

    /**
     * Constructor specifying type and value of the token.
     *
     * @param type  type of the token
     * @param value value of the token
     */
    public QueryToken(QueryTokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Getter of the token type.
     *
     * @return type of the token
     */
    public QueryTokenType getType() {
        return type;
    }

    /**
     * Getter of the token value.
     *
     * @return value of the token.
     */
    public Object getValue() {
        return value;
    }
}
