package hr.fer.zemris.java.hw13.servlets;

import hr.fer.zemris.java.hw13.util.GlasanjeUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

    private static final long serialVersionUID = -8634787181547578623L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String resultsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        String bandsFileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

        var records = GlasanjeUtil.getVotingResults(resultsFileName, bandsFileName);

        var table = getXLS(records);
        resp.setHeader("Content-Disposition", "attachment; filename=\"glasanjeRezultati.xls\"");
        table.write(resp.getOutputStream());
    }

    /**
     * Helping method for getting xls workbook.
     *
     * @param records all records
     * @return xls table
     */
    private HSSFWorkbook getXLS(List<GlasanjeUtil.VotingResult> records) {
        HSSFWorkbook hwb = new HSSFWorkbook();

        HSSFSheet sheet = hwb.createSheet("band records");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("BAND ID");
        row.createCell(1).setCellValue("BAND NAME");
        row.createCell(2).setCellValue("BAND SONG");
        row.createCell(3).setCellValue("BAND VOTES");
        for (int i = 0; i < records.size(); i++) {
            var record = records.get(i);
            HSSFRow dataRow = sheet.createRow(i + 1);
            dataRow.createCell(0).setCellValue(record.getBand().getId());
            dataRow.createCell(1).setCellValue(record.getBand().getName());
            dataRow.createCell(2).setCellValue(record.getBand().getSong());
            dataRow.createCell(3).setCellValue(record.getVotes());
        }

        return hwb;
    }
}
