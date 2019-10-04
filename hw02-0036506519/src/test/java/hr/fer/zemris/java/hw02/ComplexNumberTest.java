package hr.fer.zemris.java.hw02;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static hr.fer.zemris.java.hw02.ComplexNumber.*;
import static org.junit.jupiter.api.Assertions.*;

public class ComplexNumberTest {

    public static ComplexNumber cn;

    @BeforeAll
    public static void makeTestingCN() {
        cn = new ComplexNumber(4, -3);
    }

    @Test
    public void ConstructorTest() {
        assertEquals(4, cn.getReal(), EPSILON);
        assertEquals(-3, cn.getImaginary(), EPSILON);
        assertEquals(5, cn.getMagnitude(), EPSILON);
        assertEquals(2 * Math.PI - 0.6435011088, cn.getAngle(), EPSILON);
    }

    @Test
    public void ConstructorTestNegativeAngle() {
        assertNotEquals(-0.6435011088, cn.getAngle(), EPSILON);
        assertEquals(2 * Math.PI - 0.6435011088, cn.getAngle(), EPSILON);
    }

    @Test
    public void getRealTest() {
        assertEquals(4, cn.getReal(), EPSILON);
    }

    @Test
    public void getImaginaryTest() {
        assertEquals(-3, cn.getImaginary(), EPSILON);
    }

    @Test
    public void getMagnitudeTest() {
        assertEquals(5, cn.getMagnitude(), EPSILON);
    }


    @Test
    public void getAngleTest() {
        assertEquals(2 * Math.PI - 0.6435011088, cn.getAngle(), EPSILON);
    }

    @Test
    public void fromImaginaryTest() {
        ComplexNumber number = fromImaginary(2.12);
        assertEquals(2.12, number.getImaginary(), EPSILON);
        assertEquals(0, number.getReal(), EPSILON);
    }

    @Test
    public void fromRealTest() {
        ComplexNumber number = fromReal(5.17);
        assertEquals(0, number.getImaginary(), EPSILON);
        assertEquals(5.17, number.getReal(), EPSILON);
    }

    @Test
    public void fromMagnitudeAndAngleTest() {
        ComplexNumber number = fromMagnitudeAndAngle(5, -0.6435011088);
        assertEquals(4, number.getReal(), EPSILON);
        assertEquals(-3, number.getImaginary(), EPSILON);
        assertEquals(2 * Math.PI - 0.6435011088, number.getAngle(), EPSILON);
        assertNotNull(fromMagnitudeAndAngle(5, -0.6435011088));
    }

    @Test
    public void equalsTest() {
        ComplexNumber num1 = new ComplexNumber(17.2, -1.56);
        ComplexNumber num2 = new ComplexNumber(17.2, -1.56);
        assertEquals(num1, num2);

        num1 = new ComplexNumber(1, 2);
        assertNotEquals(num1, num2);
    }


    @Test
    public void addForNullThrowsException() {
        assertThrows(NullPointerException.class, () -> cn.add(null));
    }

    @Test
    public void addWorksGoodTest() {
        ComplexNumber test = cn.add(new ComplexNumber(0, 0));
        assertEquals(test, cn);

        test = cn.add(new ComplexNumber(2, 2));
        assertEquals(test, new ComplexNumber(6, -1));
    }

    @Test
    public void subForNullThrowsException() {
        assertThrows(NullPointerException.class, () -> cn.sub(null));
    }

    @Test
    public void subWorksGoodTest() {
        ComplexNumber test = cn.sub(new ComplexNumber(0, 0));
        assertEquals(test, cn);

        test = cn.sub(new ComplexNumber(2, 2));
        assertEquals(test, new ComplexNumber(2, -5));
    }

    @Test
    public void mulForNullThrowsException() {
        assertThrows(NullPointerException.class, () -> cn.mul(null));
    }

    @Test
    public void mulWorksGoodTest() {
        ComplexNumber test = cn.mul(new ComplexNumber(0, 0));
        assertEquals(test, new ComplexNumber(0, 0));

        test = cn.mul(new ComplexNumber(2, 2));
        assertEquals(test, new ComplexNumber(14, 2));
    }

