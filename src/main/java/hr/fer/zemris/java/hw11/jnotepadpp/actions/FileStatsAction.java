package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

/**
 * <code>FileStatsAction</code> provides useful information about current file
 * displayed.
 *
 * @author Ivan Rezic
 */
public class FileStatsAction extends MyAction {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which instantiates new file stats action.
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
	public FileStatsAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		if (panel == null)
			return;
		int[] stats = panel.getStats();

		String stat = String.format("This file has:%n%d characters%n%d characters that are not blank%n%d lines",
				stats[0], stats[1], stats[2]);

		JOptionPane.showMessageDialog(container, stat);
	}

}
