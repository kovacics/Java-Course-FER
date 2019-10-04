package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * All token types that {@code SmartScriptLexer} can produce
 *
 * @author Stjepan Kovačić
 */
public enum SmartScriptTokenType {

    /**
     * Text token
     */
    TEXT,
    /**
     * Tag name token
     */
    TAG_NAME,
    /**
     * Variable token
     */
    VARIABLE,
    /**
     * Function token
     */
    FUNCTION,
    /**
     * Operator token
     */
    OPERATOR,
    /**
     * Integer number token
     */
    INT_NUMBER,
    /**
     * Double number token
     */
    DOUBLE_NUMBER,
    /**
     * Text inside tags token
     */
    TAG_TEXT,
    /**
     * Opening tag token
     */
    OPENING_TAG,
    /**
     * Closing token
     */
    CLOSING_TAG,
    /**
     * Last token
     */
    EOF
}
