package hr.fer.zemris.java.custom.scripting.lexer;


/**
 * Represents exception caused by some error in {@code SmartScriptLexer} class.
 *
 * @author Stjepan Kovačić
 */
public class SmartScriptLexerException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    /**
     * Default constructor of the exception.
     */
    public SmartScriptLexerException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message message for the user
     */
    public SmartScriptLexerException(String message) {
        super(message);
    }

    /**
     * Constructs exception with specified message and cause.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public SmartScriptLexerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs exception with given cause.
     *
     * @param cause exception cause
     */
    public SmartScriptLexerException(Throwable cause) {
        super(cause);
    }
}
