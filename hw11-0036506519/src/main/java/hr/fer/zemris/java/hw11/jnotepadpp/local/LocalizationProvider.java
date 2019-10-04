package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Represents localization provider.
 * Also a singleton class.
 *
 * @author Stjepan Kovačić
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

    /**
     * Current language.
     */
    private String language;

    /**
     * Resource bundle.
     */
    private ResourceBundle bundle;

    /**
     * Class instance.
     */
    private static LocalizationProvider instance;

    /**
     * Basic private constructor.
     */
    private LocalizationProvider() {
        language = "hr";
        bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations", Locale.forLanguageTag(language));
    }

    /**
     * Gets instance of the class.
     *
     * @return instance
     */
    public static LocalizationProvider getInstance() {
        if (instance == null) {
            instance = new LocalizationProvider();
        }
        return instance;
    }

    @Override
    public String getString(String key) {
        return bundle.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        if (this.language.equals(language)) return;
        this.language = language;
        bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translations", Locale.forLanguageTag(language));
        fire();
    }
}
