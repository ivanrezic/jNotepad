package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * <code>ILocalizationProvider</code> is interface which must be implemented by
 * classes that are going to provide language change inside
 * {@linkplain JNotepadPP}.
 *
 * @author Ivan Rezic
 */
public interface ILocalizationProvider {

	/**
	 * Adds the localization listener.
	 *
	 * @param listener
	 *            the listener
	 */
	public void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Removes the localization listener.
	 *
	 * @param listener
	 *            the listener
	 */
	public void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Method used for getting property <code>String</code> from property files.
	 *
	 * @param key
	 *            Property files key.
	 * @return Value that mathces property file key.
	 */
	public String getString(String key);

}
