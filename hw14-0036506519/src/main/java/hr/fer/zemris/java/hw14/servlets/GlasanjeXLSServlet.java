package hr.fer.zemris.java.hw14.servlets;

import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for getting voting results as xls document.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

    private static final long serialVersionUID = 4969579824210067877L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pollID = req.getParameter("pollID");

        try {
            List<PollOption> records = DAOProvider.getDao().getPollOptions(pollID);
            var table = getXLS(records);
            resp.setHeader("Content-Disposition", "attachment; filename=\"glasanjeRezultati.xls\"");
            table.write(resp.getOutputStream());
        } catch (DAOException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
        }
    }

    /**
     * Helping method for getting xls workbook.
     *
     * @param records all records
     * @return xls table
     */
    private HSSFWorkbook getXLS(List<PollOption> records) {
        HSSFWorkbook hwb = new HSSFWorkbook();

        HSSFSheet sheet = hwb.createSheet("band records");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("NAME");
        row.createCell(2).setCellValue("LINK");
        row.createCell(3).setCellValue("VOTES COUNT");
        for (int i = 0; i < records.size(); i++) {
            var record = records.get(i);
            HSSFRow dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(record.getId());
            dataRow.createCell(1).setCellValue(record.getOptionTitle());
            dataRow.createCell(2).setCellValue(record.getOptionLink());
            dataRow.createCell(3).setCellValue(record.getVotesCount());
        }

        return hwb;
    }
}
