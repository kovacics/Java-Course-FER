package hr.fer.zemris.java.hw11.jnotepadpp;

import hr.fer.zemris.java.hw11.jnotepadpp.document.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.document.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.document.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.document.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.*;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.function.Function;

/**
 * Class represents notepad with many basic functions.
 * Notepad provides:
 * <li>document functions (create, open, close, save etc.)</li>
 * <li>edit functions (cut, copy, paste)</li>
 * <li>tools like sorting, case change, duplicate lines removal etc.</li>
 *
 * @author Stjepan Kovačić
 */
public class JNotepadPP extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Multiple document model, all notepad documents.
     */
    private DefaultMultipleDocumentModel docs;

    /**
     * Line label.
     */
    private JLabel ln = new JLabel("Ln:1");

    /**
     * Column label.
     */
    private JLabel col = new JLabel("Col:1");

    /**
     * Selection label.
     */
    private JLabel sel = new JLabel("Sel:0");

    /**
     * Localization provider.
     */
    private FormLocalizationProvider provider =
            new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

    /**
     * Constructs notepad.
     */
    public JNotepadPP() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocation(50, 50);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeTheWindowProperly();
            }
        });
        initGUI();
        setSize(1200, 600);
    }

    /**
     * Initializes gui.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new DefaultMultipleDocumentModel();
        cp.add(tabbedPane, BorderLayout.CENTER);
        docs = (DefaultMultipleDocumentModel) tabbedPane;

        addNotepadNameListener();
        initAction();
        createMenus();

        createDocument.actionPerformed(null);

        cp.add(createStatusbar(), BorderLayout.SOUTH);
        cp.add(createToolbar(), BorderLayout.NORTH);
    }

    /**
     * Adds notepad name listener.
     */
    private void addNotepadNameListener() {
        docs.addMultipleDocumentListener(new MultipleDocumentListener() {
            @Override
            public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
                String pathString = "(unnamed)";
                if (currentModel != null) {
                    Path path = currentModel.getFilePath();
                    if (path != null) {
                        pathString = currentModel.getFilePath().toString();
                    }
                }
                setTitle(pathString + " - JNotepad++");
            }

            @Override
            public void documentAdded(SingleDocumentModel model) {
            }

            @Override
            public void documentRemoved(SingleDocumentModel model) {
            }
        });
    }

    /**
     * Creates (initializes) all actions.
     */
    private void initAction() {
        openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl O"));
        openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

        saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

        saveDocumentAs.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift S"));
        saveDocumentAs.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Y);

        createDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
        createDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

        closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl W"));
        closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);

        cutText.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
        cutText.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

        copyText.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl C"));
        copyText.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

        pasteText.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl V"));
        pasteText.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);

        exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
        exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

        statisticalInfo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
        statisticalInfo.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
    }

    /**
     * Creates all menus.
     */
    private void createMenus() {
        JMenuBar mb = new JMenuBar();

        //file
        JMenu file = new LJMenu("file", provider);
        mb.add(file);
        file.add(new JMenuItem(createDocument));
        file.add(new JMenuItem(openDocument));
        file.add(new JMenuItem(saveDocument));
        file.add(new JMenuItem(saveDocumentAs));
        file.add(new JMenuItem(closeDocument));
        file.addSeparator();
        file.add(new JMenuItem(statisticalInfo));
        file.addSeparator();
        file.add(new JMenuItem(exitAction));

        //edit
        JMenu edit = new LJMenu("edit", provider);
        mb.add(edit);
        edit.add(new JMenuItem(cutText));
        cutText.setEnabled(false);
        edit.add(new JMenuItem(copyText));
        copyText.setEnabled(false);
        edit.add(new JMenuItem(pasteText));

        //languages
        JMenu languages = new LJMenu("languages", provider);
        mb.add(languages);
        JMenuItem hr = new JMenuItem("hr");
        hr.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("hr"));
        languages.add(hr);
        JMenuItem en = new JMenuItem("en");
        en.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("en"));
        languages.add(en);
        JMenuItem de = new JMenuItem("de");
        de.addActionListener(e -> LocalizationProvider.getInstance().setLanguage("de"));
        languages.add(de);

        //tools
        JMenu tools = new LJMenu("tools", provider);
        mb.add(tools);

        //change case
        JMenu changeCase = new LJMenu("changeCase", provider);
        tools.add(changeCase);
        changeCase.add(new JMenuItem(toUpper));
        toUpper.setEnabled(false);
        changeCase.add(new JMenuItem(toLower));
        toLower.setEnabled(false);
        changeCase.add(new JMenuItem(invertCase));
        invertCase.setEnabled(false);

        //sort
        JMenu sort = new LJMenu("sort", provider);
        tools.add(sort);
        sort.add(new JMenuItem(ascending));
        ascending.setEnabled(false);
        sort.add(new JMenuItem(descending));
        descending.setEnabled(false);

        //unique
        tools.add(new JMenuItem(unique));
        unique.setEnabled(false);

        setJMenuBar(mb);
    }

    /**
     * Creates toolbar of the notepad which contains buttons for most used functions.
     *
     * @return toolbar
     */
    private JToolBar createToolbar() {
        JToolBar tb = new JToolBar();
        tb.setFloatable(true);

        JButton createDoc = new JButton(createDocument);
        createDoc.setIcon(IconUtility.getInstance().getIcon("icons/createDoc.png"));
        tb.add(createDoc);

        JButton openDoc = new JButton(openDocument);
        openDoc.setIcon(IconUtility.getInstance().getIcon("icons/openDoc.png"));
        tb.add(openDoc);

        JButton saveDoc = new JButton(saveDocument);
        saveDoc.setIcon(IconUtility.getInstance().getIcon("icons/saveDoc.png"));
        tb.add(saveDoc);

        JButton saveDocAs = new JButton(saveDocumentAs);
        saveDocAs.setIcon(IconUtility.getInstance().getIcon("icons/saveDocAs.png"));
        tb.add(saveDocAs);

        JButton closeDoc = new JButton(closeDocument);
        closeDoc.setIcon(IconUtility.getInstance().getIcon("icons/closeDoc.png"));
        tb.add(closeDoc);

        tb.addSeparator();

        JButton cut = new JButton(cutText);
        cut.setIcon(IconUtility.getInstance().getIcon("icons/cut.png"));
        tb.add(cut);

        JButton copy = new JButton(copyText);
        copy.setIcon(IconUtility.getInstance().getIcon("icons/copy.png"));
        tb.add(copy);

        JButton paste = new JButton(pasteText);
        paste.setIcon(IconUtility.getInstance().getIcon("icons/paste.png"));
        tb.add(paste);

        tb.addSeparator();

        Integer[] fontSizes = {8, 10, 12, 14, 16, 18, 20, 22, 24, 26};
        JComboBox<Integer> fontsBox = new JComboBox<>(fontSizes);
        fontsBox.setSelectedIndex(5);

        fontsBox.addActionListener(e -> {
            JTextArea area = docs.getCurrentDocument().getTextComponent();
            @SuppressWarnings("unchecked")
            JComboBox<Integer> source = (JComboBox<Integer>) e.getSource();
            var value = source.getSelectedItem();
            if (value != null) {
                area.setFont(area.getFont().deriveFont(((Integer) value).floatValue()));
            }
        });
        tb.add(fontsBox);

        JButton info = new JButton(statisticalInfo);
        info.setIcon(IconUtility.getInstance().getIcon("icons/info.png"));
        tb.add(info);

        tb.addSeparator();

        JButton exit = new JButton(exitAction);
        exit.setIcon(IconUtility.getInstance().getIcon("icons/exit.png"));
        tb.add(exit);

        return tb;
    }

    /**
     * Creates status bar of the notepad.
     *
     * @return status bar
     */
    private JPanel createStatusbar() {
        JPanel sb = new JPanel();
        sb.setLayout(new GridLayout(1, 3));

        JLabel lengthLabel = new JLabel("length:0");

        sb.add(lengthLabel);
        sb.add(getCaretInfoComponent(ln, col, sel));
        sb.add(getTimeComponent());

        addLengthListener(lengthLabel);

        sb.setBorder(new MatteBorder(3, 0, 0, 0, Color.GRAY));
        return sb;
    }

    /**
     * Returns component which contains date time text, and gets refreshed every second.
     *
     * @return time component
     */
    private Component getTimeComponent() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd  HH:mm:ss");
        JLabel time = new JLabel(formatter.format(LocalDateTime.now()), SwingConstants.RIGHT);

        Timer timer = new Timer(1000, e -> time.setText(formatter.format(LocalDateTime.now())));
        timer.start();

        return time;
    }

    /**
     * Returns panel with caret information labels.
     *
     * @param ln  lines label
     * @param col column label
     * @param sel selection label
     * @return caret info panel
     */
    private JPanel getCaretInfoComponent(JLabel ln, JLabel col, JLabel sel) {
        JPanel caretInfo = new JPanel();
        caretInfo.add(ln);
        caretInfo.add(col);
        caretInfo.add(sel);

        return caretInfo;
    }

    /**
     * Adds length listener.
     *
     * @param length length label
     */
    private void addLengthListener(JLabel length) {
        SingleDocumentListener lengthListener = new SingleDocumentListener() {
            SingleDocumentModel doc = docs.getCurrentDocument();

            @Override
            public void documentModifyStatusUpdated(SingleDocumentModel model) {
                if (model != doc) {
                    doc.removeSingleDocumentListener(this);
                    model.addSingleDocumentListener(this);
                    doc = model;
                }
                length.setText("length:" + model.getTextComponent().getText().length());
            }

            @Override
            public void documentFilePathUpdated(SingleDocumentModel model) {
            }
        };
        docs.getCurrentDocument().addSingleDocumentListener(lengthListener);
    }

    /**
     * Method closes windows properly.
     * It asks user for every not saved modified document, so
     * that no data gets lost.
     */
    private void closeTheWindowProperly() {
        for (int i = 0; i < docs.getNumberOfDocuments(); i++) {
            var doc = docs.getDocument(i);
            if (doc.isModified()) {
                docs.setSelectedIndex(i);
                int reply = JOptionPane.showOptionDialog(
                        JNotepadPP.this,
                        "Save \"" + docs.getToolTipTextAt(i) + "\"?",
                        "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null
                );
                if (reply == JOptionPane.YES_OPTION) {
                    saveDocument.actionPerformed(null);
                } else if (reply == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
        }
        dispose();
    }

    /**
     * Adds caret listeners to the document model.
     *
     * @param doc document model
     */
    private void addCaretListeners(SingleDocumentModel doc) {
        var caret = doc.getTextComponent().getCaret();

        caret.addChangeListener(e -> {
            boolean partSelected = caret.getDot() != caret.getMark();
            cutText.setEnabled(partSelected);
            copyText.setEnabled(partSelected);
            invertCase.setEnabled(partSelected);
            toUpper.setEnabled(partSelected);
            toLower.setEnabled(partSelected);
            ascending.setEnabled(partSelected);
            descending.setEnabled(partSelected);
            unique.setEnabled(partSelected);
        });

        caret.addChangeListener(e -> {
            JTextArea textArea = docs.getCurrentDocument().getTextComponent();

            int dot = caret.getDot();
            int mark = caret.getMark();

            try {
                int line = textArea.getLineOfOffset(dot);
                int column = dot - textArea.getLineStartOffset(line) + 1;

                ln.setText("Ln:" + (line + 1));
                col.setText("Col:" + column);
                sel.setText("Sel:" + Math.abs(dot - mark));
            } catch (BadLocationException ignore) {
            }
        });
    }

    //***********************************
    //          ACTIONS
    //***********************************

    /**
     * Create document action.
     */
    private final Action createDocument = new LocalizableAction("new", provider) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            var doc = docs.createNewDocument();
            doc.addSingleDocumentListener(new SingleDocumentListener() {
                @Override
                public void documentModifyStatusUpdated(SingleDocumentModel model) {
                }

                @Override
                public void documentFilePathUpdated(SingleDocumentModel model) {
                    Path path = model.getFilePath();
                    setTitle(path == null ? "(unnamed) - JNotepad++" : path.toString() + " - JNotepad++");
                }
            });
            addCaretListeners(doc);
        }
    };

    /**
     * Open document action.
     */
    private final Action openDocument = new LocalizableAction("open", provider) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Open file");
            if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            Path filePath = jfc.getSelectedFile().toPath();

            if (!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(
                        JNotepadPP.this,
                        "Cannot load this file: " + filePath,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            var doc = docs.loadDocument(filePath);
            if (doc != null) {
                addCaretListeners(doc);
            }
        }
    };

    /**
     * Close document action.
     */
    private final Action closeDocument = new LocalizableAction("close", provider) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            var doc = docs.getCurrentDocument();

            if (doc.isModified()) {
                String path = doc.getFilePath() == null ? "(unnamed)" : doc.getFilePath().toString();
                int reply = JOptionPane.showOptionDialog(
                        JNotepadPP.this,
                        "Save \"" + path + "\"?",
                        "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null
                );
                if (reply == JOptionPane.YES_OPTION) {
                    saveDocument.actionPerformed(null);
                } else if (reply == JOptionPane.CANCEL_OPTION) {
                    return;
                }
            }
            docs.closeDocument(docs.getCurrentDocument());
            if (docs.getCurrentDocument() == null) {
                createDocument.actionPerformed(null);
            }
        }
    };


    /**
     * Save as action.
     */
    private final Action saveDocumentAs = new LocalizableAction("saveAs", provider) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Save file");
            if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            var doc = docs.getCurrentDocument();
            Path filePath = jfc.getSelectedFile().toPath();
            File file = filePath.toFile();
            if (file.exists()) {
                int reply = JOptionPane.showConfirmDialog(
                        JNotepadPP.this,
                        "File already exist. Overwrite?",
                        "Overwrite",
                        JOptionPane.YES_NO_CANCEL_OPTION
                );
                if (reply == JOptionPane.YES_OPTION) {
                    docs.saveDocument(doc, filePath);
                }
            } else {
                docs.saveDocument(doc, filePath);
            }
        }
    };
    /**
     * Save document action.
     */
    private final Action saveDocument = new LocalizableAction("save", provider) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {

            var currentDoc = docs.getCurrentDocument();

            if (currentDoc.getFilePath() == null) {
                saveDocumentAs.actionPerformed(e);
            } else {
                docs.saveDocument(currentDoc, currentDoc.getFilePath());
            }
        }
    };

    /**
     * Copy selection action.
     */
    private final Action copyText = new LocalizableAction("copy", provider) {

        private static final long serialVersionUID = 1L;
        private Action copy = new DefaultEditorKit.CopyAction();

        @Override
        public void actionPerformed(ActionEvent e) {
            copy.actionPerformed(e);
        }
    };

    /**
     * Cut selection action.
     */
    private final Action cutText = new LocalizableAction("cut", provider) {

        private static final long serialVersionUID = 1L;
        private Action cut = new DefaultEditorKit.CutAction();

        @Override
        public void actionPerformed(ActionEvent e) {
            cut.actionPerformed(e);
        }
    };

    /**
     * Paste selection action.
     */
    private final Action pasteText = new LocalizableAction("paste", provider) {

        private static final long serialVersionUID = 1L;
        private Action paste = new DefaultEditorKit.PasteAction();

        @Override
        public void actionPerformed(ActionEvent e) {
            paste.actionPerformed(e);
        }
    };

    /**
     * Exit notepad action.
     */
    private final Action exitAction = new LocalizableAction("exit", provider) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            closeTheWindowProperly();
        }
    };

    /**
     * Statistical info action.
     */
    private final Action statisticalInfo = new LocalizableAction("statisticalInfo", provider) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            String text = docs.getCurrentDocument().getTextComponent().getText();
            int charsTotal = text.length();
            int nonBlank = text.replaceAll("\n|\r|\t|\\s", "").length();
            int lines = docs.getCurrentDocument().getTextComponent().getLineCount();

            JOptionPane.showMessageDialog(
                    JNotepadPP.this,
                    "Your document has " + charsTotal + " characters, " +
                            nonBlank + " non-blank characters and " + lines + " lines.",
                    "Statistical Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    };

    /**
     * To uppercase action.
     */
    private final Action toUpper =
            new CaseChangeAction("toUpper", provider, (c) -> Character.isLowerCase(c) ? Character.toUpperCase(c) : c);

    /**
     * To lowercase action.
     */
    private final Action toLower =
            new CaseChangeAction("toLower", provider, (c) -> Character.isUpperCase(c) ? Character.toLowerCase(c) : c);

    /**
     * Invert case action.
     */
    private final Action invertCase = new CaseChangeAction("invertCase", provider, (c) ->
            Character.isLowerCase(c) ? Character.toUpperCase(c) :
                    Character.isUpperCase(c) ? Character.toLowerCase(c) : c);

    /**
     * Ascending sort action.
     */
    private final Action ascending = new SortActions("ascending", provider,
            (line1, line2) -> {
                Locale hrLocale = new Locale("hr");
                Collator hrCollator = Collator.getInstance(hrLocale);
                return hrCollator.compare(line1, line2);
            });

    /**
     * Descending sort action.
     */
    private final Action descending = new SortActions("descending", provider,
            (line1, line2) -> {
                Locale hrLocale = new Locale("hr");
                Collator hrCollator = Collator.getInstance(hrLocale);
                return hrCollator.compare(line2, line1);
            });

    /**
     * Unique lines action.
     */
    private final Action unique = new LocalizableAction("unique", provider) {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            LinkedHashSet<String> lines = new LinkedHashSet<>();

            JTextArea textArea = docs.getCurrentDocument().getTextComponent();
            Caret caret = textArea.getCaret();

            int v1 = Math.min(caret.getMark(), caret.getDot());
            int v2 = Math.max(caret.getMark(), caret.getDot());

            try {
                int linePos1 = textArea.getLineOfOffset(v1);
                int linePos2 = textArea.getLineOfOffset(v2);

                if (linePos1 == linePos2) return;

                var allLines = getLines(textArea, linePos1, linePos2);
                lines.addAll(allLines);

                StringBuilder sb = new StringBuilder();
                for (String s : lines) {
                    sb.append(s).append("\n");
                }
                textArea.replaceRange(sb.toString(), textArea.getLineStartOffset(linePos1), textArea.getLineEndOffset(linePos2));
            } catch (BadLocationException ignore) {
            }
        }
    };


    /**
     * Inner class which extends localizable action and has special function
     * of working with cases.
     */
    private class CaseChangeAction extends LocalizableAction {

        private static final long serialVersionUID = 1L;
        private Function<Character, Character> func;

        public CaseChangeAction(String key, ILocalizationProvider lp, Function<Character, Character> func) {
            super(key, lp);
            this.func = func;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            var textArea = docs.getCurrentDocument().getTextComponent();
            var text = textArea.getText().toCharArray();
            var caret = textArea.getCaret();

            int start = Math.min(caret.getMark(), caret.getDot());
            int end = start == caret.getMark() ? caret.getDot() : caret.getMark();

            for (int i = start; i < end; i++) {
                text[i] = func.apply(text[i]);
            }
            textArea.setText(new String(text));
        }
    }

    /**
     * Inner class that represents special localizable action used
     * for sorting functions.
     */
    private class SortActions extends LocalizableAction {

        private static final long serialVersionUID = 1L;

        /**
         * Comparator of the action.
         */
        private Comparator<String> comparator;

        /**
         * Constructs actions with specified key, provider and comparator.
         *
         * @param key        key
         * @param lp         localization provider
         * @param comparator comparator
         */
        public SortActions(String key, ILocalizationProvider lp, Comparator<String> comparator) {
            super(key, lp);
            this.comparator = comparator;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextArea textArea = docs.getCurrentDocument().getTextComponent();
            Caret caret = textArea.getCaret();

            int v1 = Math.min(caret.getMark(), caret.getDot());
            int v2 = Math.max(caret.getMark(), caret.getDot());

            try {
                int linePos1 = textArea.getLineOfOffset(v1);
                int linePos2 = textArea.getLineOfOffset(v2);

                if (linePos1 == linePos2) return;

                java.util.List<String> lines = getLines(textArea, linePos1, linePos2);

                lines.sort(comparator);

                StringBuilder sb = new StringBuilder();
                for (String s : lines) {
                    sb.append(s).append("\n");
                }
                textArea.replaceRange(sb.toString(), textArea.getLineStartOffset(linePos1), textArea.getLineEndOffset(linePos2));
            } catch (BadLocationException ignore) {
            }
        }
    }

    /**
     * Method returns list of all text area lines between given positions.
     *
     * @param textArea text area component
     * @param linePos1 first line position
     * @param linePos2 last line position
     * @return list of all lines
     */
    private java.util.List<String> getLines(JTextArea textArea, int linePos1, int linePos2) {
        java.util.List<String> lines = new ArrayList<>();

        try {
            for (int i = linePos1; i <= linePos2; i++) {
                int start = textArea.getLineStartOffset(i);
                int end = textArea.getLineEndOffset(i);
                int len = end - start;
                String line = textArea.getText(start, len);
                if (line.endsWith("\n")) {
                    len--; //do not include new line char
                }
                lines.add(textArea.getText(start, len));
            }
        } catch (BadLocationException ignore) {

        }

        return lines;
    }

    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
    }
}

