package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;

import javax.swing.*;
import java.util.function.DoubleBinaryOperator;

/**
 * Class represents binary operator button.
 *
 * @author Stjepan Kovačić
 */
public class BinaryOperatorCalcButton extends CalcButton {

    /**
     * Normal text.
     */
    public final String TEXT;

    /**
     * Inverse function text.
     */
    public final String TEXT_INV;

    /**
     * Constructs binary operator button.
     *
     * @param calc     calculator model
     * @param text     button text
     * @param operator function
     */
    public BinaryOperatorCalcButton(CalcModelImpl calc, String text, DoubleBinaryOperator operator) {
        this(calc, text, null, operator, null, null);
    }

    /**
     * Constructs binary operator button.
     *
     * @param calc        calculator model
     * @param text        button text
     * @param textInv     inverse button text
     * @param operator    function
     * @param operatorInv inverse function
     * @param inv         inverse checkbox
     */
    public BinaryOperatorCalcButton(CalcModelImpl calc, String text, String textInv,
                                    DoubleBinaryOperator operator, DoubleBinaryOperator operatorInv, JCheckBox inv) {
        super(text, e -> {

            var currentOp = calc.getPendingBinaryOperation();
            var currentValue = calc.getValue();

            if (currentOp != null) {
                calc.setActiveOperand(currentOp.applyAsDouble(calc.getActiveOperand(), currentValue));
            } else {
                calc.setActiveOperand(currentValue);
            }

            if (textInv != null) {
                calc.setPendingBinaryOperation(inv.isSelected() ? operatorInv : operator);
            } else {
                calc.setPendingBinaryOperation(operator);
            }
            calc.clear();
        });

        TEXT = text;
        TEXT_INV = textInv;

        if(inv != null){
            inv.addActionListener(e -> this.setText(inv.isSelected() ? TEXT_INV : TEXT));
        }
    }
}
