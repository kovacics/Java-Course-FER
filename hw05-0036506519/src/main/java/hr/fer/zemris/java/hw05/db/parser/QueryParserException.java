package hr.fer.zemris.java.hw05.db.parser;

/**
 * Exception which inherit {@code RuntimeException} and is specific for
 * {@link QueryParser} class.
 * This exception is thrown for any failure in the {@code QueryParser} class.
 */
public class QueryParserException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public QueryParserException() {
        super();
    }

    /**
     * Constructor specifying detail message of the thrown exception.
     *
     * @param message detail message of the exception
     */
    public QueryParserException(String message) {
        super(message);
    }

    /**
     * Constructor specifying both message and cause of the thrown exception.
     *
     * @param message detail message of the exception
     * @param cause   cause of the exception
     */
    public QueryParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor specifying cause of the thrown exception.
     *
     * @param cause cause of the exception
     */
    public QueryParserException(Throwable cause) {
        super(cause);
    }
}
