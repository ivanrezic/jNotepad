package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * <code>LanguageAction</code> sets selected language as new one. 
 * Available languages are: english, croatian and german.
 *
 * @author Ivan Rezic
 */
public class LanguageAction extends MyAction {
	
	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** language. */
	private String language;

	/**
	 * Constructor which instantiates new language action.
	 *
	 * @param container
	 *            {@linkplain JNotepadPP}
	 * @param actionName
	 *            The action name.
	 * @param keyStroke
	 *            Action key stroke.
	 * @param keyEvent
	 *            Action accelerator keys.
	 * @param shortDescription
	 *            Action short description.
	 */
	public LanguageAction(String language, JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
		
		this.language = language;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (LocalizationProvider.getInstance().getLanguage().equals(language)) {
			return;
		}
		
		LocalizationProvider.getInstance().setLanguage(language);
	}
}
