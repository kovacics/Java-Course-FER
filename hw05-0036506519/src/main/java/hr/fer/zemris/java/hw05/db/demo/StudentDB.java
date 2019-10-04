package hr.fer.zemris.java.hw05.db.demo;

import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.RecordFormatter;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;
import hr.fer.zemris.java.hw05.db.parser.QueryParser;
import hr.fer.zemris.java.hw05.db.parser.QueryParserException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Program is simple database emulator.
 * Database consists of records of the type {@code StudentRecord}.
 * Records get loaded from a textual file, and then database gets created.
 * If in the textual file there are some duplicate {@code jmbags}, or if the finalGrade is not in
 * [1,5] range, program will terminate.
 */
public class StudentDB {


    /**
     * Main method of the program, being called first.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        StudentDatabase db;
        try {
            db = loadDB();
        } catch (IOException e) {
            System.out.println(e.getMessage() + "\nExiting...");
            return;
        }

        Scanner sc = new Scanner(System.in);
        String line;
        System.out.print("> ");

        while (!(line = sc.nextLine()).equals("exit")) {
            if (!line.startsWith("query")) {
                System.out.println("Illegal studentDatabase command, try again");
                System.out.print("> ");
                continue;
            }
            try {
                var parser = new QueryParser(line.split("query")[1]);
                List<StudentRecord> filtered = new ArrayList<>();
                if (parser.isDirectQuery()) {
                    StudentRecord record = db.forJMBAG(parser.getQueriedJMBAG());
                    if (record != null) {
                        filtered.add(db.forJMBAG(parser.getQueriedJMBAG()));
                    }
                } else {
                    filtered = db.filter(new QueryFilter(parser.getQuery()));
                }
                List<String> formatted = RecordFormatter.format(filtered);
                formatted.forEach(System.out::println);
            } catch (QueryParserException | IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.print("> ");
        }
        sc.close();
        System.out.println("Goodbye!");
    }

    /**
     * Helping method for loading data from textual file.
     *
     * @return database filled with data from the textual file
     * @throws IOException if io error happens
     */
    private static StudentDatabase loadDB() throws IOException {
        List<String> lines = Files.readAllLines(
                Paths.get("./database.txt"),
                StandardCharsets.UTF_8
        );
        String[] arr = new String[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            arr[i] = lines.get(i);
        }
        return new StudentDatabase(arr);
    }
}
