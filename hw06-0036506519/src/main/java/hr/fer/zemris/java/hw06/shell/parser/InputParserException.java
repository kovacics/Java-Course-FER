package hr.fer.zemris.java.hw06.shell.parser;

/**
 * Exception which inherit {@code RuntimeException} and is specific for {@link InputParser} class.
 * This exception is thrown for any failure in the {@code InputParser} class.
 */
public class InputParserException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public InputParserException() {
        super();
    }

    /**
     * Constructor specifying detail message of the thrown exception.
     *
     * @param message detail message of the exception
     */
    public InputParserException(String message) {
        super(message);
    }

    /**
     * Constructor specifying both message and cause of the thrown exception.
     *
     * @param message detail message of the exception
     * @param cause   cause of the exception
     */
    public InputParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor specifying cause of the thrown exception.
     *
     * @param cause cause of the exception
     */
    public InputParserException(Throwable cause) {
        super(cause);
    }
}
