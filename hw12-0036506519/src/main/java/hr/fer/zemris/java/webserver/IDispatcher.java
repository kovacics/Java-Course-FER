package hr.fer.zemris.java.webserver;

/**
 * Interface represents dispatcher of the request.
 *
 * @author Stjepan Kovačić
 */
public interface IDispatcher {

    /**
     * Method whose function is to dispatch request on the given path.
     *
     * @param urlPath destination path
     * @throws Exception if error happens
     */
    void dispatchRequest(String urlPath) throws Exception;
}
