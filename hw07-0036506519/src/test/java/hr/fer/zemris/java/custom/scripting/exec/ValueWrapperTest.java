package hr.fer.zemris.java.custom.scripting.exec;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Stjepan Kovačić
 */
class ValueWrapperTest {

    @Test
    void add() {
        ValueWrapper v1 = new ValueWrapper(null);
        ValueWrapper v2 = new ValueWrapper(null);
        v1.add(v2.getValue()); // v1 now stores Integer(0); v2 still stores null.
        assertEquals(Integer.valueOf(0), v1.getValue());
        assertEquals(null, v2.getValue());

        ValueWrapper v3 = new ValueWrapper("1.2E1");
        ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
        v3.add(v4.getValue()); // v3 now stores Double(13); v4 still stores Integer(1).
        assertEquals(Double.valueOf(13), v3.getValue());
        assertEquals(Integer.valueOf(1), v4.getValue());

        ValueWrapper v5 = new ValueWrapper("12");
        ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
        v5.add(v6.getValue()); // v5 now stores Integer(13); v6 still stores Integer(1).
        assertEquals(Integer.valueOf(13), v5.getValue());
        assertEquals(Integer.valueOf(1), v6.getValue());
    }

    @Test
    void addThrows() {
        ValueWrapper v7 = new ValueWrapper("Ankica");
        ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
        assertThrows(RuntimeException.class, () -> v7.add(v8.getValue())); // throws RuntimeException
    }

    @Test
    void substract() {
        ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
        ValueWrapper v2 = new ValueWrapper(Double.valueOf(2.5));
        v1.subtract(v2.getValue());
        assertEquals(Double.valueOf(2.5), v1.getValue());
    }

    @Test
    void multiply() {
        ValueWrapper v1 = new ValueWrapper(Integer.valueOf(5));
        ValueWrapper v2 = new ValueWrapper("10");
        v1.multiply(v2.getValue());
        assertEquals(Integer.valueOf(50), v1.getValue());
    }

    @Test
    void divide() {
        ValueWrapper v1 = new ValueWrapper("100");
        ValueWrapper v2 = new ValueWrapper(Double.valueOf(25));
        v1.divide(v2.getValue());
        assertEquals(Double.valueOf(4), v1.getValue());
    }

    @Test
    void numCompare() {
        ValueWrapper v1 = new ValueWrapper(Double.valueOf(5));
        ValueWrapper v2 = new ValueWrapper(Double.valueOf(5));
        assertEquals(0, v1.numCompare(v2.getValue()));

        ValueWrapper v3 = new ValueWrapper(Double.valueOf(2));
        ValueWrapper v4 = new ValueWrapper(Double.valueOf(5));
        assertEquals(-1, v3.numCompare(v4.getValue()));

        ValueWrapper v5 = new ValueWrapper(Double.valueOf(50));
        ValueWrapper v6 = new ValueWrapper(Double.valueOf(5));
        assertEquals(1, v5.numCompare(v6.getValue()));
    }
}