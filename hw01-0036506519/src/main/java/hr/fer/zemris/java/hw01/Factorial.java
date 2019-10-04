package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This program waits for user's number input and then calculate factorial of
 * the number and prints it to the console.
 * <p>
 * If user gives illegal argument, then program asks for new input. It keeps
 * asking for new number until user give 'kraj' command.
 * <p>
 * Range for numbers is [3,20]
 */
public class Factorial {

    /**
     * Minimum value for valid input.
     */
    public static final int INPUT_MIN_VALUE = 3;

    /**
     * Maximum value for valid input.
     */
    public static final int INPUT_MAX_VALUE = 20;


    /**
     * Calculates and returns factorial of the number.
     *
     * @param number Number for which to calculate factorial.
     *               Because of the return type(long), for method to work correctly,
     *               number must be less or equal 20
     * @return factorial of the number
     * @throws IllegalArgumentException if the number is negative
     */
    public static long calculateFactorial(int number) {
        if (number < 0 || number > 20) {
            throw new IllegalArgumentException("'" + number + "' nije u dozvoljenom rasponu.");
        } else if (number < 2) {
            return 1;
        } else
            return number * calculateFactorial(number - 1);
    }

    /**
     * Method that executes first
     *
     * @param args - command-line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Unesite broj > ");
            String s = sc.next();

            if (s.equals("kraj")) {
                System.out.println("Doviđenja.");
                sc.close();
                return;
            } else {
                try {
                    int number = Integer.parseInt(s);
                    if (number >= INPUT_MIN_VALUE && number <= INPUT_MAX_VALUE) {
                        System.out.printf("%d! = %d%n", number, calculateFactorial(number));
                    } else {
                        System.out.println("Zadani broj je izvan raspona.");
                    }
                } catch (NumberFormatException e) {
                    System.out.printf("'%s' nije cijeli broj.%n", s);
                } catch (IllegalArgumentException e) {
                    System.out.printf("%s%n", e.getMessage());
                }
            }
        }
    }
}
