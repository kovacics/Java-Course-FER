package hr.fer.zemris.java.hw17.util;

import hr.fer.zemris.java.hw17.vector.DocumentVector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Util class.
 *
 * @author Stjepan Kovačić
 */
public class Util {

    /**
     * Method returns set od all stop words.
     *
     * @return set of stop words
     * @throws IOException if io error happens
     */
    public static Set<String> getStopWords(String filepath) throws IOException {
        String[] words = Files.readString(Paths.get(filepath)).split("\r\n");
        return new HashSet<>(Arrays.asList(words));
    }

    /**
     * Method returns list of all words in the given string.
     *
     * @param s input string
     * @return list of all words
     */
    public static List<String> getWordsFromString(String s) {
        boolean toAdd = false;
        List<String> words = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (Character.isAlphabetic(c)) {
                sb.append(Character.toLowerCase(c));
                toAdd = true;
            } else if (toAdd) {
                words.add(sb.toString());
                sb.setLength(0);
                toAdd = false;
            }
        }
        if (sb.length() > 0) {
            words.add(sb.toString());
        }
        return words;
    }

    /**
     * Method gets tf vector from the given list of words in the document.
     *
     * @param file       file path
     * @param vocabulary vocabulary words
     * @return tf vector of the document
     */
    public static DocumentVector getTfVector(List<String> words, List<String> vocabulary, Path file) {
        DocumentVector vector = new DocumentVector(file);

        for (String word : vocabulary) {
            vector.add(Collections.frequency(words, word));
        }
        return vector;
    }

    /**
     * Method gets tf-idf vector from the tf and idf vector.
     *
     * @param tfVector  tf vector
     * @param idfVector idf vector
     * @return tf-idf vector
     */
    public static DocumentVector getTfIdfVector(DocumentVector tfVector, List<Double> idfVector) {
        if (tfVector.size() != idfVector.size()) {
            throw new IllegalArgumentException("Given vectors don't have the same size.");
        }

        DocumentVector tfIdfVector = new DocumentVector(tfVector.getDocument());

        for (int i = 0; i < tfVector.size(); i++) {
            tfIdfVector.add(tfVector.get(i) * idfVector.get(i));
        }
        return tfIdfVector;
    }
}
