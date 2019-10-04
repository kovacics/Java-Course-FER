package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class represent a database of the student records.
 */
public class StudentDatabase {

    /**
     * List of all student records.
     */
    List<StudentRecord> studentRecordsList;

    /**
     * Map for fast student record retrieval.
     */
    HashMap<String, StudentRecord> studentRecordsMap;

    /**
     * Constructor which as argument gets array of the String representations of the
     * student records.
     * Creates {@code StudentRecord} objects and puts that records in the {@code Map} and {@code List}
     * if they pass these conditions:
     * <li> {@code StudentRecord} with the same {@code jmbag} is not yet present in the map </li>
     * <li> {@code finalGrade} of the record is in the [1,5] range </li>
     * Otherwise, constructor would throw appropriate exception.
     *
     * @param records String array of the student records
     * @throws IllegalArgumentException if there are too much or too less arguments for a student record
     * @throws IllegalArgumentException if finalGrade cannot be parse to Integer or if it is out of [1,5] range
     * @throws IllegalArgumentException if {@code StudentRecord} with the same {@code jmbag} is already in the map
     */
    public StudentDatabase(String[] records) {
        studentRecordsList = new ArrayList<>(records.length);
        studentRecordsMap = new HashMap<>(records.length);

        for (String s : records) {
            String[] data = s.split("\t");
            checkIfLegalRecord(data);

            StudentRecord record = new StudentRecord(data[0], data[1], data[2],
                    Integer.parseInt(data[3])); //already checked if parsable

            studentRecordsMap.put(data[0], record);
            studentRecordsList.add(record);
        }
    }

    /**
     * Helping method for checking if textual form of the student record is legal.
     * If not, method throws exception.
     *
     * @param data array of the String which are parts of the student record
     * @throws IllegalArgumentException if given string is not valid as student record
     */
    private void checkIfLegalRecord(String[] data) {
        if (data.length != 4) {
            throw new IllegalArgumentException("Too much or too less data for a student.");
        }
        try {
            int finalGrade = Integer.parseInt(data[3]);
            if (finalGrade < 1 || finalGrade > 5) {
                throw new IllegalArgumentException("Final grade must be in [1,5] range.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cannot parse " + data[3] + " into finalGrade");
        }

        if (studentRecordsMap.containsKey(data[0])) {
            throw new IllegalArgumentException("Cannot store duplicate jmbags.");
        }
    }

    /**
     * Returns {@code StudentRecord} with given jmbag value.
     * Complexity of the record getting is {@code O(1)}.
     *
     * @param jmbag jmbag for which to return {@code StudentRecord}
     * @return jmbag-specified {@code StudentRecord} if record exist,
     * {@code null} otherwise
     */
    public StudentRecord forJMBAG(String jmbag) {
        return studentRecordsMap.get(jmbag);
    }

    /**
     * Method returns filtered {@code StudentRecord} list.
     * It uses filter passed as method parameter.
     *
     * @param filter filter used in filtering of the records
     * @return filtered {@code List} of the elements of the type
     * {@code StudentRecord}
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> filteredList = new ArrayList<>();
        studentRecordsList.forEach(
                record -> {
                    if (filter.accepts(record))
                        filteredList.add(record);
                }
        );
        return filteredList;
    }
}
