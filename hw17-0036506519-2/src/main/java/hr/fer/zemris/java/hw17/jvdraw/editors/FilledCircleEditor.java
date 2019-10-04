package hr.fer.zemris.java.hw17.jvdraw.editors;

import hr.fer.zemris.java.hw17.jvdraw.jcolorarea.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;

import javax.swing.*;
import java.awt.*;

/**
 * Class represents editors of the {@link FilledCircle} object.
 *
 * @author Stjepan Kovačić
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

    /**
     * Circle which is edited.
     */
    private FilledCircle circle;

    /**
     * Text field for the x value.
     */
    private JTextField xField = new JTextField(20);

    /**
     * Text field for the y value.
     */
    private JTextField yField = new JTextField(20);

    /**
     * Text field for the radius value.
     */
    private JTextField radiusField = new JTextField(20);

    /**
     * Circle outline color component.
     */
    private JColorArea circleOutlineColor;

    /**
     * Circle area color component.
     */
    private JColorArea circleAreaColor;

    /**
     * New x value to be set.
     */
    private int newX;

    /**
     * New y value to be set.
     */
    private int newY;

    /**
     * New r value to be set.
     */
    private int newR;

    /**
     * Constructs filled circle editors of the given circle.
     *
     * @param circle circle which gets to edited
     */
    public FilledCircleEditor(FilledCircle circle) {
        this.circle = circle;
        initEditor();
    }

    /**
     * Method initializes filled circle editors panel.
     */
    private void initEditor() {
        this.setLayout(new GridLayout(0, 1));

        xField.setText(String.valueOf(circle.getX()));
        yField.setText(String.valueOf(circle.getY()));
        radiusField.setText(String.valueOf(circle.getR()));
        circleAreaColor = new JColorArea(circle.getAreaColor());
        circleOutlineColor = new JColorArea(circle.getOutlineColor());

        add(new JLabel("Center - x"));
        add(xField);
        add(new JLabel("Center - y"));
        add(yField);
        add(new JLabel("Radius"));
        add(radiusField);

        add(new JLabel("Outline color"));
        add(circleOutlineColor);
        add(new JLabel("Area color"));
        add(circleAreaColor);
    }

    @Override
    public void checkEditing() {
        try {
            newX = Integer.parseInt(xField.getText());
            newY = Integer.parseInt(yField.getText());
            newR = Integer.parseInt(radiusField.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse to number.");
        }
    }

    @Override
    public void acceptEditing() {
        circle.setX(newX);
        circle.setY(newY);
        circle.setR(newR);

        circle.setAreaColor(circleAreaColor.getCurrentColor());
        circle.setOutlineColor(circleOutlineColor.getCurrentColor());
    }
}
