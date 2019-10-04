package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Interface represents tools used with mouse.
 */
public interface Tool {

    /**
     * Method is called when mouse has been pressed.
     *
     * @param e mouse event
     */
    void mousePressed(MouseEvent e);

    /**
     * Method is called when mouse has been released.
     *
     * @param e mouse event
     */
    void mouseReleased(MouseEvent e);

    /**
     * Method is called when mouse has been clicked.
     *
     * @param e mouse event
     */
    void mouseClicked(MouseEvent e);

    /**
     * Method is called when mouse has been moved.
     *
     * @param e mouse event
     */
    void mouseMoved(MouseEvent e);

    /**
     * Method is called when mouse has been dragged.
     *
     * @param e mouse event
     */
    void mouseDragged(MouseEvent e);

    /**
     * Method is called when tools has to draw something as addition
     * on the given graphics.
     *
     * @param g2d graphics object
     */
    void paint(Graphics2D g2d);
}