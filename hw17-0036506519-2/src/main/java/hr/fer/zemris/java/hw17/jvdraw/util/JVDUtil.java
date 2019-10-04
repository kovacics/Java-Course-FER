package hr.fer.zemris.java.hw17.jvdraw.util;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

import java.awt.*;

/**
 * Util class.
 *
 * @author Stjepan Kovačić
 */
public class JVDUtil {

    /**
     * Method parses given string line into {@link GeometricalObject} which given line describes.
     *
     * @param line input line
     * @return parsed geometrical object
     */
    public static GeometricalObject parseGeometricalObjectLine(String line) {
        String[] parts = line.split("\\s+");
        switch (parts[0]) {
        case "LINE":
            return parseLine(parts);
        case "CIRCLE":
            return parseCircle(parts);
        case "FCIRCLE":
            return parseFCircle(parts);
        default:
            throw new RuntimeException("Cannot parse into geometrical object.");
        }
    }

    /**
     * Helping method which parses {@link Line} string line.
     *
     * @param parts line parts
     * @return parsed line object
     */
    private static GeometricalObject parseLine(String[] parts) {
        int x1 = Integer.parseInt(parts[1]);
        int x2 = Integer.parseInt(parts[3]);
        int y1 = Integer.parseInt(parts[2]);
        int y2 = Integer.parseInt(parts[4]);

        int r = Integer.parseInt(parts[5]);
        int g = Integer.parseInt(parts[6]);
        int b = Integer.parseInt(parts[7]);

        return new Line(x1, x2, y1, y2, new Color(r, g, b));
    }

    /**
     * Helping method which parses {@link Circle} string line.
     *
     * @param parts line parts
     * @return parsed circle object
     */
    private static GeometricalObject parseCircle(String[] parts) {
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int ra = Integer.parseInt(parts[3]);

        int r = Integer.parseInt(parts[4]);
        int g = Integer.parseInt(parts[5]);
        int b = Integer.parseInt(parts[6]);

        return new Circle(x, y, ra, new Color(r, g, b));
    }

    /**
     * Helping method which parses {@link FilledCircle} string line.
     *
     * @param parts line parts
     * @return parsed filled circle object
     */
    private static GeometricalObject parseFCircle(String[] parts) {
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int r = Integer.parseInt(parts[3]);

        int ro = Integer.parseInt(parts[4]);
        int go = Integer.parseInt(parts[5]);
        int bo = Integer.parseInt(parts[6]);

        int ra = Integer.parseInt(parts[7]);
        int ga = Integer.parseInt(parts[8]);
        int ba = Integer.parseInt(parts[9]);

        return new FilledCircle(x, y, r, new Color(ro, go, bo), new Color(ra, ga, ba));
    }
}
