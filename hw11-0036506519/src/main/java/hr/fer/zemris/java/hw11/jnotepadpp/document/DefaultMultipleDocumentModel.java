package hr.fer.zemris.java.hw11.jnotepadpp.document;

import hr.fer.zemris.java.hw11.jnotepadpp.IconUtility;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Concrete implementation of the {@code MultipleDocumentModel} interface.
 *
 * @author Stjepan Kovačić
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

    /**
     * Current document.
     */
    private SingleDocumentModel current;

    /**
     * List of all listeners.
     */
    private List<MultipleDocumentListener> listeners = new ArrayList<>();

    /**
     * List of all opened documents.
     */
    private List<SingleDocumentModel> documents = new ArrayList<>();

    /**
     * Icon represents modified document.
     */
    private final Icon MODIFIED_ICON = IconUtility.getInstance().getIcon("icons/redDisk.png");

    /**
     * Icon represents unmodified document.
     */
    private final Icon UNMODIFIED_ICON = IconUtility.getInstance().getIcon("icons/blueDisk.png");


    /**
     * Basic constructor.
     * Adds listener whose function is to update current document reference.
     */
    public DefaultMultipleDocumentModel() {
        addChangeListener(e -> {
            var old = current;
            int index = this.getSelectedIndex();
            current = index != -1 ? documents.get(index) : null;
            listeners.forEach(l -> l.currentDocumentChanged(old, current));
        });
    }

    @Override
    public SingleDocumentModel createNewDocument() {
        var newDoc = new DefaultSingleDocumentModel(null, "");
        newDoc.addSingleDocumentListener(TAB_ACTION_LISTENER);
        documents.add(newDoc);

        addTab("(unnamed)",
                UNMODIFIED_ICON,
                new JScrollPane(newDoc.getTextComponent()),
                "(unnamed)");

        setSelectedIndex(getNumberOfDocuments() - 1);
        listeners.forEach(l -> l.documentAdded(newDoc));

        return newDoc;
    }

    @Override
    public SingleDocumentModel getCurrentDocument() {
        return current;
    }

    /**
     * @throws NullPointerException if given path is <code>null</code>
     */
    @Override
    public SingleDocumentModel loadDocument(Path path) {
        Objects.requireNonNull(path);

        for (SingleDocumentModel doc : documents) {
            if (path.equals(doc.getFilePath())) {
                setSelectedIndex(documents.indexOf(doc));
                return doc;
            }
        }

        String text;
        try {
            text = Files.readString(path);
        } catch (IOException e) {
            showError("Error happened while loading document.");
            return null;
        }

        var newDoc = new DefaultSingleDocumentModel(path, text);
        newDoc.addSingleDocumentListener(TAB_ACTION_LISTENER);
        documents.add(newDoc);

        addTab(newDoc.getFilePath().getFileName().toString(),
                UNMODIFIED_ICON,
                new JScrollPane(newDoc.getTextComponent()),
                newDoc.getFilePath().toString());

        setSelectedIndex(getNumberOfDocuments() - 1);
        listeners.forEach(l -> l.documentAdded(newDoc));

        return newDoc;
    }

    @Override
    public void saveDocument(SingleDocumentModel model, Path newPath) {
        if (newPath == null) {
            newPath = model.getFilePath();
        }

        for (SingleDocumentModel doc : documents) {
            if (doc != model && newPath.equals(doc.getFilePath())) {
                showError("Document is currently opened.");
                return;
            }
        }

        try {
            Files.writeString(newPath, model.getTextComponent().getText());
        } catch (IOException e) {
            showError("Error while saving the file.");
            return;
        }
        model.setFilePath(newPath);
        model.setModified(false);
        JOptionPane.showMessageDialog(
                this,
                "File is saved.",
                "Information.",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void closeDocument(SingleDocumentModel model) {
        int index = documents.indexOf(model);
        documents.remove(index);
        listeners.forEach(l -> l.documentRemoved(model));
        remove(index);
    }

    @Override
    public void addMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeMultipleDocumentListener(MultipleDocumentListener l) {
        listeners.remove(l);
    }

    @Override
    public int getNumberOfDocuments() {
        return documents.size();
    }

    @Override
    public SingleDocumentModel getDocument(int index) {
        return documents.get(index);
    }

    @Override
    public Iterator<SingleDocumentModel> iterator() {
        return documents.iterator();
    }

    /**
     * Helping method shows error message frame.
     *
     * @param errorMessage error message to show
     */
    private void showError(String errorMessage) {
        JOptionPane.showMessageDialog(
                this,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Single document listener whose function is changing document modified icon,
     * and updating tab name and tool tip text.
     */
    private final SingleDocumentListener TAB_ACTION_LISTENER = new SingleDocumentListener() {

        @Override
        public void documentModifyStatusUpdated(SingleDocumentModel model) {
            setIconAt(documents.indexOf(model), model.isModified() ? MODIFIED_ICON : UNMODIFIED_ICON);
        }

        @Override
        public void documentFilePathUpdated(SingleDocumentModel model) {
            setTitleAt(documents.indexOf(model), model.getFilePath().getFileName().toString());
            setToolTipTextAt(documents.indexOf(model), model.getFilePath().toString());
        }
    };
}
