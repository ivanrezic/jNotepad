package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

/**
 * <code>ExitAppAction</code> exits from {@linkplain JNotepadPP}.
 *
 * @author Ivan Rezic
 */
public class ExitAppAction extends MyAction {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which instantiates new exit app action.
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
	public ExitAppAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		exitIfPossible();
	}

	/**
	 * Exits application, if there are unsaved files, user must choose where to
	 * save them are to discard them.
	 */
	public void exitIfPossible() {
		int count = tabbedPane.getComponentCount() - 1;

		while (count >= 0) {
			MyTextArea panel = (MyTextArea) tabbedPane.getComponentAt(count);
			CloseFileAction action = (CloseFileAction) container.getActions().get("closeFile");

			action.closeTab(panel);
			count--;
		}

		System.exit(0);
	}

}
