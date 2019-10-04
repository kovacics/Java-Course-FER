package hr.fer.zemris.java.hw11.jnotepadpp.document;

import java.nio.file.Path;

/**
 * Interface represents multiple document model.
 *
 * @author Stjepan Kovačić
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

    /**
     * Creates new document.
     *
     * @return created document model
     */
    SingleDocumentModel createNewDocument();

    /**
     * Returns current document model.
     *
     * @return current document
     */
    SingleDocumentModel getCurrentDocument();

    /**
     * Loads document from the specified path.
     *
     * @param path path of the document
     * @return loaded document
     */
    SingleDocumentModel loadDocument(Path path);

    /**
     * Saves document to the specified path.
     *
     * @param model   document to save
     * @param newPath save location
     */
    void saveDocument(SingleDocumentModel model, Path newPath);

    /**
     * Closes given document.
     *
     * @param model document to close
     */
    void closeDocument(SingleDocumentModel model);

    /**
     * Adds multiple document listener to the collection.
     *
     * @param l listener to add
     */
    void addMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Removes specified multiple document listener.
     *
     * @param l listener to remove from collection
     */
    void removeMultipleDocumentListener(MultipleDocumentListener l);

    /**
     * Returns number of documents in the model.
     *
     * @return documents number
     */
    int getNumberOfDocuments();

    /**
     * Gets document at the specified index.
     *
     * @param index index of the document
     * @return document at the given index
     */
    SingleDocumentModel getDocument(int index);
}