    @Test
    public void divForNullThrowsException() {
        assertThrows(NullPointerException.class, () -> cn.div(null));
    }

    @Test
    public void divWorksGoodTest() {
        ComplexNumber test = cn.div(new ComplexNumber(0, 0));
        assertEquals(test.getReal(), new ComplexNumber(Double.NaN, Double.NaN).getReal());
        assertEquals(test.getImaginary(), new ComplexNumber(Double.NaN, Double.NaN).getReal());

        test = cn.div(new ComplexNumber(2, 2));
        assertEquals(test, new ComplexNumber(1d / 4, -7d / 4));
    }

    @Test
    public void conjugateTest() {
        assertEquals(new ComplexNumber(4, 3), cn.conjugate());
    }

    @Test
    public void powerForNegativeNThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> cn.power(-1));
    }

    @Test
    public void powerWorksGoodTest() {
        assertEquals(new ComplexNumber(1, 0), cn.power(0));
        assertEquals(new ComplexNumber(-3116, 237), cn.power(5));
    }

    @Test
    public void rootForBadNThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> cn.root(0));
    }

    @Test
    public void rootWorksGoodTest() {
        assertEquals(new ComplexNumber(-2.12132034355964, 0.70710678118654), cn.root(2)[0]);
        assertEquals(new ComplexNumber(2.12132034355964, -0.70710678118654), cn.root(2)[1]);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(new ComplexNumber(4, -3).hashCode(), cn.hashCode());
    }

    @Test
    public void parseForNullThrowsException() {
        assertThrows(NullPointerException.class, () -> parse(null));
    }

    @Test
    public void parseForEmptyStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> parse("    "));
    }

    @Test
    public void parseThrowsForIllegalString() {
        assertThrows(IllegalArgumentException.class, () -> parse("-+2i"));
        assertThrows(IllegalArgumentException.class, () -> parse("abc+2i"));
        assertThrows(IllegalArgumentException.class, () -> parse("+"));
        assertThrows(IllegalArgumentException.class, () -> parse("-"));
        assertThrows(IllegalArgumentException.class, () -> parse(""));
        assertThrows(IllegalArgumentException.class, () -> parse("17+"));
        assertThrows(IllegalArgumentException.class, () -> parse("3i+2i"));
        assertThrows(IllegalArgumentException.class, () -> parse("0+-2i"));
        assertThrows(IllegalArgumentException.class, () -> parse("4-i5"));
        assertThrows(IllegalArgumentException.class, () -> parse("+  +i"));
        assertThrows(IllegalArgumentException.class, () -> parse("2+3"));
        assertThrows(IllegalArgumentException.class, () -> parse("-i123"));
        assertThrows(IllegalArgumentException.class, () -> parse("i123"));
        assertThrows(IllegalArgumentException.class, () -> parse("+i123"));
    }

    @Test
    public void parseWorksGoodTest() {
        assertEquals(new ComplexNumber(2, 3), parse("2+3i"));
        assertEquals(new ComplexNumber(0, 5), parse("+5i"));
        assertEquals(new ComplexNumber(22.2, 0), parse("22.2"));
        assertEquals(new ComplexNumber(-17, -6), parse("-17-6i"));
        assertEquals(new ComplexNumber(14, 3.2), parse("14.+3.2i"));
        assertEquals(new ComplexNumber(0, 1), parse("i"));
        assertEquals(new ComplexNumber(0, -1), parse("-i"));
        assertEquals(new ComplexNumber(5, -1), parse("5-i"));
        assertEquals(new ComplexNumber(5, 1), parse("5+i"));
        assertEquals(new ComplexNumber(17, 55.45), parse("   17+   55.45i"));
        assertEquals(new ComplexNumber(2, 3), parse(" 2 + 3 i"));
        assertEquals(new ComplexNumber(-9.9, 0), parse("-9.9"));
    }
}
