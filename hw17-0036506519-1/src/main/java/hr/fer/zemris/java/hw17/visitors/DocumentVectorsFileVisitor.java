package hr.fer.zemris.java.hw17.visitors;

import hr.fer.zemris.java.hw17.util.Util;
import hr.fer.zemris.java.hw17.vector.DocumentVector;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * Class represents visitors which gets tf vectors from the files.
 *
 * @author Stjepan Kovačić
 */
public class DocumentVectorsFileVisitor extends SimpleFileVisitor<Path> {

    /**
     * List of document vectors.
     */
    private List<DocumentVector> tfVectors = new ArrayList<>();

    /**
     * Idf vector.
     */
    private List<Double> idfVector;

    /**
     * Map of occurrences for idf vector.
     */
    private Map<String, Integer> idfMap = new HashMap<>();

    /**
     * Vocabulary of the engine.
     */
    private List<String> vocabulary;

    /**
     * Constructs visitors.
     *
     * @param vocabulary vocabulary
     */
    public DocumentVectorsFileVisitor(List<String> vocabulary) {
        this.vocabulary = vocabulary;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        var words = Util.getWordsFromString(Files.readString(file));
        DocumentVector vector = new DocumentVector(file);

        for (String word : vocabulary) {
            int frequency = Collections.frequency(words, word);
            vector.add(frequency);
            if (frequency > 0) {
                idfMap.merge(word, 1, (oldValue, newValue) -> oldValue + 1);
            }
        }
        tfVectors.add(vector);
        return FileVisitResult.CONTINUE;
    }

    /**
     * Getter of the vectors list.
     *
     * @return list of all tf vectors
     */
    public List<DocumentVector> getTfVectors() {
        return tfVectors;
    }

    /**
     * Getter of the idf vector.
     *
     * @return idf vector
     */
    public List<Double> getIdfVector() {
        if (idfVector == null) {
            idfVector = new ArrayList<>();
            for (String word : vocabulary) {
                double x = (double) tfVectors.size() / idfMap.get(word);
                idfVector.add(Math.log(x));
            }
        }
        return idfVector;
    }
}
