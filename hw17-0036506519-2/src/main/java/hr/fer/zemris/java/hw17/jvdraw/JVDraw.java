package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.canvas.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawingmodel.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.jcolorarea.CurrentColorLabel;
import hr.fer.zemris.java.hw17.jvdraw.jcolorarea.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.list.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.tools.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.util.JVDUtil;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectOutputStringBuilder;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class represents simple painter program with functions of drawing
 * lines and circles, save, open and export option.
 *
 * @author Stjepan Kovačić
 */
public class JVDraw extends JFrame {

    /**
     * Foreground color area.
     */
    private JColorArea fg;

    /**
     * Background color area.
     */
    private JColorArea bg;

    /**
     * List of all objects.
     */
    private JList<GeometricalObject> jl;

    /**
     * Currently active tools.
     */
    private Tool currentTool;

    /**
     * Drawing model which contains drawing objects.
     */
    private DrawingModel model;

    /**
     * Drawing canvas on which all objects get drawn.
     */
    private JDrawingCanvas canvas;

    /**
     * Constructs JVDraw frame.
     */
    public JVDraw() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(50, 50);

        initGUI();
        setSize(new Dimension(800, 600));
    }

    /**
     * Method which initializes GUI.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        model = new DrawingModelImpl();

        canvas = new JDrawingCanvas(() -> currentTool, model);
        canvas.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        initializeJList();

        cp.add(createToolbar(), BorderLayout.NORTH);
        cp.add(new CurrentColorLabel(fg, bg), BorderLayout.SOUTH);
        cp.add(jl, BorderLayout.EAST);
        cp.add(canvas, BorderLayout.CENTER);

        currentTool = new LineTool(canvas, model, fg);

        createMenus();
    }

    /**
     * Method creates and returns toolbar of the frame.
     *
     * @return created toolbar
     */
    private JToolBar createToolbar() {
        JToolBar tb = new JToolBar();

        fg = new JColorArea(Color.blue);
        tb.add(fg);

        bg = new JColorArea(Color.red);
        tb.add(bg);

        tb.addSeparator();

        addButtons(tb);

        return tb;
    }

    /**
     * Method adds buttons on the toolbar.
     *
     * @param tb toolbar on which to add buttons
     */
    private void addButtons(JToolBar tb) {
        JToggleButton lineButton = new JToggleButton(new AbstractAction("Line") {
            private static final long serialVersionUID = -8587886714957651404L;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentTool = new LineTool(canvas, model, fg);
            }
        });

        JToggleButton circleButton = new JToggleButton(new AbstractAction("Circle") {
            private static final long serialVersionUID = -8587886714957651404L;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentTool = new CircleTool(canvas, model, fg);
            }
        });

        JToggleButton filledCircleButton = new JToggleButton(new AbstractAction("Filled circle") {
            private static final long serialVersionUID = -8587886714957651404L;

            @Override
            public void actionPerformed(ActionEvent e) {
                currentTool = new FilledCircleTool(canvas, model, bg, fg);
            }
        });

        ButtonGroup toolsButtons = new ButtonGroup();

        toolsButtons.add(lineButton);
        tb.add(lineButton);
        toolsButtons.add(circleButton);
        tb.add(circleButton);
        toolsButtons.add(filledCircleButton);
        tb.add(filledCircleButton);
    }

    /**
     * Method that creates menus of the program.
     */
    private void createMenus() {
        JMenuBar mb = new JMenuBar();

        JMenu file = new JMenu("File");
        mb.add(file);

        file.add(new JMenuItem(openAction));
        file.add(new JMenuItem(saveAction));
        file.add(new JMenuItem(saveAsAction));
        file.add(new JMenuItem(exportAction));
        file.addSeparator();
        file.add(new JMenuItem(exitAction));

        setJMenuBar(mb);
    }

    /**
     * Method initializes program's jlist.
     */
    private void initializeJList() {
        jl = new JList<>(new DrawingObjectListModel(model));
        jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int number = e.getClickCount();
                if (number == 2) {
                    GeometricalObject clicked = jl.getSelectedValue();
                    GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
                    if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Confirm?",
                            JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        try {
                            editor.checkEditing();
                            editor.acceptEditing();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            // alternative:if checkEditing got us here, show message and reopen editors
                        }
                    }
                }
            }
        });

        jl.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!jl.hasFocus()) return;

                var object = jl.getSelectedValue();
                int index = jl.getSelectedIndex();

                int key = e.getKeyCode();

                int delKeyCode = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0, true).getKeyCode();
                int minusKeyCode = 109; //getKeyStroke(VK_MINUS).keyCode didn't work good
                int plusKeyCode = 107;  //getKeyStroke(VK_PLUS).keyCode didn't work good

                DrawingObjectListModel model = (DrawingObjectListModel) jl.getModel();

                if (key == delKeyCode) {
                    model.removeElement(object);
                    jl.setSelectedIndex(model.getSize() - 1);
                } else if (key == minusKeyCode) {
                    model.changeOrder(object, 1);
                    jl.setSelectedIndex(index + 1);
                } else if (key == plusKeyCode) {
                    model.changeOrder(object, -1);
                    jl.setSelectedIndex(index - 1);
                }
            }
        });

        jl.setFixedCellWidth(200);
    }


    /**
     * Main method of the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JVDraw npp = new JVDraw();
            npp.setVisible(true);
        });
    }

    //***********************************************
    //                  ACTIONS
    //***********************************************

    /**
     * Action which saves drawing in .jvd format with new filename.
     */
    private final Action saveAsAction = new AbstractAction("Save as") {
        private static final long serialVersionUID = 450973100226836129L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = getJvdSaveFileChooser();
            if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) return;

            String fileName = jfc.getSelectedFile().toString();
            if (!fileName.endsWith(".jvd"))
                fileName += ".jvd";

            Path filePath = Paths.get(fileName);
            var stringBuilder = new GeometricalObjectOutputStringBuilder();

            for (int i = 0; i < model.getSize(); i++) {
                model.getObject(i).accept(stringBuilder);
            }

            try {
                Files.writeString(filePath, stringBuilder.getFinalString());
                JOptionPane.showMessageDialog(
                        JVDraw.this,
                        "File is saved.",
                        "Information.",
                        JOptionPane.INFORMATION_MESSAGE
                );
                model.setFilePath(filePath);
                model.clearModifiedFlag();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    };

    /**
     * Action which saves drawing in .jvd format.
     */
    private final Action saveAction = new AbstractAction("Save") {
        private static final long serialVersionUID = -1249099713332167418L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.getFilePath() == null) {
                saveAsAction.actionPerformed(e);
            } else {
                Path filePath = model.getFilePath();
                var stringBuilder = new GeometricalObjectOutputStringBuilder();

                for (int i = 0; i < model.getSize(); i++) {
                    model.getObject(i).accept(stringBuilder);
                }
                try {
                    Files.writeString(filePath, stringBuilder.getFinalString());
                    JOptionPane.showMessageDialog(
                            JVDraw.this,
                            "File is saved.",
                            "Information.",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    model.clearModifiedFlag();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    /**
     * Action exits painter.
     */
    private final Action exitAction = new AbstractAction("Exit") {
        private static final long serialVersionUID = 3673062109235680991L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.isModified()) {
                int reply = JOptionPane.showOptionDialog(
                        JVDraw.this,
                        "Save before exiting?",
                        "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null
                );
                if (reply == JOptionPane.YES_OPTION) {
                    saveAction.actionPerformed(e);
                } else if (reply == JOptionPane.CANCEL_OPTION || reply == JOptionPane.CLOSED_OPTION) {
                    return;
                }
            }
            JVDraw.this.dispose();
        }
    };

    /**
     * Action which exports drawing as image.
     */
    private final Action exportAction = new AbstractAction("Export") {
        private static final long serialVersionUID = -6972038047776180404L;

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser jfc = getImageSaveFileChooser();
            if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) return;

            String fileName = jfc.getSelectedFile().toString();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png") && !fileName.endsWith(".gif"))
                fileName += ".png";

            Path filePath = Paths.get(fileName);

            Rectangle box = getBoundingBox(model);
            BufferedImage image = getImageOfTheModel(model, box);
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

            try {
                ImageIO.write(image, extension, filePath.toFile());
                JOptionPane.showMessageDialog(
                        JVDraw.this,
                        "File is exported.",
                        "Information.",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    };

    /**
     * Action which opens some .jvd file and generates drawing from it.
     */
    private final Action openAction = new AbstractAction("Open") {
        private static final long serialVersionUID = 7241221451514290144L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (model.isModified()) {
                int reply = JOptionPane.showOptionDialog(
                        JVDraw.this,
                        "Save before opening new file?",
                        "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null
                );
                if (reply == JOptionPane.YES_OPTION) {
                    saveAction.actionPerformed(e);
                } else if (reply == JOptionPane.CANCEL_OPTION || reply == JOptionPane.CLOSED_OPTION) {
                    return;
                }
            }

            JFileChooser jfc = getJvdOpenFileChooser();
            if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) return;

            Path filePath = jfc.getSelectedFile().toPath();

            if (!Files.isReadable(filePath)) {
                JOptionPane.showMessageDialog(
                        JVDraw.this,
                        "Cannot load this file: " + filePath,
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            try {
                model.clear();
                List<String> lines = Files.readAllLines(filePath);
                for (String line : lines) {
                    model.add(JVDUtil.parseGeometricalObjectLine(line));
                }
                model.setFilePath(filePath);
                model.clearModifiedFlag();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    };

    //***********************************************
    //            ACTION HELPING METHODS
    //***********************************************


    /**
     * Method returns bounding box of the model.
     *
     * @param model drawing model
     * @return bounding box
     */
    private Rectangle getBoundingBox(DrawingModel model) {
        var calc = new GeometricalObjectBBCalculator();
        for (int i = 0; i < model.getSize(); i++) {
            model.getObject(i).accept(calc);
        }

        return calc.getBoundingBox();
    }

    /**
     * Method returns image from the model bounding box.
     *
     * @param model drawing model
     * @param box   bounding box
     * @return image
     */
    private BufferedImage getImageOfTheModel(DrawingModel model, Rectangle box) {
        BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = image.createGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, box.width, box.height);
        g.translate(-box.x, -box.y);

        var painter = new GeometricalObjectPainter(g);
        for (int i = 0; i < model.getSize(); i++) {
            model.getObject(i).accept(painter);
        }
        g.dispose();
        return image;
    }

    /**
     * Method returns save file chooser for images.
     *
     * @return save file chooser
     */
    private JFileChooser getImageSaveFileChooser() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save file");
        jfc.setFileFilter(new FileNameExtensionFilter("Image files (*.jpg, *.png, *.gif)", "jpg", "png", "gif"));
        return jfc;
    }

    /**
     * Method returns save file chooser for *.jvd files.
     *
     * @return save file chooser
     */
    private JFileChooser getJvdOpenFileChooser() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Open file");

        FileNameExtensionFilter jvdFilter = new FileNameExtensionFilter("jvd files (*.jvd)", "jvd");
        jfc.setFileFilter(jvdFilter);
        return jfc;
    }

    /**
     * Method returns open file chooser for the *.jvd files.
     *
     * @return open file chooser
     */
    private JFileChooser getJvdSaveFileChooser() {
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Save file");
        jfc.setFileFilter(new FileNameExtensionFilter("jvd file (*.jvd)", "jvd"));
        return jfc;
    }
}
