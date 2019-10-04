package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Represents localization provider bridge.
 *
 * @author Stjepan Kovačić
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

    /**
     * Parent provider.
     */
    private ILocalizationProvider parent;

    /**
     * Connected flag.
     */
    private boolean connected;

    /**
     * Listener.
     */
    private ILocalizationListener listener = this::fire;

    /**
     * Last known parent language before it was disconnected.
     */
    private String lastKnownLanguage;


    /**
     * Constructs provider bridge.
     *
     * @param parent provider
     */
    public LocalizationProviderBridge(ILocalizationProvider parent) {
        this.parent = parent;
    }

    @Override
    public String getString(String key) {
        return parent.getString(key);
    }

    @Override
    public String getCurrentLanguage() {
        return lastKnownLanguage;
    }

    /**
     * Connects bridge, adds listener to the parent provider.
     */
    public void connect() {
        if (!connected) {
            if (!parent.getCurrentLanguage().equals(lastKnownLanguage)) {
                fire();
            }
            parent.addLocalizationListener(listener);
            connected = true;
        }
    }

    /**
     * Disconnects bridge, removes listener from the parent provider.
     */
    public void disconnect() {
        lastKnownLanguage = parent.getCurrentLanguage();
        parent.removeLocalizationListener(listener);
        connected = false;
    }
}
