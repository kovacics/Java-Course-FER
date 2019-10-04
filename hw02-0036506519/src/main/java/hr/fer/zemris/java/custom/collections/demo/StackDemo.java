package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Program demonstrates work of ObjectStack class.
 * It evaluates expression in postfix representation using stack.
 * Expression should be given as single command-line argument.
 *
 * @author Stjepan Kovačić.
 */
public class StackDemo {

    /**
     * Starting point of the program.
     *
     * @param args command-line arguments.
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("I need exactly one expression!");
            return;
        }

        ObjectStack stack = new ObjectStack();
        String[] elements = args[0].trim().split("\\s+");

        for (String s : elements) {
            try {
                stack.push(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                int arg2;
                int arg1;

                try {
                    arg2 = (int) stack.pop();
                    arg1 = (int) stack.pop();
                } catch (EmptyStackException ex) {
                    System.out.println("This is not valid expression:" + args[0]);
                    return;
                }

                switch (s) {
                case "+":
                    stack.push(arg1 + arg2);
                    break;
                case "-":
                    stack.push(arg1 - arg2);
                    break;
                case "*":
                    stack.push(arg1 * arg2);
                    break;
                case "/":
                    if (arg2 == 0) {
                        System.out.println("Can't divide by zero, exiting...");
                        return;
                    }
                    stack.push(arg1 / arg2);
                    break;
                case "%":
                    if (arg2 == 0) {
                        System.out.println("Can't divide by zero, exiting...");
                        return;
                    }
                    stack.push(arg1 % arg2);
                    break;
                default:
                    System.out.println("This is not valid expression:" + args[0]);
                    return;
                }
            }
        }

        if (stack.size() != 1) {
            System.out.println("Something went wrong...exiting");
        } else {
            System.out.println("Expresion evaluates to " + stack.pop());
        }
    }
}
