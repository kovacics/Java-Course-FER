package hr.fer.zemris.java.hw05.db;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ConditionalExpressionTest {

    private static ConditionalExpression expression;

    @BeforeAll
    public static void setUp() {
        expression = new ConditionalExpression(FieldValueGetters.LAST_NAME,
                "Tesla", ComparisonOperators.EQUALS);
    }


    @Test
    void constructorTest() {
        assertNotNull(expression);
    }

    @Test
    void getFieldGetterTest() {
        assertEquals(FieldValueGetters.LAST_NAME, expression.getFieldGetter());
        assertNotEquals(FieldValueGetters.FIRST_NAME, expression.getFieldGetter());
    }

    @Test
    void getStringLiteralTest() {
        assertEquals("Tesla", expression.getStringLiteral());
        assertNotEquals("Edison", expression.getStringLiteral());
    }

    @Test
    void getComparisonOperatorTest() {
        assertEquals(ComparisonOperators.EQUALS, expression.getComparisonOperator());
        assertNotEquals(ComparisonOperators.LIKE, expression.getComparisonOperator());
    }
}