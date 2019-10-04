package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo program for the {@link BarChart} and {@link BarChartComponent } classes.
 *
 * @author Stjepan Kovačić
 */
public class BarChartDemo extends JFrame {

    /**
     * Path to the file.
     */
    private static Path path;

    /**
     * Bar chart model.
     */
    private static BarChart model;

    /**
     * Public constructor.
     */
    public BarChartDemo() {
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initGUI();
    }

    /**
     * Initializes gui.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(new BarChartComponent(model), BorderLayout.CENTER);
        JLabel pathlabel = new JLabel(path.toAbsolutePath().toString());
        pathlabel.setHorizontalAlignment(SwingConstants.CENTER);
        cp.add(pathlabel, BorderLayout.NORTH);
    }


    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Wrong number of arguments.");
            System.exit(1);
        }

        path = Paths.get(args[0]);
        try {
            var bfr = Files.newBufferedReader(path);
            String xAxis = bfr.readLine();
            String yAxis = bfr.readLine();

            String inputs = bfr.readLine();
            List<XYValue> values = new ArrayList<>();

            for (String s : inputs.split(" ")) {
                String[] splitted = s.split(",");
                values.add(new XYValue(Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])));
            }

            int yMin = Integer.parseInt(bfr.readLine());
            int yMax = Integer.parseInt(bfr.readLine());
            int yStep = Integer.parseInt(bfr.readLine());

            model = new BarChart(values, xAxis, yAxis, yMin, yMax, yStep);
            SwingUtilities.invokeLater(() -> new BarChartDemo().setVisible(true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
