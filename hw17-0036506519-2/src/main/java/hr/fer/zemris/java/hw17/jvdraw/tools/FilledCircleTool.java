package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.canvas.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.drawingmodel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.jcolorarea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Class represents tools used when current state demands filled circle drawing.
 *
 * @author Stjepan Kovačić
 */
public class FilledCircleTool implements Tool {

    /**
     * Drawing canvas.
     */
    private JDrawingCanvas canvas;

    /**
     * Drawing model.
     */
    private DrawingModel model;

    /**
     * Area color provider.
     */
    private IColorProvider areaColorProvider;

    /**
     * Outline color provider.
     */
    private IColorProvider outlineColorProvider;

    /**
     * Constructed filled circle.
     */
    private FilledCircle filledCircle = null;

    /**
     * Counts clicks.
     */
    private int clicked;

    /**
     * COonstructs filled circle tools with specified canvas, model and color providers.
     *
     * @param canvas               drawing canvas
     * @param model                drawing model
     * @param areaColorProvider    area color provider
     * @param outlineColorProvider outline color provider
     */
    public FilledCircleTool(JDrawingCanvas canvas, DrawingModel model,
                            IColorProvider areaColorProvider, IColorProvider outlineColorProvider) {
        this.canvas = canvas;
        this.model = model;
        this.areaColorProvider = areaColorProvider;
        this.outlineColorProvider = outlineColorProvider;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        clicked++;
        if (clicked == 1) {
            filledCircle = new FilledCircle(e.getPoint(), 0,
                    outlineColorProvider.getCurrentColor(), areaColorProvider.getCurrentColor());
        } else if (clicked == 2) {
            model.add(filledCircle);
            filledCircle = null;
            clicked = 0;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (clicked == 1) {
            int radius = (int) Math.hypot(e.getX() - filledCircle.getX(), e.getY() - filledCircle.getY());
            filledCircle.setR(radius);
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
        if (filledCircle != null) {
            new GeometricalObjectPainter(g2d).visit(filledCircle);
        }
    }
}
