package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

/**
 * <code>SaveFileAction</code> saves current file if it is edit.
 *
 * @author Ivan Rezic
 */
public class SaveFileAction extends MyAction{

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which instantiates new save file action.
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
	public SaveFileAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		saveFile(panel);
	}

	/**
	 * Saves edited file to chosen path.
	 *
	 * @param Opened text area.
	 */
	protected void saveFile(MyTextArea panel) {
		if (panel == null) {
			return;
		}else if (panel.isFileUnsaved()) {
			JOptionPane.showMessageDialog(container, "Click 'Save as' option first!");	
			return;
		}	
		Path openedFilePath = panel.getOpenedFilePath();
		
		try {
			Files.write(openedFilePath, panel.getTextArea().getText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(container, "Saving aborted! File status not clear.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(container, "File saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
		setCurrentTabIcon(loadIconFrom("icons/save_green.png"));
		panel.setEdited(false);
	}
}
