package hr.fer.zemris.java.hw13.servlets;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for creating xls document that consist of pages with powers data.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/powers")
public class PowersXLSServlet extends HttpServlet {
    private static final long serialVersionUID = -1254073272932499900L;
    private HttpServletRequest req;
    private HttpServletResponse resp;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.req = req;
        this.resp = resp;

        int a;
        int b;
        int n;

        try {
            a = Integer.parseInt(req.getParameter("a"));
            b = Integer.parseInt(req.getParameter("b"));
            n = Integer.parseInt(req.getParameter("n"));
        } catch (NumberFormatException | NullPointerException e) {
            sendError("Missing or invalid parameters.");
            return;
        }

        if (a < -100 || a > 100) {
            sendError("Parameter a must be in [-100,100] range.");
        }
        if (b < -100 || b > 100) {
            sendError("Parameter b must be in [-100,100] range.");
        }
        if (n < 1 || n > 5) {
            sendError("Parameter n must be in [1,5] range.");
        }

        resp.setHeader("Content-Disposition", "attachment; filename=\"powerTable.xls\"");
        var table = getXLS(a, b, n);
        table.write(resp.getOutputStream());
    }


    /**
     * Forward request to the error page.
     *
     * @param message error message
     * @throws ServletException if servlet error happens
     * @throws IOException      if io error happens
     */
    private void sendError(String message) throws ServletException, IOException {
        req.setAttribute("message", message);
        req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
    }

    /**
     * Helping method for getting xls.
     *
     * @param a parameter a
     * @param b parameter b
     * @param n parameter n
     * @return workbook
     */
    private HSSFWorkbook getXLS(int a, int b, int n) {
        HSSFWorkbook hwb = new HSSFWorkbook();

        for (int i = 1; i <= n; i++) {
            HSSFSheet sheet = hwb.createSheet("new sheet" + i);
            for (int j = a, rowN = 0; j <= b; j++, rowN++) {
                HSSFRow row = sheet.createRow(rowN);
                row.createCell(0).setCellValue(j);
                row.createCell(1).setCellValue(Math.pow(j, i));
            }
        }

        return hwb;
    }
}
