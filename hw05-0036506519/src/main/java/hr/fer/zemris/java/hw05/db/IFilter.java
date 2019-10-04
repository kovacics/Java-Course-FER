package hr.fer.zemris.java.hw05.db;

/**
 * Interface represent some filter for {@code StudentRecord}.
 */
@FunctionalInterface
public interface IFilter {

    /**
     * Method that filters one {@code StudentRecord} which means that it tests
     * record for some condition(s) and returns true if the record is acceptable,
     * and force if not.
     *
     * @param record record which should be tested
     * @return {@code true} if result is acceptable, otherwise {@code false}
     */
    boolean accepts(StudentRecord record);
}
