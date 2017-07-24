package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * <code>FormLocalizationProvider</code> enables {@linkplain JNotepadPP} to
 * register itself as LocalizationListener.
 *
 * @author Ivan Rezic
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor which instantiates new form localization provider.
	 *
	 * @param provider
	 *            Localization provider.
	 * @param {@linkplain JNotepadPP}
	 *            
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);

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
