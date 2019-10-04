package hr.fer.zemris.java.gui.layouts;

/**
 * Class represents specific runtime exception that is thrown for errors
 * in {@link CalcLayout} class.
 *
 * @author Stjepan Kovačić
 */
public class CalcLayoutException extends RuntimeException {

    private static final long serialVersionUID = -7571988897781474581L;

    public CalcLayoutException() {
        super();
    }

    public CalcLayoutException(String message) {
        super(message);
    }

    public CalcLayoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalcLayoutException(Throwable cause) {
        super(cause);
    }
}
