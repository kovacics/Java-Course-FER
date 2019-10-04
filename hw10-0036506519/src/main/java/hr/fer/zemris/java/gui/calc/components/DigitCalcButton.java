package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;

/**
 * Class represents digit button.
 *
 * @author Stjepan Kovačić
 */
public class DigitCalcButton extends CalcButton {

    /**
     * Constructs digit button.
     *
     * @param calc calculator model
     * @param text button text
     */
    public DigitCalcButton(CalcModelImpl calc, String text) {
        super(text, e -> calc.insertDigit(Integer.parseInt(text)));
        this.setFont(this.getFont().deriveFont(30f));
    }
}
