package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.canvas.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.jcolorarea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Class represents tools used when current state demands line drawing.
 *
 * @author Stjepan Kovačić
 */
public class LineTool implements Tool {

    /**
     * Drawing canvas.
     */
    private JDrawingCanvas canvas;

    /**
     * Drawing model.
     */
    private DrawingModel model;

    /**
     * Color provider.
     */
    private IColorProvider colorProvider;

    /**
     * Constructed line.
     */
    private Line line = null;

    /**
     * Counts clicks.
     */
    private int clicked;

    /**
     * Constructs line tools with specified canvas, model and color provider.
     *
     * @param canvas        drawing canvas
     * @param model         drawing model
     * @param colorProvider color provider
     */
    public LineTool(JDrawingCanvas canvas, DrawingModel model, IColorProvider colorProvider) {
        this.canvas = canvas;
        this.model = model;
        this.colorProvider = colorProvider;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        clicked++;
        if (clicked == 1) {
            line = new Line(e.getPoint(), e.getPoint(), colorProvider.getCurrentColor());
        } else if (clicked == 2) {
            model.add(line);
            line = null;
            clicked = 0;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (clicked == 1) {
            line.setX2(e.getX());
            line.setY2(e.getY());
            canvas.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void paint(Graphics2D g2d) {
        if (line != null) {
            new GeometricalObjectPainter(g2d).visit(line);
        }
    }
}
