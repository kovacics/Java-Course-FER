package hr.fer.zemris.java.hw17.jvdraw.editors;

import hr.fer.zemris.java.hw17.jvdraw.jcolorarea.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

import javax.swing.*;
import java.awt.*;

/**
 * Class represents editors of the {@link Line} object.
 *
 * @author Stjepan Kovačić
 */
public class LineEditor extends GeometricalObjectEditor {

    /**
     * Line which gets to be edited.
     */
    private Line line;

    /**
     * Text field for the X1 value.
     */
    private JTextField x1Field = new JTextField(20);

    /**
     * Text field for the X2 value.
     */
    private JTextField x2Field = new JTextField(20);

    /**
     * Text field for the Y1 value.
     */
    private JTextField y1Field = new JTextField(20);

    /**
     * Text field for the Y2 value.
     */
    private JTextField y2Field = new JTextField(20);

    /**
     * New x1 value to be set.
     */
    private int newX1;

    /**
     * New x2 value to be set.
     */
    private int newX2;

    /**
     * New y1 value to be set.
     */
    private int newY1;

    /**
     * New y2 value to be set.
     */
    private int newY2;

    /**
     * Line color component.
     */
    private JColorArea lineColor;

    /**
     * Constructs line editors of the given line.
     *
     * @param line line which gets to be edited
     */
    public LineEditor(Line line) {
        this.line = line;
        initEditor();
    }

    /**
     * Method initialzes line editors panel.
     */
    private void initEditor() {
        this.setLayout(new GridLayout(0, 1));

        x1Field.setText(String.valueOf(line.getX1()));
        x2Field.setText(String.valueOf(line.getX2()));
        y1Field.setText(String.valueOf(line.getY1()));
        y2Field.setText(String.valueOf(line.getY2()));
        lineColor = new JColorArea(line.getColor());

        add(new JLabel("Start X"));
        add(x1Field);
        add(new JLabel("End X"));
        add(x2Field);
        add(new JLabel("Start Y"));
        add(y1Field);
        add(new JLabel("End Y"));
        add(y2Field);
        add(new JLabel("Color"));
        add(lineColor);
    }

    @Override
    public void checkEditing() {
        try {
            newX1 = Integer.parseInt(x1Field.getText());
            newX2 = Integer.parseInt(x2Field.getText());
            newY1 = Integer.parseInt(y1Field.getText());
            newY2 = Integer.parseInt(y2Field.getText());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse to number.");
        }
    }

    @Override
    public void acceptEditing() {
        line.setX1(newX1);
        line.setX2(newX2);
        line.setY1(newY1);
        line.setY2(newY2);
        line.setColor(lineColor.getCurrentColor());
    }
}
