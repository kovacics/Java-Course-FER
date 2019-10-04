package hr.fer.zemris.java.hw05.db;

/**
 * <p>Class offers three concrete {@code IFieldValueGetter} strategies.</p>
 * Concrete strategies that class offers are:
 * <li>FIRST_NAME - for obtaining {@code firstName} of the {@code StudentRecord}</li>
 * <li>LAST_NAME - for obtaining {@code lastName} of the {@code StudentRecord}</li>
 * <li>JMBAG - for obtaining {@code jmbag} of the {@code StudentRecord}</li>
 */
public class FieldValueGetters {

    /**
     * Concrete {@code IFieldValueGetter} strategy
     * for obtaining {@code firstName} of the {@code StudentRecord}
     */
    public static final IFieldValueGetter FIRST_NAME;

    /**
     * Concrete {@code IFieldValueGetter} strategy
     * for obtaining {@code lastName} of the {@code StudentRecord}
     */
    public static final IFieldValueGetter LAST_NAME;

    /**
     * Concrete {@code IFieldValueGetter} strategy
     * for obtaining {@code jmbag} of the {@code StudentRecord}
     */
    public static final IFieldValueGetter JMBAG;

    static {
        FIRST_NAME = StudentRecord::getFirstName;
        LAST_NAME = StudentRecord::getLastName;
        JMBAG = StudentRecord::getJmbag;
    }
}
