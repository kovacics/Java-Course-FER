package hr.fer.zemris.java.hw15.forms;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class represents form that has implementations of the basic form methods.
 *
 * @author Stjepan Kovačić
 */
public abstract class Form {

    /**
     * Map of errors.
     */
    Map<String, String> errors = new HashMap<>();

    /**
     * Method checks if form has some errors.
     *
     * @return true if form has errors, false otherwise
     */
    public boolean hasErrors() {
        errors.forEach((key, value) -> System.out.println(key + "->" + value));
        return !errors.isEmpty();
    }

    /**
     * Method checks if error with given name exist.
     *
     * @param name name of error
     * @return true if error exist, false otherwise
     */
    public boolean errorExist(String name) {
        return errors.containsKey(name);
    }

    /**
     * Method returns error message with the given error name.
     *
     * @param name error name
     * @return error message
     */
    public String getErrorFor(String name) {
        return errors.get(name);
    }

    /**
     * Method validates form.
     */
    public abstract void validate();

    /**
     * Method fill form from http request.
     *
     * @param req http request with form parameters
     */
    public abstract void fillFromRequest(HttpServletRequest req);
}
