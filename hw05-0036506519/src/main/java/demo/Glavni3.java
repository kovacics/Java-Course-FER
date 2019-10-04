package demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Demo class for testing {@code LSystemBuilderImpl} class and specificly creating
 * {@code LSystem} using some chosen textual file.
 */
public class Glavni3 {

    /**
     * Main method of the class.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        LSystemViewer.showLSystem(LSystemBuilderImpl::new);
    }
}
