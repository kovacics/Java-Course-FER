package hr.fer.zemris.java.hw17.jvdraw.canvas;

import hr.fer.zemris.java.hw17.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawingmodel.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectVisitor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

/**
 * JComponent which represents drawing canvas.
 * It is used for drawing objects on the component.
 *
 * @author Stjepan Kovačić
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

    /**
     * Tool supplier.
     */
    private Supplier<Tool> toolSupplier;

    /**
     * Drawing model which canvas uses.
     */
    private DrawingModel model;

    /**
     * Constructs canvas with specified tool supplier.
     *
     * @param toolSupplier supplier of the tools
     * @param model        drawing model for canvas
     */
    public JDrawingCanvas(Supplier<Tool> toolSupplier, DrawingModel model) {
        this.toolSupplier = toolSupplier;
        this.model = model;

        MouseAdapter listeners = createMouseListeners();
        addMouseListener(listeners);
        addMouseMotionListener(listeners);

        model.addDrawingModelListener(this);
    }

    /**
     * Method creates mouse adapter.
     *
     * @return mouse adapter
     */
    private MouseAdapter createMouseListeners() {

        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toolSupplier.get().mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                toolSupplier.get().mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                toolSupplier.get().mouseReleased(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                toolSupplier.get().mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                toolSupplier.get().mouseMoved(e);
            }
        };
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(200, 200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.white);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        GeometricalObjectVisitor painter = new GeometricalObjectPainter(g2d);

        for (int i = 0; i < model.getSize(); i++) {
            model.getObject(i).accept(painter);
        }

        toolSupplier.get().paint(g2d); // return if needed some addition drawing
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        repaint();
    }
}
