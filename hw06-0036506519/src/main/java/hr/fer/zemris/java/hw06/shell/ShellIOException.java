package hr.fer.zemris.java.hw06.shell;

/**
 * Runtime exception specific for the {@link MyShell} class.
 *
 * @author Stjepan Kovačić
 */
public class ShellIOException extends RuntimeException {

    private static final long serialVersionUID = 5940709970169552888L;

    /**
     * Default constructor.
     */
    public ShellIOException() {
    }

    /**
     * Constructor specifying message.
     *
     * @param message detail message of the exception
     */
    public ShellIOException(String message) {
        super(message);
    }

    /**
     * Constructor specifying message and the cause.
     *
     * @param message detail message of the exception
     * @param cause   cause of the exception
     */
    public ShellIOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor the cause of the exception.
     *
     * @param cause cause of the exception
     */
    public ShellIOException(Throwable cause) {
        super(cause);
    }
}
