package hr.fer.zemris.java.gui.calc.components;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class represents {@link JButton} specific and used in the {@link hr.fer.zemris.java.gui.calc.Calculator} class.
 *
 * @author Stjepan Kovačić
 */
public class CalcButton extends JButton {

    /**
     * Buttons color.
     */
    private static final Color BUTTONS_COLOR = new Color(221, 221, 255);

    /**
     * Constructs calculator button with text and action listener.
     *
     * @param text     button text
     * @param listener button action listener
     */
    public CalcButton(String text, ActionListener listener) {
        super(text);
        this.addActionListener(listener);
        this.setBackground(BUTTONS_COLOR);
    }
}
