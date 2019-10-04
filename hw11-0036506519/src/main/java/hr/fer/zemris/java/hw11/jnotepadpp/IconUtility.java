package hr.fer.zemris.java.hw11.jnotepadpp;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Represents utility class for working with icons.
 * Also a singleton class.
 *
 * @author Stjepan Kovačić
 */
public class IconUtility {

    /**
     * Instance of the utility.
     */
    private static IconUtility instance;

    private IconUtility() {
    }

    /**
     * Returns instance of the class.
     *
     * @return class instance
     */
    public static IconUtility getInstance() {
        if (instance == null) {
            instance = new IconUtility();
        }
        return instance;
    }

    /**
     * Method returns icon specified by the string path.
     *
     * @param s string specifying icon
     * @return icon
     */
    public Icon getIcon(String s) {
        InputStream is = this.getClass().getResourceAsStream(s);
        if (is == null) {
            throw new RuntimeException("Cannot find icon.");
        }
        byte[] bytes;
        try {
            bytes = is.readAllBytes();
            is.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading.");
        }
        return new ImageIcon(bytes);
    }
}
