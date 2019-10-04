package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program gets height and width of the rectangle from the user. Then it
 * calculates area and perimeter of the rectangle and prints it all to the
 * console.
 * <p>
 * Program can get user input in two ways: 1) command-line arguments 2) from
 * console .
 * <p>
 * If the arguments are invalid, user gets the error message and can try again
 * with another argument.
 */
public class Rectangle {

    /**
     * This method executes first.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        if (args.length == 0) {
            Scanner sc = new Scanner(System.in);

            double width = getPositiveNumber("Unesite širinu > ", sc);
            double height = getPositiveNumber("Unesite visinu > ", sc);
            printAreaAndPerimeter(width, height);
            sc.close();
        } else if (args.length == 2) {
            try {
                double width = Double.parseDouble(args[0]);
                double height = Double.parseDouble(args[1]);

                if (width <= 0 || height <= 0) {
                    System.out.println("Širina i visina moraju biti pozitivni brojevi.");
                } else {
                    printAreaAndPerimeter(width, height);
                }
            } catch (NumberFormatException e) {
                System.out.println("Ne mogu protumačiti kao broj.");
            }
        } else {
            System.out.println("Niste unijeli točan broj argumenata.");
        }
    }

    /**
     * Method is used to get positive number.
     * It will ask user for input until user give legal argument.
     *
     * @param message string that is shown to user again on every new try of getting
     *                legal argument
     * @param sc      scanner of the input stream
     * @return double positive value
     */
    private static double getPositiveNumber(String message, Scanner sc) {

        double arg;
        while (true) {
            System.out.printf("%s", message);
            String s = sc.next();
            try {
                arg = Double.parseDouble(s);
                if (arg < 0) {
                    System.out.println("Unijeli ste negativnu vrijednost.");
                    continue;
                } else if (arg == 0) {
                    System.out.println("Ne možete unijeti nulu.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.printf("'%s' se ne može protumačiti kao broj%n", s);
            }
        }
        return arg;
    }

    /**
     * Prints width, height, area and perimeter of the rectangle.
     *
     * @param width  width of a rectangle
     * @param height height of a rectangle
     */
    private static void printAreaAndPerimeter(double width, double height) {
        System.out.printf("Pravokutnik širine %s i visine %s ima površinu %s i opseg %s", Double.toString(width),
                Double.toString(height), Double.toString(findArea(width, height)),
                Double.toString(findPerimeter(width, height)));
    }

    /**
     * Calculates area based on width and height of the rectangle.
     *
     * @param width  width of a rectangle
     * @param height height of a rectangle
     * @return area of a rectangle
     * @throws IllegalArgumentException if arguments are negative
     */
    private static double findArea(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Širina i visina moraju biti pozitivni brojevi.");
        }

        return height * width;
    }

    /**
     * Calculates perimeter of the rectangle based on given width and height.
     *
     * @param width  width of a rectangle
     * @param height height of a rectangle
     * @return perimeter of a rectangle
     * @throws IllegalArgumentException if arguments are negative
     */
    private static double findPerimeter(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Širina i visina moraju biti pozitivni brojevi.");
        }

        return 2 * width + 2 * height;
    }
}
