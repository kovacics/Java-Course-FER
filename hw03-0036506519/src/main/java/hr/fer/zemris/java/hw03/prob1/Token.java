package hr.fer.zemris.java.hw03.prob1;

/**
 * Lexical unit of one or more characters.
 * Token has its type and value.
 *
 * @author Stjepan Kovačić
 */
public class Token {

    /**
     * Token value
     */
    private Object value;

    /**
     * Token type
     */
    private TokenType type;


    /**
     * Constructs token with given type and value
     *
     * @param type  token type
     * @param value token value
     */
    public Token(TokenType type, Object value) {
        this.value = value;
        this.type = type;
    }

    /**
     * @return value of the token
     */
    public Object getValue() {
        return value;
    }

    /**
     * @return type of the token
     */
    public TokenType getType() {
        return type;
    }
}
