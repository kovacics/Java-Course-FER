package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Concrete {@code IFilter} strategy used to filter student records.
 */
public class QueryFilter implements IFilter {

    /**
     * List of conditional expressions for which to filter records.
     */
    private List<ConditionalExpression> expressions;

    /**
     * Constructor specifying conditional expressions.
     *
     * @param expressions list of conditional expressions
     */
    public QueryFilter(List<ConditionalExpression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public boolean accepts(StudentRecord record) {
        for (ConditionalExpression ex : expressions) {
            if (!ex.getComparisonOperator().satisfied(ex.getFieldGetter().get(record), ex.getStringLiteral())) {
                return false;
            }
        }
        return true;
    }
}
