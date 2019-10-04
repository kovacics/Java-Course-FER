package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import java.util.Map;

/**
 * Class represents web worker whose function is creating simple html document for
 * calculating sum of two numbers.
 *
 * @author Stjepan Kovačić
 */
public class SumWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        Map<String, String> params = context.getParameters();
        int a = 1;
        int b = 2;

        try {
            String va = params.get("a");
            a = va == null ? a : Integer.parseInt(va);
        } catch (NumberFormatException ignore) {
        }
        try {
            String vb = params.get("b");
            b = vb == null ? b : Integer.parseInt(vb);
        } catch (NumberFormatException ignore) {
        }

        context.setTemporaryParameter("zbroj", String.valueOf(a + b));
        context.setTemporaryParameter("varA", String.valueOf(a));
        context.setTemporaryParameter("varB", String.valueOf(b));
        context.setTemporaryParameter("imgName", (a + b) % 2 == 0 ? "java.gif" : "motivation.jpg");
        context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
    }
}
