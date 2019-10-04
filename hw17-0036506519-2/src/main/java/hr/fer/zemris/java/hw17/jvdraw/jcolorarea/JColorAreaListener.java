package hr.fer.zemris.java.hw17.jvdraw.jcolorarea;

import java.awt.*;

/**
 * Class represents listener of the {@link JColorArea} class.
 */
public interface JColorAreaListener {

    /**
     * Method executes when new color is selected.
     *
     * @param source   source
     * @param oldColor oldcolor
     * @param newColor newcolor
     */
    void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}