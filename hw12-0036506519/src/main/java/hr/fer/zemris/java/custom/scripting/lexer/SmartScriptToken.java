package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represent lexical unit of one or more characters.
 * Token has its type and value.
 *
 * @author Stjepan Kovačić
 */
public class SmartScriptToken {

    /**
     * Token type
     */
    private SmartScriptTokenType type;

    /**
     * Token value
     */
    private Object value;

    /**
     * @return token type
     */
    public SmartScriptTokenType getType() {
        return type;
    }

    /**
     * @return token value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Constructs token with given type and value
     *
     * @param type  type of the token
     * @param value value of the token
     */
    public SmartScriptToken(SmartScriptTokenType type, Object value) {
        this.type = type;
        this.value = value;
    }
}
