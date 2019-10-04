package hr.fer.zemris.java.hw03.prob1;

/**
 * Represents exception which can happen in Lexer class.
 *
 * @author Stjepan Kovačić
 */
public class LexerException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public LexerException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message Textual representation of an error that happen.
     */
    public LexerException(String message) {
        super(message);
    }
}
