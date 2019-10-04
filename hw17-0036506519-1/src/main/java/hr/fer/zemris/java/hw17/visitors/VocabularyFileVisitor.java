package hr.fer.zemris.java.hw17.visitors;

import hr.fer.zemris.java.hw17.util.Util;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class represents visitors whose function is collecting vocabulary of the search engine.
 *
 * @author Stjepan Kovačić
 */
public class VocabularyFileVisitor extends SimpleFileVisitor<Path> {

    /**
     * Vocabulary of the search engine for the accesed files.
     */
    private Set<String> vocabulary = new HashSet<>();

    /**
     * Set of all skip words.
     */
    private Set<String> skipWords;

    /**
     * Constructs vocabulary visitors.
     *
     * @throws IOException if io error happens
     */
    public VocabularyFileVisitor() throws IOException {
        skipWords = Util.getStopWords("hrvatski_stoprijeci.txt");
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String text = Files.readString(file);
        addToVocabulary(text);

        return FileVisitResult.CONTINUE;
    }

    /**
     * Method adds words from text to the vocabulary.
     *
     * @param text text with words
     */
    private void addToVocabulary(String text) {
        List<String> words = Util.getWordsFromString(text);

        for (String word : words) {
            if (!skipWords.contains(word)) {
                vocabulary.add(word);
            }
        }
    }

    /**
     * Getter of the collected vocabulary.
     *
     * @return vocabulary
     */
    public Set<String> getVocabulary() {
        return vocabulary;
    }
}
