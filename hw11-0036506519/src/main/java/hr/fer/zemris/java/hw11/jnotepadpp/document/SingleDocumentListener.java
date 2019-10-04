package hr.fer.zemris.java.hw11.jnotepadpp.document;

/**
 * Interface represents single document model listener.
 *
 * @author Stjepan Kovačić
 */
public interface SingleDocumentListener {

    /**
     * Method which is executed when document gets modified.
     *
     * @param model document whose modified status changed
     */
    void documentModifyStatusUpdated(SingleDocumentModel model);

    /**
     * Method which is executed when document's file path is updated.
     *
     * @param model document whose filepath has been updated
     */
    void documentFilePathUpdated(SingleDocumentModel model);
}
