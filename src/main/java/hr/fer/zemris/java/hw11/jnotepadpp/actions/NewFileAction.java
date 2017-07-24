package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

/**
 * <code>NewFileAction</code> creates new file and adds it to newly created tab.
 *
 * @author Ivan Rezic
 */
public class NewFileAction extends MyAction {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which instantiates new new file action.
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
	public NewFileAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		addTab(null, "Not saved yet");
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		panel.setText("");
	}

}
