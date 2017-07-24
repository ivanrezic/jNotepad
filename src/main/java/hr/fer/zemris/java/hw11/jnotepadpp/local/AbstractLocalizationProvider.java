package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * <code>AbstractLocalizationProvider</code> is abstract class that enables
 * LocalizatonProvider to add listeners and to notify them when language change
 * occurs.
 *
 * @author Ivan Rezic
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/** LocalizationProvider listeners. */
	private List<ILocalizationListener> listeners = new ArrayList<>();

	/**
	 * Constructor which instantiates new abstract localization provider.
	 */
	public AbstractLocalizationProvider() {
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notifies all connected listeners.
	 */
	public void fire() {
		for (ILocalizationListener listener : listeners) {
			listener.localizationChanged();
		}
	}

	@Override
	public abstract String getString(String key);
}
