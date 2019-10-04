package hr.fer.zemris.java.hw17.trazilica;

import hr.fer.zemris.java.hw17.util.Util;
import hr.fer.zemris.java.hw17.vector.DocumentVector;
import hr.fer.zemris.java.hw17.visitors.DocumentVectorsFileVisitor;
import hr.fer.zemris.java.hw17.visitors.VocabularyFileVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * Simple search engine program.
 *
 * @author Stjepan Kovačić
 */
public class Konzola {

    /**
     * Vocabulary of the all words.
     */
    private static List<String> vocabulary;

    /**
     * Tf vectors.
     */
    private static List<DocumentVector> tfVectors;

    /**
     * Idf vectors.
     */
    private static List<Double> idfVector;

    /**
     * List of results.
     */
    private static List<Result> results = new ArrayList<>();

    /**
     * Main method of the program.
     *
     * @param args command line args
     * @throws IOException if io exception happens
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Illegal number of arguments. Path expected.");
            System.exit(1);
        }
        Path documentsFolder = Paths.get(args[0]);

        vocabulary = getVocabularyFromDocs(documentsFolder);
        System.out.println("doc done");
        initializeTfAndIdfVectors(documentsFolder);

        System.out.println("Veličina riječnika je " + vocabulary.size() + " riječi.");

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("Enter command > ");
                String line = sc.nextLine();

                if (line.equals("exit")) break;

                String command = line.split("\\s")[0];

                switch (command) {
                case "query":
                    processQueryCommand(line);
                    break;
                case "type":
                    processTypeCommand(line);
                    break;
                case "results":
                    processResultCommand();
                    break;
                default:
                    System.out.println("Nepoznata naredba");
                }
            }
        }
    }

    /**
     * Method returns vocabulary from the document folder.
     *
     * @param documentsFolder doc folder
     * @return vocabulary
     * @throws IOException if io error happens
     */
    private static List<String> getVocabularyFromDocs(Path documentsFolder) throws IOException {
        VocabularyFileVisitor visitor = new VocabularyFileVisitor();
        Files.walkFileTree(documentsFolder, visitor);

        return new ArrayList<>(visitor.getVocabulary());
    }

    /**
     * Method initializes tf and idf vectors.
     *
     * @param documentsFolder doc folder
     * @throws IOException if io error happens
     */
    private static void initializeTfAndIdfVectors(Path documentsFolder) throws IOException {
        DocumentVectorsFileVisitor visitor = new DocumentVectorsFileVisitor(vocabulary);
        Files.walkFileTree(documentsFolder, visitor);

        tfVectors = visitor.getTfVectors();
        idfVector = visitor.getIdfVector();
    }

    /**
     * Method process query command.
     */
    private static void processQueryCommand(String s) {
        s = s.replace("query", "").trim();
        var queryWords = Util.getWordsFromString(s);
        queryWords.removeIf((word) -> !vocabulary.contains(word));

        DocumentVector queriedVector = Util.getTfIdfVector(Util.getTfVector(queryWords, vocabulary, null), idfVector);

        for (DocumentVector tfVector : tfVectors) {
            var tfIdfVector = Util.getTfIdfVector(tfVector, idfVector);
            double similarity = tfIdfVector.getSimilarityWith(queriedVector);
            results.add(new Result(tfVector, similarity));
        }
        results.sort(Comparator.comparingDouble(Result::getSimilarity).reversed());
        System.out.println("Najboljih 10 rezultata:");
        printResults();
    }

    /**
     * Method prints query results to the console.
     */
    private static void printResults() {
        for (int i = 0; i < Math.min(10, results.size()); i++) {
            double similarity = results.get(i).similarity;
            if (similarity == 0) break;
            System.out.printf("[ %d ] ( %s ) %s%n", i, String.format("%.4f", similarity),
                    results.get(i).getVector().getDocument().toAbsolutePath());
        }
    }

    /**
     * Method process type command.
     */
    private static void processTypeCommand(String s) throws IOException {
        s = s.replace("type", "").trim();
        int index = Integer.parseInt(s);

        if (results == null) {
            throw new RuntimeException("Cannot call this action before query action.");
        }
        if (index >= results.size()) {
            throw new RuntimeException("Cannot get result at that index.");
        }

        var result = results.get(index);
        System.out.println("--------------------------------------------");
        System.out.println("Dokument " + result.getVector().getDocument().toAbsolutePath());
        System.out.println("--------------------------------------------");
        System.out.println(Files.readString(result.getVector().getDocument()));
        System.out.println("--------------------------------------------");
    }

    /**
     * Method process results command.
     */
    private static void processResultCommand() {
        printResults();
    }

    /**
     * Helping class that represents result of a query.
     */
    private static class Result {
        private DocumentVector vector;
        private double similarity;

        public Result(DocumentVector vector, double similarity) {
            this.vector = vector;
            this.similarity = similarity;
        }

        public DocumentVector getVector() {
            return vector;
        }

        public double getSimilarity() {
            return similarity;
        }
    }
}
