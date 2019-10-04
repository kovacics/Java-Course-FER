package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;

/**
 * Class represents localized version of the {@link JMenu} class.
 *
 * @author Stjepan Kovačić
 */
public class LJMenu extends JMenu {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs LJ Menu with specified key and provider.
     *
     * @param key key for the menu text
     * @param lp  provider of the text
     */
    public LJMenu(String key, ILocalizationProvider lp) {
        setText(lp.getString(key));
        lp.addLocalizationListener(() -> setText(lp.getString(key)));
    }
}
