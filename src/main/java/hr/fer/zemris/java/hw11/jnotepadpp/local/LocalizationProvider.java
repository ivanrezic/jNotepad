package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <code>LocalizationProvider</code> is singleton instance that enables us to
 * get current langueage values saved in property files.
 *
 * @author Ivan Rezic
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** Current language. */
	private String language;

	/** Bundle which contains translation files. */
	private ResourceBundle bundle;

	/** {@linkplain LocalizationProvider} instance. */
	private static final LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Constructor which instantiates new localization provider.
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}

	/**
	 * Gets the single instance of LocalizationProvider.
	 *
	 * @return single instance of LocalizationProvider
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * Method which sets given language as language.
	 *
	 * @param language
	 *            New language.
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.translation", locale);
		fire();
	}

	/**
	 * Method used for getting property <code>Language</code>.
	 *
	 * @return Language.
	 */
	public String getLanguage() {
		return language;
	}
}
