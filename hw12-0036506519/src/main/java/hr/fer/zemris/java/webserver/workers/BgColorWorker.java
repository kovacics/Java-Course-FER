package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class represents web worker whose function is to set
 * persistent color parameter and create html document
 * which informs user if color has been changed.
 *
 * @author Stjepan Kovačić
 */
public class BgColorWorker implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        StringBuilder doc = new StringBuilder();
        doc.append("<html><body>");
        doc.append("<a href=\"/index2.html\">Home</a>\r\n");

        String color = context.getParameter("bgcolor");

        if (color != null && color.matches("\\p{XDigit}+")) {
            context.setPersistentParameter("bgcolor", color);
            doc.append("<p>Color is updated</p>");
        } else {
            doc.append("<p>Color is not updated</p>");
        }

        doc.append("</body></html>");
        context.write(doc.toString());
    }
}
