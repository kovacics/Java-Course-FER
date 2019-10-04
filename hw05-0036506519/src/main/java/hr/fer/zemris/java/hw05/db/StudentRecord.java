package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class represents record of the student.
 */
public class StudentRecord {

    /**
     * Jmbag of the student.
     */
    private String jmbag;

    /**
     * First name of the student.
     */
    private String firstName;

    /**
     * Last name of the student.
     */
    private String lastName;

    /**
     * Final grade of the student.
     */
    private int finalGrade;

    /**
     * Constructor specifying {@code firstName}, {@code lastName}, {@code jmbag} and {@code finalGrade}.
     *
     * @param firstName  first name of the student
     * @param lastName   last name of the student
     * @param jmbag      jmbag of the student
     * @param finalGrade final grade of the student
     */
    public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
        this.jmbag = jmbag;
        this.firstName = firstName;
        this.lastName = lastName;
        this.finalGrade = finalGrade;
    }

    /**
     * Getter for the {@code jmbag} field.
     *
     * @return jmbag of the student
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * Getter for the {@code firstName} field.
     *
     * @return first name of the student
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for the {@code lastName} field.
     *
     * @return last name of the student
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for the {@code finalGrade} field.
     *
     * @return final grade of the student
     */
    public int getFinalGrade() {
        return finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return Objects.equals(jmbag, that.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }
}
