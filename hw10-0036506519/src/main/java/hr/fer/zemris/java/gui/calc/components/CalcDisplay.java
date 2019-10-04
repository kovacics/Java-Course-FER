package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;

import javax.swing.*;
import java.awt.*;

/**
 * Component represents calculator display.
 *
 * @author Stjepan Kovačić
 */
public class CalcDisplay extends JLabel {

    /**
     * Constructs calculator display.
     *
     * @param calc calculator model
     * @param text display text
     */
    public CalcDisplay(CalcModelImpl calc, String text) {
        super(text);
        this.setFont(this.getFont().deriveFont(30f));
        this.setBackground(Color.YELLOW);
        this.setOpaque(true);
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        calc.addCalcValueListener(m -> this.setText(m.toString()));
    }
}
