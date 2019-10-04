package hr.fer.zemris.java.hw11.jnotepadpp.document;

/**
 * Interface that represents listener for the multiple document model.
 *
 * @author Stjepan Kovačić
 */
public interface MultipleDocumentListener {

    /**
     * Method which is executed when current document changes.
     *
     * @param previousModel previous model
     * @param currentModel  current model
     */
    void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

    /**
     * Method which is executed when document is added to the model.
     *
     * @param model added document model
     */
    void documentAdded(SingleDocumentModel model);

    /**
     * Method which is executed when document is removed from the model.
     *
     * @param model removed document model
     */
    void documentRemoved(SingleDocumentModel model);
}
