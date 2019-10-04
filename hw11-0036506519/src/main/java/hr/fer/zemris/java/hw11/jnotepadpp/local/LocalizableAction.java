package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;

/**
 * Represents special abstract action which uses localization.
 *
 * @author Stjepan Kovačić
 */
public abstract class LocalizableAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    /**
     * Key of the action.
     */
    private String key;

    /**
     * Localization provider.
     */
    private ILocalizationProvider lp;

    /**
     * Constructs action with specified key and provider.
     *
     * @param key key
     * @param lp  provider
     */
    public LocalizableAction(String key, ILocalizationProvider lp) {

        this.key = key;
        this.lp = lp;

        putValues();

        lp.addLocalizationListener(this::putValues);
    }

    /**
     * Puts values of thr action.
     */
    private void putValues() {
        putValue(NAME, lp.getString(this.key));
        putValue(SHORT_DESCRIPTION, lp.getString(this.key + "_sd"));
    }
}
