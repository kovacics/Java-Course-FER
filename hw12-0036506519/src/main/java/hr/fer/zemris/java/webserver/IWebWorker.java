package hr.fer.zemris.java.webserver;

/**
 * Interface represents web worker.
 *
 * @author Stjepan Kovačić
 */
public interface IWebWorker {

    /**
     * Method that processes given request.
     *
     * @param context request to process
     * @throws Exception if error happens
     */
    void processRequest(RequestContext context) throws Exception;
}
