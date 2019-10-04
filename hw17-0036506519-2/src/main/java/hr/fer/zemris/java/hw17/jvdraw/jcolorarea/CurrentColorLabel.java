package hr.fer.zemris.java.hw17.jvdraw.jcolorarea;

import javax.swing.*;
import java.awt.*;

/**
 * Class represents jlabel specified for selected colors info.
 *
 * @author Stjepan Kovačić
 */
public class CurrentColorLabel extends JLabel implements JColorAreaListener {

    /**
     * Foreground color provider.
     */
    private IColorProvider fg;

    /**
     * Background color provider.
     */
    private IColorProvider bg;

    /**
     * Constructs color label.
     *
     * @param fg fg color provider
     * @param bg bg color provider
     */
    public CurrentColorLabel(IColorProvider fg, IColorProvider bg) {
        super(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).",
                fg.getCurrentColor().getRed(),
                fg.getCurrentColor().getGreen(),
                fg.getCurrentColor().getBlue(),
                bg.getCurrentColor().getRed(),
                bg.getCurrentColor().getGreen(),
                bg.getCurrentColor().getBlue()
        ));

        this.fg = fg;
        this.bg = bg;

        bg.addColorChangeListener(this);
        fg.addColorChangeListener(this);
    }

    @Override
    public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
        setText(String.format("Foreground color: (%d, %d, %d), background color: (%d, %d, %d).",
                fg.getCurrentColor().getRed(),
                fg.getCurrentColor().getGreen(),
                fg.getCurrentColor().getBlue(),
                bg.getCurrentColor().getRed(),
                bg.getCurrentColor().getGreen(),
                bg.getCurrentColor().getBlue()
        ));
    }
}
