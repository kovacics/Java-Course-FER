package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * Demo program with 8 tasks of filtering {@code StudentRecord}s.
 *
 * @author Stjepan Kovačić
 */
public class StudentDemo {

    /**
     * Entry point of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get("./studenti.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<StudentRecord> records = convert(lines);

        ipisiBrojZadatka(1);
        System.out.println(vratiBodovaViseOd25(records));

        ipisiBrojZadatka(2);
        System.out.println(vratiBrojOdlikasa(records));

        ipisiBrojZadatka(3);
        vratiListuOdlikasa(records).forEach(System.out::println);

        ipisiBrojZadatka(4);
        vratiSortiranuListuOdlikasa(records).forEach(System.out::println);

        ipisiBrojZadatka(5);
        vratiPopisNepolozenih(records).forEach(System.out::println);

        ipisiBrojZadatka(6);
        razvrstajStudentePoOcjenama(records).forEach((key, value) -> System.out.println(key + " -> " + value));

        ipisiBrojZadatka(7);
        vratiBrojStudenataPoOcjenama(records).forEach((key, value) -> System.out.println(key + " -> " + value));

        ipisiBrojZadatka(8);
        razvrstajProlazPad(records).forEach((key, value) -> System.out.println(key + " -> " + value));
    }


    /**
     * Helping method for printing header of the task output.
     *
     * @param broj task number
     */
    private static void ipisiBrojZadatka(int broj) {
        System.out.println("Zadatak " + broj);
        System.out.println("====================");
    }

    /**
     * Creates map in which students are grouped in the ones that passed the class,
     * and the ones that failed the class.
     *
     * @param records list of all students
     * @return map of grouped students by their passage of the class
     */
    private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
        return records.stream()
                .collect(Collectors.partitioningBy(
                        student -> student.getOcjena() >= 2
                ));
    }

    /**
     * Creates map in which grades are mapped to number of students with that grade.
     *
     * @param records list of all students
     * @return map of grades and number of students with that grade
     */
    private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
        return records.stream()
                .collect(toMap(
                        StudentRecord::getOcjena,
                        student -> 1,
                        (stariBroj, noviBroj) -> stariBroj + 1
                ));
    }

    /**
     * Creates map in which students are grouped by their grade.
     *
     * @param records list of all students
     * @return map of grades mapped to all students with that grade
     */
    private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(StudentRecord::getOcjena));
    }

    /**
     * Creates list of all students that failed the class.
     *
     * @param records list of all students
     * @return list of all students that failed the class
     */
    private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
        return records.stream()
                .filter(student -> student.getOcjena() == 1)
                .map(StudentRecord::getJmbag)
                .sorted()
                .collect(Collectors.toList());
    }

    /**
     * Creates sorted list of all students with grade 5.
     *
     * @param records list of all students
     * @return sorted list of all students with grade 5
     */
    private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
        return records.stream()
                .filter(student -> student.getOcjena() == 5)
                .sorted(Comparator.comparingDouble(s -> s.getBodoviLAB() + s.getBodoviZI() + s.getBodoviMI()))
                .collect(Collectors.toList());
    }

    /**
     * Creates list of all students with grade 5.
     *
     * @param records list of all students
     * @return list of all students with grade 5
     */
    private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
        return records.stream()
                .filter(student -> student.getOcjena() == 5)
                .collect(Collectors.toList());
    }

    /**
     * Returns number of students with grade 5.
     *
     * @param records list of all students
     * @return number of students with grade 5
     */
    private static long vratiBrojOdlikasa(List<StudentRecord> records) {
        return records.stream()
                .mapToInt(StudentRecord::getOcjena)
                .filter(ocjena -> ocjena == 5)
                .count();
    }

    /**
     * Returns number of students with more than 25 points.
     *
     * @param records list of all students
     * @return number of students with more than 25 points
     */
    private static long vratiBodovaViseOd25(List<StudentRecord> records) {
        return records.stream()
                .mapToDouble((record) -> record.getBodoviMI()
                        + record.getBodoviZI() + record.getBodoviLAB())
                .filter((bodovi) -> bodovi > 25)
                .count();
    }

    /**
     * Private method that converts list of string lines from the file
     * to list of student records parsed from every line.
     *
     * @param lines strings of the student records
     * @return list of student records
     */
    private static List<StudentRecord> convert(List<String> lines) {
        List<StudentRecord> recordsList = new ArrayList<>();
        lines.forEach(line -> {
            String[] parts = line.split("\t");

            try {
                if (parts.length != 7) {
                    throw new RuntimeException("Illegal number of arguments for student record");
                }
                recordsList.add(new StudentRecord(parts[0], parts[1], parts[2],
                        Double.parseDouble(parts[3]), Double.parseDouble(parts[4]),
                        Double.parseDouble(parts[5]), Integer.parseInt(parts[6])));
            } catch (RuntimeException e) {
                System.out.println("Corrupted student record line");
            }
        });

        return recordsList;
    }
}
