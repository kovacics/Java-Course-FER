package hr.fer.zemris.java.hw17.jvdraw.editors;

import javax.swing.*;

/**
 * Abstract class which represents geometrical object editors.
 */
public abstract class GeometricalObjectEditor extends JPanel {

    /**
     * Checks if editing is valid.
     */
    public abstract void checkEditing();

    /**
     * Accepts editing.
     */
    public abstract void acceptEditing();
}