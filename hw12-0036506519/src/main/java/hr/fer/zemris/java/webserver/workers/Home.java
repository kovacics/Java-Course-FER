package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class represents web worker whose function is to create our home page.
 *
 * @author Stjepan Kovačić
 */
public class Home implements IWebWorker {

    /**
     * Default color of the background.
     */
    private static final String DEFAULT_BACKGROUND = "7F7F7F";

    @Override
    public void processRequest(RequestContext context) throws Exception {
        String bg = context.getPersistentParameter("bgcolor");
        context.setTemporaryParameter("background", bg == null ? DEFAULT_BACKGROUND : bg);
        context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
    }
}
