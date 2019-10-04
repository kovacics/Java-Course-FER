package hr.fer.zemris.java.hw05.db;

/**
 * Class offers seven concrete {@code IComparisonOperator} operators which
 * can be then used for executing some comparing operation on two strings.
 */
public class ComparisonOperators {

    /**
     * Comparing operator what checks if the first given {@code String} value is less
     * than the second given {@code String} value.
     */
    public static final IComparisonOperator LESS;

    /**
     * Comparing operator what checks if the first given {@code String} value is less
     * than or equal the second given {@code String} value.
     */
    public static final IComparisonOperator LESS_OR_EQUALS;

    /**
     * Comparing operator what checks if the first given {@code String} value is greater
     * than the second given {@code String} value.
     */
    public static final IComparisonOperator GREATER;

    /**
     * Comparing operator what checks if the first given {@code String} value is greater
     * than or equal the second given {@code String} value.
     */
    public static final IComparisonOperator GREATER_OR_EQUALS;

    /**
     * Comparing operator what checks if the first given {@code String} value equals
     * the second given {@code String} value.
     */
    public static final IComparisonOperator EQUALS;

    /**
     * Comparing operator what checks if the first given {@code String} value does not
     * equal the second given {@code String} value.
     */
    public static final IComparisonOperator NOT_EQUALS;

    /**
     * Comparing operator what checks if the first given {@code String} value matches
     * the pattern given in the second given {@code String} value.
     */
    public static final IComparisonOperator LIKE;

    static {
        LESS = (v1, v2) -> v1.compareTo(v2) < 0;
        LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) < 1;
        GREATER = (v1, v2) -> v1.compareTo(v2) > 0;
        GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) > -1;
        EQUALS = (v1, v2) -> v1.compareTo(v2) == 0;
        NOT_EQUALS = (v1, v2) -> v1.compareTo(v2) != 0;
        LIKE = (v1, v2) -> {
            if (v2.matches(".*" + "\\*" + ".*" + "\\*" + ".*")) { // more then one "*"
                throw new IllegalArgumentException("Only one wildcard allowed.");
            }
            v2 = v2.replace("*", ".*");
            return v1.matches(v2);
        };
    }
}
