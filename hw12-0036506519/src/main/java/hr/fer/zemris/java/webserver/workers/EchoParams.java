package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class represents web worker whose function is to list all parameter that worker accepted.
 *
 * @author Stjepan Kovačić
 */
public class EchoParams implements IWebWorker {

    @Override
    public void processRequest(RequestContext context) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n" +
                "<head>\n" +
                "<title>Parameters</title>\n" +
                "</head>");
        sb.append("<table>\n" +
                "<thead>\n" +
                "<tr><th>Parameter name</th><th>Parameter value</th></tr>\n" +
                "</thead>\n" +
                "<tbody>\n");
        context.getParameters().forEach((name, value) -> {
            sb.append("<tr><td>").append(name).append("</td><td>").append(value).append("</td></tr>\n");
        });
        sb.append("</tbody>\n" +
                "</table>").append("</body>\n" +
                "</html>");

        context.write(sb.toString());
    }
}
