package hr.fer.zemris.java.hw17.jvdraw.jcolorarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Button-like component which is used for choosing colors.
 *
 * @author Stjepan Kovačić
 */
public class JColorArea extends JComponent implements IColorProvider {

    /**
     * Current color of the component.
     */
    private Color color;

    /**
     * List of all component listeners.
     */
    private List<JColorAreaListener> listeners = new ArrayList<>();

    /**
     * Component width.
     */
    private static final int COMP_WIDTH = 15;

    /**
     * Component height.
     */
    private static final int COMP_HEIGHT = 15;

    /**
     * Constructs component with specified initial color.
     *
     * @param color initial color
     */
    public JColorArea(Color color) {
        this.color = color;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setCurrentColor(JColorChooser.showDialog(
                        JColorArea.this, "Select color", JColorArea.this.getCurrentColor()
                ));
            }
        });
    }

    /**
     * Setter of the current color.
     *
     * @param color color to set.
     */
    public void setCurrentColor(Color color) {
        if (color != null) {
            this.color = color;
            notifyListeners(this.color, color);
            this.repaint();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COMP_WIDTH, COMP_HEIGHT);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(COMP_WIDTH, COMP_HEIGHT);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(COMP_WIDTH, COMP_HEIGHT);
    }

    @Override
    public Color getCurrentColor() {
        return color;
    }

    @Override
    public void addColorChangeListener(JColorAreaListener l) {
        listeners.add(l);
    }

    @Override
    public void removeColorChangeListener(JColorAreaListener l) {
        listeners.remove(l);
    }

    private void notifyListeners(Color oldColor, Color newColor) {
        listeners.forEach(l -> l.newColorSelected(this, oldColor, newColor));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillRect(1, 1, getPreferredSize().width - 2, getPreferredSize().height - 2);
    }
}
