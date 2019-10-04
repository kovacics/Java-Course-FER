package hr.fer.zemris.java.hw01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FactorialTest {

    @Test
    public void normalFactorialTest() {

        long factorial = Factorial.calculateFactorial(5);
        assertEquals(120, factorial);

        factorial = Factorial.calculateFactorial(0);
        assertEquals(1, factorial);

        factorial = Factorial.calculateFactorial(20);
        assertEquals(2432902008176640000L, factorial);
    }

    @Test
    public void negativeArgumentTest() {

        assertThrows(IllegalArgumentException.class, () -> Factorial.calculateFactorial(-1));
        assertThrows(IllegalArgumentException.class, () -> Factorial.calculateFactorial(21));
    }
}
