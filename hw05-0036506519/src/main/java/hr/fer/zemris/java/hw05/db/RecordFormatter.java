package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static hr.fer.zemris.java.hw05.db.FieldValueGetters.*;

/**
 * Class that offers method for formatting given {@code List} of {@code StudentRecord}
 * into list of {@code String} so it can be later used in a different way such as
 * printing query result to the user.
 */
public class RecordFormatter {

    /**
     * Formats given list of student records into form more suitable for printing.
     *
     * @param records list of student records which should get formatted
     * @return formatted list
     */
    public static List<String> format(List<StudentRecord> records) {
        List<String> list = new ArrayList<>(records.size());
        IFieldValueGetter[] getters = {JMBAG, LAST_NAME, FIRST_NAME};
        int jmbagWidth = getWidth(JMBAG, records);
        int lastNameWidth = getWidth(LAST_NAME, records);
        int firstNameWidth = getWidth(FIRST_NAME, records);
        int[] widths = {jmbagWidth, lastNameWidth, firstNameWidth};

        StringBuilder sb = new StringBuilder();
        sb.append(getBorderLine(widths)).append('\n');

        for (StudentRecord rec : records) {
            for (int j = 0; j < getters.length; j++) {
                sb.append("| ").append(getters[j].get(rec));
                int spaces = widths[j] + 1 - getters[j].get(rec).length();
                sb.append(" ".repeat(spaces));
            }
            sb.append("| ").append(rec.getFinalGrade()).append(" |");
            list.add(sb.toString());
            sb.setLength(0);
        }
        list.add(getBorderLine(widths));
        String end = String.format("Records selected: %d", records.size());
        list.add(end);
        return list;
    }

    /**
     * Private method that forms border line of the database print.
     *
     * @param widths max widths for columns
     * @return border line string
     */
    private static String getBorderLine(int[] widths) {
        StringBuilder sb = new StringBuilder();
        for (int width : widths) {
            sb.append("+");
            sb.append("=".repeat(width + 2));
        }
        sb.append("+===+");
        return sb.toString();
    }

    /**
     * Private method for getting maximum length of some field value of all given records.
     *
     * @param fieldGetter getter of the field value
     * @param records     list of student records
     * @return maximum length of all records' field value
     */
    private static int getWidth(IFieldValueGetter fieldGetter, List<StudentRecord> records) {
        Objects.requireNonNull(records);
        var optWidth = records
                .stream()
                .mapToInt((record) -> fieldGetter.get(record).length())
                .max();

        return optWidth.orElse(0);
    }
}
