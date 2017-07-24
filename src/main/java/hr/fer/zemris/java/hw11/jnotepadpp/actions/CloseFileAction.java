package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

/**
 * <code>CloseFileAction</code> closes current tab opened.
 *
 * @author Ivan Rezic
 */
public class CloseFileAction extends MyAction {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which instantiates new close file action.
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
	public CloseFileAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		if (panel == null) return;

		closeTab(panel);
	}

	/**
	 * Close tab if file is saved, if not checks first whether user wants to save it first.
	 *
	 * @param Opened text area.
	 */
	protected void closeTab(MyTextArea panel) {
		if (panel.isEdited()) {
			tabbedPane.setSelectedComponent(panel);
			int choice = JOptionPane.showConfirmDialog(container, "Do you want to save this file?",
					"File is not saved yet!", JOptionPane.YES_NO_CANCEL_OPTION);

			if (choice == JOptionPane.YES_OPTION) {
				if (panel.isFileUnsaved()) {
					SaveAsAction saveAs = (SaveAsAction) container.getActions().get("saveAs");
					saveAs.saveFileAs(panel);
				} else {
					SaveFileAction save = (SaveFileAction) container.getActions().get("saveFile");
					save.saveFile(panel);
				}
			} else if (choice == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
				
		tabbedPane.remove(panel);
		
		//set status bar on default if there are no tabs opened
		if (tabbedPane.getTabCount() == 0){
			container.getStatusBar().setDefaultValues();	
		}
	}
}
