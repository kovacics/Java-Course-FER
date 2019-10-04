package hr.fer.zemris.java.hw05.db.parser;

/**
 * Exception which inherit {@code RuntimeException} and is specific for
 * {@link QueryLexer} class.
 * This exception is thrown for any failure in the {@code QueryLexer} class.
 */
public class QueryLexerException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public QueryLexerException() {
        super();
    }

    /**
     * Constructor specifying detail message of the thrown exception.
     *
     * @param message detail message of the exception
     */
    public QueryLexerException(String message) {
        super(message);
    }

    /**
     * Constructor specifying both message and cause of the thrown exception.
     *
     * @param message detail message of the exception
     * @param cause   cause of the exception
     */
    public QueryLexerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor specifying cause of the thrown exception.
     *
     * @param cause cause of the exception
     */
    public QueryLexerException(Throwable cause) {
        super(cause);
    }
}
