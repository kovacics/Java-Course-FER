package hr.fer.zemris.java.hw11.jnotepadpp.document;

import javax.swing.*;
import java.nio.file.Path;

/**
 * Interface represents single document model.
 *
 * @author Stjepan Kovačić
 */
public interface SingleDocumentModel {

    /**
     * Method returns text component of the model.
     *
     * @return text component
     */
    JTextArea getTextComponent();

    /**
     * Method returns file path of the model.
     *
     * @return filepath
     */
    Path getFilePath();

    /**
     * Method sets file path of the model.
     *
     * @param path path to be set
     */
    void setFilePath(Path path);

    /**
     * Method returns modified status.
     *
     * @return true if model modified, false otherwise
     */
    boolean isModified();

    /**
     * Method sets modified flag of the model.
     *
     * @param modified value to be set
     */
    void setModified(boolean modified);

    /**
     * Method adds single document listener to the collection.
     *
     * @param l listener to add
     */
    void addSingleDocumentListener(SingleDocumentListener l);

    /**
     * Method removes single document listener from the collection.
     *
     * @param l listener to be removed
     */
    void removeSingleDocumentListener(SingleDocumentListener l);
}
