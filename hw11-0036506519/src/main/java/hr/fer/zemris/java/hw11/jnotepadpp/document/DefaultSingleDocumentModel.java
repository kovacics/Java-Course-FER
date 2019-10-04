package hr.fer.zemris.java.hw11.jnotepadpp.document;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Concrete single document model implementation.
 *
 * @author Stjepan Kovačić
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

    /**
     * File path of the model.
     */
    private Path filePath;

    /**
     * Text component of the model.
     */
    private JTextArea textArea;

    /**
     * List of all listeners.
     */
    private List<SingleDocumentListener> listeners = new ArrayList<>();

    /**
     * Modified flag.
     */
    private boolean modified;

    /**
     * Constructs single document model with specified path and text.
     *
     * @param filePath    document location
     * @param textContent document text
     */
    public DefaultSingleDocumentModel(Path filePath, String textContent) {
        this.filePath = filePath;
        textArea = new JTextArea(textContent);
        textArea.setBackground(new Color(60, 60, 70));
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 18));

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setModified(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setModified(true);
            }
        });
    }

    @Override
    public JTextArea getTextComponent() {
        return textArea;
    }

    @Override
    public Path getFilePath() {
        return filePath;
    }

    @Override
    public void setFilePath(Path path) {
        Objects.requireNonNull(path);
        filePath = path;
        listeners.forEach(l -> l.documentFilePathUpdated(this));
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
        for (var l : listeners) {
            l.documentModifyStatusUpdated(this);
        }
    }

    @Override
    public void addSingleDocumentListener(SingleDocumentListener l) {
        listeners.add(l);
    }

    @Override
    public void removeSingleDocumentListener(SingleDocumentListener l) {
        listeners.remove(l);
    }
}
