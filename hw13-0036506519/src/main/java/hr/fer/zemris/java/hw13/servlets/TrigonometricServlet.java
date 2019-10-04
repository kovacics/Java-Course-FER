package hr.fer.zemris.java.hw13.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet for getting trigonometric table.
 *
 * @author Stjepan Kovačić
 */
@WebServlet(name = "trig", urlPatterns = {"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {

    private static final long serialVersionUID = 4311085577773252673L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TrigonometricRecord> trigRecords = new ArrayList<>();
        String a = req.getParameter("a");
        String b = req.getParameter("b");
        Integer varA = 0;
        Integer varB = 360;

        if (a != null) {
            try {
                varA = Integer.parseInt(a);
            } catch (NumberFormatException ignore) {
            }
        }

        if (b != null) {
            try {
                varB = Integer.parseInt(b);
            } catch (NumberFormatException ignore) {
            }
        }

        if (varA > varB) {
            varA = varA + varB;
            varB = varA - varB;
            varA = varA - varB;
        }

        if (varB > varA + 720) {
            varB = varA + 720;
        }

        for (int i = varA; i <= varB; i++) {
            double sin = Math.sin(Math.toRadians(i));
            double cos = 1 - sin * sin;
            trigRecords.add(new TrigonometricRecord(i, sin, cos));
        }

        req.setAttribute("trigRecords", trigRecords);
        req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
    }

    /**
     * Java bean class used for storing trigonometric records.
     */
    public static class TrigonometricRecord {

        /**
         * Angle value.
         */
        private double angle;

        /**
         * Sine value.
         */
        private double sin;

        /**
         * Cosine value.
         */
        private double cos;

        /**
         * Constructs trigonometric record with specified angle, sine and cosine.
         *
         * @param angle angle value
         * @param sin   sine value
         * @param cos   cosine value
         */
        public TrigonometricRecord(double angle, double sin, double cos) {
            this.angle = angle;
            this.sin = sin;
            this.cos = cos;
        }

        public double getAngle() {
            return angle;
        }

        public double getSin() {
            return sin;
        }

        public double getCos() {
            return cos;
        }
    }
}
