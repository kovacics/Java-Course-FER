package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Calculator model implementation.
 *
 * @author Stjepan Kovačić
 */
public class CalcModelImpl implements CalcModel {

    /**
     * Tells if model is editable.
     */
    private boolean editable = true;

    /**
     * Tells if current value is negative.
     */
    private boolean negative;

    /**
     * Text value.
     */
    private String textValue = "";

    /**
     * Current value.
     */
    private double value;

    /**
     * Current active operand.
     */
    private Double activeOperand;

    /**
     * Current pending operation.
     */
    private DoubleBinaryOperator pendingOperation;

    /**
     * List of all listeners.
     */
    List<CalcValueListener> listeners = new ArrayList<>();


    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    private void notifyAllListeners() {
        listeners.forEach((listener) -> listener.valueChanged(this));
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;

        if (value == Double.NEGATIVE_INFINITY) {
            textValue = "Infinity";
            negative = true;
        } else if (value == Double.POSITIVE_INFINITY) {
            textValue = "Infinity";
        } else if (Double.isNaN(value)) {
            textValue = "NaN";
        } else {
            textValue = String.valueOf(Math.abs(value));
            if (value < 0) {
                negative = true;
            }
        }

        editable = false;
        notifyAllListeners();
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public void clear() {
        value = 0;
        textValue = "";
        negative = false;
        editable = true;
        notifyAllListeners();
    }

    @Override
    public void clearAll() {
        clear();
        clearActiveOperand();
        setPendingBinaryOperation(null);
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        if (!isEditable()) {
            throw new CalculatorInputException("Cannot change sign now, textValue is not editable.");
        }
        value *= -1;
        negative = !negative;
        notifyAllListeners();
    }

    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if (!isEditable()) {
            throw new CalculatorInputException("Cannot insert decimal point now, textValue is not editable.");
        }
        if (textValue.contains(".")) {
            throw new CalculatorInputException("Cannot insert another decimal point.");
        }
        if (textValue.equals("")) {
            throw new CalculatorInputException("Cannot insert decimal point on the first place. Insert some digit first.");
        }

        textValue += ".";
        notifyAllListeners();
    }

    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (!isEditable()) {
            throw new CalculatorInputException("Cannot insert digit now, textValue is not editable.");
        }

        checkIfDigitInRange(digit);

        if (digit == 0 && textValue.equals("0")) {
            return;
        }

        try {
            String nextValue = textValue + digit;

            if (tooBigForDouble(nextValue)) {
                throw new CalculatorInputException("Input is too big for double");
            }

            value = Double.parseDouble(negative ? "-" + nextValue : nextValue);
            textValue += digit;
        } catch (NumberFormatException e) {
            throw new CalculatorInputException("Cannot parse textValue to double.");
        }
        notifyAllListeners();
    }

    /**
     * Checks if passed string represents double that is too big for double.
     *
     * @param value value to check
     * @return true if too big, false otherwise
     */
    private boolean tooBigForDouble(String value) {
        this.value = Double.parseDouble(value);
        return this.value == Double.POSITIVE_INFINITY;
    }

    /**
     * Private method that checks if digit is in [0,9] range.
     *
     * @param digit digit to check
     * @throws IllegalArgumentException if digit is not in the range
     */
    private void checkIfDigitInRange(int digit) throws IllegalArgumentException {
        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Digit must be in [0,9] range.");
        }
    }

    @Override
    public boolean isActiveOperandSet() {
        return activeOperand != null;
    }

    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!isActiveOperandSet()) {
            throw new IllegalStateException("Active operand is not set.");
        }
        return activeOperand;
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
    }

    @Override
    public void clearActiveOperand() {
        activeOperand = null;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        if (negative) {
            sb.append("-");
        }

        if (textValue.equals("")) {
            sb.append("0");
        } else if (textValue.startsWith("0") && textValue.length() > 1 && !textValue.startsWith("0.")) {
            sb.append(textValue.substring(1));
        } else {
            sb.append(textValue);
        }
        return sb.toString();
    }
}
