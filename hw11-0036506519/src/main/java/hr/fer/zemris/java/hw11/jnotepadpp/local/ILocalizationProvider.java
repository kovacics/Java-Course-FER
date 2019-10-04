package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface represents localization provider.
 */
public interface ILocalizationProvider {

    /**
     * Returns string value for the key.
     *
     * @param key localization key
     * @return string value
     */
    String getString(String key);

    /**
     * Adds listener to the collection.
     *
     * @param l listener to add
     */
    void addLocalizationListener(ILocalizationListener l);

    /**
     * Removes listener from the collection.
     *
     * @param l listener to be removed
     */
    void removeLocalizationListener(ILocalizationListener l);

    /**
     * Returns current language.
     *
     * @return current language.
     */
    String getCurrentLanguage();
}
