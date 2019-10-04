package hr.fer.zemris.java.hw05.db;

/**
 * Strategy with only one method used for doing some comparing operation
 * on two string values.
 */
@FunctionalInterface
public interface IComparisonOperator {

    /**
     * Does some comparing operation between given two strings.
     *
     * @param value1 first string
     * @param value2 second string
     * @return true if strings satisfy comparing operator, false if don't
     */
    boolean satisfied(String value1, String value2);
}
