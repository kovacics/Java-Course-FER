package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Class represents exception caused by some failure or unexpected behavior
 * inside {@link SmartScriptParser} class.
 *
 * @author Stjepan Kovačić
 */
public class SmartScriptParserException extends RuntimeException {


    private static final long serialVersionUID = 1L;


    /**
     * Default constructor for exception.
     */
    public SmartScriptParserException() {
        super();
    }

    /**
     * Constructor with message.
     *
     * @param message message of error that happen explained
     *                in more detailed way for the user
     */
    public SmartScriptParserException(String message) {
        super(message);
    }

    /**
     * Constructs exception with specified message and cause.
     *
     * @param message exception message
     * @param cause   exception cause
     */
    public SmartScriptParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs exception with given cause.
     *
     * @param cause exception cause
     */
    public SmartScriptParserException(Throwable cause) {
        super(cause);
    }
}
