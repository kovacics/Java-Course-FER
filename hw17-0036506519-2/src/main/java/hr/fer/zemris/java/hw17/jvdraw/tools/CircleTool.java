package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.canvas.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.jcolorarea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Class represents tools used when current state demands circle drawing.
 *
 * @author Stjepan Kovačić
 */
public class CircleTool implements Tool {

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
    private IColorProvider outlineColorProvider;

    /**
     * Constructed circle.
     */
    private Circle circle = null;

    /**
     * Counts clicks.
     */
    private int clicked;


    /**
     * Constructs circle tools with specified canvas, model and color provider.
     *
     * @param canvas               drawing canvas
     * @param model                drawing model
     * @param outlineColorProvider color provider
     */
    public CircleTool(JDrawingCanvas canvas, DrawingModel model, IColorProvider outlineColorProvider) {
        this.canvas = canvas;
        this.model = model;
        this.outlineColorProvider = outlineColorProvider;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        clicked++;
        if (clicked == 1) {
            circle = new Circle(e.getPoint(), 0, outlineColorProvider.getCurrentColor());
        } else if (clicked == 2) {
            model.add(circle);
            circle = null;
            clicked = 0;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (clicked == 1) {
            int radius = (int) Math.hypot(e.getX() - circle.getX(), e.getY() - circle.getY());
            circle.setR(radius);
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
        if (circle != null) {
            new GeometricalObjectPainter(g2d).visit(circle);
        }
    }
}
