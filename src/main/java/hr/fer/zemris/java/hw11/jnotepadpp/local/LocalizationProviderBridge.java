package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * <code>LocalizationProviderBridge</code> provider bridge is a intermediate
 * class between a localizable application component and localization provider.
 * It provides easy connection and disconnection of LocalizationProvider
 * listeners.
 *
 * @author Ivan Rezic
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** Is listener connected to provider. */
	private boolean connected;

	/** LocalizationProvider listener. */
	private ILocalizationListener listener = () -> fire();

	/** Localization provider. */
	private ILocalizationProvider parent;

	/**
	 * Constructor which instantiates new localization provider bridge.
	 *
	 * @param provider
	 *            the provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.parent = provider;
	}

	/**
	 * Connects listener and provider.
	 */
	public void connect() {
		if (connected) {
			return;
		}
		connected = true;
		parent.addLocalizationListener(listener);
	}

	/**
	 * Disconnects listener and provider.
	 */
	public void disconnect() {
		if (!connected) {
			return;
		}
		connected = false;
		parent.removeLocalizationListener(listener);
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
