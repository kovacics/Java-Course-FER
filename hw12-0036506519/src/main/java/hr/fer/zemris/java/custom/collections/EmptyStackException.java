package hr.fer.zemris.java.custom.collections;

/**
 * Runtime exception that is being thrown on special cases
 * while working with stack such as trying to pop from empty stack.
 *
 * @author Stjepan Kovačić
 */
public class EmptyStackException extends RuntimeException {


    private static final long serialVersionUID = 1L;

    /**
     * Basic constructor for the class.
     */
    public EmptyStackException() {

    }

    /**
     * Creates instance of the class and calls
     * super constructor with given message.
     *
     * @param message Message that explains conditions
     *                in which exception was being thrown.s
     */
    public EmptyStackException(String message) {
        super(message);
    }
}
