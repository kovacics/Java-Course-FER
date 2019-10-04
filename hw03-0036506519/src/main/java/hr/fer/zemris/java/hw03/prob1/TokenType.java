package hr.fer.zemris.java.hw03.prob1;

/**
 * Token types for {@link Lexer}
 *
 * @author Stjepan Kovačić
 */
public enum TokenType {

    /**
     * Last token
     */
    EOF,
    /**
     * Word token
     */
    WORD,
    /**
     * Number token
     */
    NUMBER,
    /**
     * Symbol token
     */
    SYMBOL
}
