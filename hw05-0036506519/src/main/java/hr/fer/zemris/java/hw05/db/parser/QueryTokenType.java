package hr.fer.zemris.java.hw05.db.parser;

/**
 * Represent all types of {@code QueryToken}.
 */
public enum QueryTokenType {

    /**
     * Operator token, e.g. '<'.
     */
    OPERATOR,

    /**
     * Word inside quotes, e.g. "java".
     */
    STRING_LITERAL,

    /**
     * Word not inside quotes.
     */
    WORD,

    /**
     * End of file token.
     */
    EOF
}
