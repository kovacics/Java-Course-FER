package hr.fer.zemris.java.hw17.jvdraw.jcolorarea;

import java.awt.*;

/**
 * Interface represents provider of the color.
 */
@SuppressWarnings("unused")
public interface IColorProvider {
    /**
     * Getter of the current color.
     *
     * @return current color
     */
    Color getCurrentColor();

    /**
     * Method adds listener.
     *
     * @param l listener to add
     */
    void addColorChangeListener(JColorAreaListener l);

    /**
     * Method removes listener.
     *
     * @param l listener to remove
     */
    void removeColorChangeListener(JColorAreaListener l);
}