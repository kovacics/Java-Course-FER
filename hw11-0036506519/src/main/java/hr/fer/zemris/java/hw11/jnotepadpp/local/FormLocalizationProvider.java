package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Represents form localization provider.
 *
 * @author Stjepan Kovačić
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

    /**
     * Constructs provider.
     *
     * @param parent parent provider
     * @param frame  frame
     */
    public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
        super(parent);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                connect();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                disconnect();
            }
        });
    }
}
