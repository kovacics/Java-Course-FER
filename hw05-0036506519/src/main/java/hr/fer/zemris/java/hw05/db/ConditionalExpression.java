package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class represents one conditional expression of a query.
 */
public class ConditionalExpression {

    /**
     * Getter of the field which is contained in the expression.
     */
    private IFieldValueGetter fieldGetter;

    /**
     * String literal used as one argument in some type of comparing operation in the expression.
     */
    private String stringLiteral;

    /**
     * Comparing operator for two values in the expression.
     */
    private IComparisonOperator comparisonOperator;

    /**
     * Constructor specifying {@code fieldGetter}, {@code stringLiteral} and {@code comparisonOperator}
     * of the expression.
     *
     * @param fieldGetter        field getter of the expression
     * @param stringLiteral      string used in expression for comparing
     * @param comparisonOperator operator used for comparing operation in this expression
     */
    public ConditionalExpression(IFieldValueGetter fieldGetter, String stringLiteral, IComparisonOperator comparisonOperator) {
        this.fieldGetter = fieldGetter;
        this.stringLiteral = stringLiteral;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * Getter of the expression's {@code fieldGetter}.
     *
     * @return {@code fieldGetter} of this {@code ConditionalExpression}
     */
    public IFieldValueGetter getFieldGetter() {
        return fieldGetter;
    }

    /**
     * Getter of the expression's {@code stringLiteral}.
     *
     * @return {@code stringLiteral} of this {@code ConditionalExpression}
     */
    public String getStringLiteral() {
        return stringLiteral;
    }

    /**
     * Getter of the expression's {@code IComparisonOperator}.
     *
     * @return {@code IComparisonOperator} of this {@code ConditionalExpression}
     */
    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConditionalExpression that = (ConditionalExpression) o;
        return Objects.equals(fieldGetter, that.fieldGetter) &&
                Objects.equals(stringLiteral, that.stringLiteral) &&
                Objects.equals(comparisonOperator, that.comparisonOperator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldGetter, stringLiteral, comparisonOperator);
    }
}
