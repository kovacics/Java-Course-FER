package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;

import javax.swing.*;
import java.util.function.DoubleUnaryOperator;

/**
 * Class represents unary operator button.
 *
 * @author Stjepan Kovačić
 */
public class UnaryOperatorCalcButton extends CalcButton {

    /**
     * Normal button text.
     */
    public final String TEXT;

    /**
     * Inverse button text;
     */
    public final String TEXT_INV;

    /**
     * Constructs unary operator button.
     *
     * @param calc    calculator model
     * @param text    button text
     * @param textInv inverse button text
     * @param fun     function
     * @param funInv  inverse function
     * @param inv     inverse checkbox
     */
    public UnaryOperatorCalcButton(CalcModelImpl calc, String text, String textInv,
                                   DoubleUnaryOperator fun, DoubleUnaryOperator funInv, JCheckBox inv) {
        super(text, (e) ->
                calc.setValue(inv.isSelected() ? funInv.applyAsDouble(calc.getValue()) : fun.applyAsDouble(calc.getValue()))
        );

        TEXT = text;
        TEXT_INV = textInv;

        if(inv != null){
            inv.addActionListener(e -> this.setText(inv.isSelected() ? TEXT_INV : TEXT));
        }
    }
}
