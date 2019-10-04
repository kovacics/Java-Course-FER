package hr.fer.zemris.java.hw05.db;


/**
 * Strategy for obtaining requested field value from the given {@code StudentRecord}.
 */
@FunctionalInterface
public interface IFieldValueGetter {
    /**
     * Method that gets some field value of the {@index StudentRecord}.
     *
     * @param record record for which to get field value
     * @return field value
     */
    String get(StudentRecord record);
}
