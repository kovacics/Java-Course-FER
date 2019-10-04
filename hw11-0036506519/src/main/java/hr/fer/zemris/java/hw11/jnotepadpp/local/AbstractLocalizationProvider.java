package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents abstract localization provider.
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

    /**
     * List of all listeners.
     */
    List<ILocalizationListener> listeners = new ArrayList<>();

    /**
     * Basic constructor.
     */
    public AbstractLocalizationProvider() {
    }

    @Override
    public void addLocalizationListener(ILocalizationListener l) {
        listeners.add(l);
    }

    @Override
    public void removeLocalizationListener(ILocalizationListener l) {
        listeners.remove(l);
    }

    public void fire() {
        listeners.forEach(ILocalizationListener::localizationChanged);
    }
}
