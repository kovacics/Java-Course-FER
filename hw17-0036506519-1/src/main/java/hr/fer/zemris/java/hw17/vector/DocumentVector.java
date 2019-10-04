package hr.fer.zemris.java.hw17.vector;


import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stjepan Kovačić
 */
public class DocumentVector {

    /**
     * Vector values;
     */
    private List<Double> values = new ArrayList<>();

    /**
     * Path of the document.
     */
    private Path document;

    /**
     * Constructs document vector.
     *
     * @param document path of the document
     */
    public DocumentVector(Path document) {
        this.document = document;
    }

    /**
     * Method returns similarity between two vectors. Vectors are the most similar when similarity equals 1.00.
     *
     * @param second second vector
     * @return similarity of the vectors
     */
    public double getSimilarityWith(DocumentVector second) {
        double sp = getScalarProduct(this, second);
        return sp / (this.getVectorNorm() * second.getVectorNorm());
    }

    /**
     * Method does scalar(dot) product of the vectors.
     *
     * @param first  first vector
     * @param second second vector
     * @return dot product result
     */
    private static double getScalarProduct(DocumentVector first, DocumentVector second) {
        if (first.size() != second.size()) {
            throw new IllegalArgumentException("Given vectors don't have the same size.");
        }
        double result = 0.0;

        for (int i = 0; i < first.size(); i++) {
            result += first.get(i) * second.get(i);
        }
        return result;
    }

    /**
     * Method returns norm of the given vector.
     *
     * @return calculated vector norm
     */
    private double getVectorNorm() {
        double x = 0.0;
        for (double value : values) {
            x += value * value;
        }
        return Math.sqrt(x);
    }

    /**
     * Returns size of the vector.
     *
     * @return vector size
     */
    public int size() {
        return values.size();
    }

    /**
     * Returns element at the given index.
     *
     * @param index index
     * @return vector element
     */
    public double get(int index) {
        return values.get(index);
    }

    /**
     * Adds element to the vector.
     *
     * @param value element value
     */
    public void add(double value) {
        values.add(value);
    }

    /**
     * Getter of the document.
     *
     * @return vector document
     */
    public Path getDocument() {
        return document;
    }
}
