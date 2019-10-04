package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * Program demonstrates work of the ComplexNumber class.
 *
 * @author Stjepan Kovačić
 */
public class ComplexDemo {

    /**
     * Program's starting point
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        ComplexNumber c1 = new ComplexNumber(2, 3);
        ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
        ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
                .div(c2).power(3).root(2)[1];
        System.out.println(c3);

//		String[] complexNumbers = {"-i2",
//                "-3a2.+789.2i",
//                "+  +2i",
//                "-32i   ",
//                "   1.145",
//                "4.44    -1i",
//                "-i",
//                "1+i",
//                "-8.99 +    9.11i",
//                "3",
//                "3-",
//                "-3-",
//                "-3.  +2.i",
//                "32.1-i12i",
//                "i",
//                "2+3"
//        };
//       
//        for(String number : complexNumbers) {
//            try {
//                ComplexNumber complexParse = ComplexNumber.parse(number);
//                System.out.println(number + "     SUCCEDED");
//                } catch(Exception ex) {
//                System.out.println(number + "     FAILED");
//            }
//        }

    }
}


