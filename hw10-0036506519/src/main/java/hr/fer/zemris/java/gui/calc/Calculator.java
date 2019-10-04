package hr.fer.zemris.java.gui.calc;


import hr.fer.zemris.java.gui.calc.components.*;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

/**
 * Class represents calculator with basic operations and display.
 *
 * @author Stjepan Kovačić
 */
public class Calculator extends JFrame {

    /**
     * Calc model implementation.
     */
    private CalcModelImpl calc = new CalcModelImpl();

    /**
     * Calcultor stack.
     */
    private Stack<Double> stack = new Stack<>();

    /**
     * Calculator display.
     */
    private JLabel calcDisplay;

    /**
     * Content pane.
     */
    private Container cp;

    /**
     * Inverse button.
     */
    private JCheckBox inv = new JCheckBox("inv");

    /**
     * Constructs calculator.
     */
    public Calculator() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Java Calculator v1.0");
        setLocation(80, 60);
        setSize(700, 350);
        initGUI();
    }

    /**
     * Private method that initializes gui.
     */
    private void initGUI() {
        cp = getContentPane();
        cp.setLayout(new CalcLayout(5));

        addDisplay();
        addDigitButtons();
        addOperators();
        addFunctionButtons();
        addOtherButtons();
    }

    /**
     * Adds display to the calculator.
     */
    private void addDisplay() {
        calcDisplay = new CalcDisplay(calc, "0");
        cp.add(calcDisplay, "1,1");
    }

    /**
     * Adds digit buttons to the calculator.
     */
    private void addDigitButtons() {
        int k = 1;
        for (int i = 4; i > 1; i--) {
            for (int j = 3; j < 6; j++) {
                addDigitButton(new RCPosition(i, j), k++);
            }
        }
        addDigitButton("5,3", 0);
    }

    /**
     * Adds digit button to the calculator.
     */
    private void addDigitButton(Object rcPosition, int k) {
        var button = new DigitCalcButton(calc, String.valueOf(k));
        cp.add(button, rcPosition);
    }

    /**
     * Adds functions buttons to the calculator.
     */
    private void addFunctionButtons() {
        addFunctionButton("2,2", "sin", "arcsin", Math::sin, Math::asin);
        addFunctionButton("3,2", "cos", "arccos", Math::cos, Math::acos);
        addFunctionButton("4,2", "tan", "arctan", Math::tan, Math::atan);
        addFunctionButton("5,2", "ctg", "arcctg", v -> 1 / Math.tan(v), v -> Math.PI / 2 - Math.atan(v));
        addFunctionButton("2,1", "1/x", "1/x", v -> 1 / v, v -> 1 / v);
        addFunctionButton("3,1", "log", "10^x", Math::log10, v -> Math.pow(10, v));
        addFunctionButton("4,1", "ln", "e^x", Math::log, v -> Math.pow(Math.E, v));
        addBiFunctionButton("5,1", "x^n", "x^(1/n)", Math::pow, (v1, v2) -> Math.pow(v1, 1 / v2));
    }

    /**
     * Adds function button to the calculator.
     */
    private void addFunctionButton(Object pos, String text, String textInv, DoubleUnaryOperator fun, DoubleUnaryOperator funInv) {
        cp.add(new UnaryOperatorCalcButton(calc, text, textInv, fun, funInv, inv), pos);
    }

    /**
     * Adds function button to the calculator.
     */
    private void addBiFunctionButton(Object pos, String text, String textInv, DoubleBinaryOperator fun, DoubleBinaryOperator funInv) {
        cp.add(new BinaryOperatorCalcButton(calc, text, textInv, fun, funInv, inv), pos);
    }

    /**
     * Adds operators buttons to the calculator.
     */
    private void addOperators() {
        addOperator("5,6", "+", Double::sum);
        addOperator("4,6", "-", (v1, v2) -> v1 - v2);
        addOperator("3,6", "*", (v1, v2) -> v1 * v2);
        addOperator("2,6", "/", (v1, v2) -> v1 / v2);
    }

    /**
     * Adds operator button to the calculator.
     */
    private void addOperator(Object pos, String text, DoubleBinaryOperator operator) {
        cp.add(new BinaryOperatorCalcButton(calc, text, operator), pos);
    }

    /**
     * Adds other buttons to the calculator.
     */
    private void addOtherButtons() {
        cp.add(inv, "5,7");
        cp.add(new CalcButton(".", e -> calc.insertDecimalPoint()), "5, 5");
        cp.add(new CalcButton("+/-", e -> calc.swapSign()), "5,4");
        cp.add(new CalcButton("clr", (e) -> calc.clear()), "1,7");
        cp.add(new CalcButton("push", (e) -> stack.push(calc.getValue())), "3,7");
        cp.add(new CalcButton("pop", (e) -> {
                    try {
                        calc.setValue(stack.pop());
                    } catch (EmptyStackException ex) {
                        calcDisplay.setText("EMPTY STACK!");
                    }
                }),
                "4,7"
        );
        cp.add(new CalcButton("=", e -> {
                    calc.setValue(calc.getPendingBinaryOperation().applyAsDouble(calc.getActiveOperand(), calc.getValue()));
                    calc.clearActiveOperand();
                    calc.setPendingBinaryOperation(null);
                }),
                "1,6"
        );
        cp.add(new CalcButton("reset", (e) -> {
                    calc.clearAll();
                    stack.clear();
                }),
                "2,7"
        );
    }

    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
    }
}
