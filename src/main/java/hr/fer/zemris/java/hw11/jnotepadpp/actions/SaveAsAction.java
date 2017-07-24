package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

/**
 * <code>SaveAsAction</code> saves newly created file or edited file saves as
 * new one.
 *
 * @author Ivan Rezic
 */
public class SaveAsAction extends MyAction {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which instantiates new save as action.
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
	public SaveAsAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		saveFileAs(panel);
	}

	/**
	 * Saves newly created file or saves edited file as new one.
	 *
	 * @param panel
	 *            Opened text area.
	 */
	protected void saveFileAs(MyTextArea panel) {
		if (panel == null)
			return;

		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Save As file");
		if (fc.showSaveDialog(container) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(container, "Saving canceled.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (fc.getSelectedFile().exists()) {
			int choose = JOptionPane.showConfirmDialog(container, "Do you want to overwrite existing file?", "",
					JOptionPane.YES_NO_OPTION);
			if (choose != JOptionPane.YES_OPTION)
				return;
		}

		Path openedFilePath = fc.getSelectedFile().toPath();
		try {
			Files.write(openedFilePath, panel.getTextArea().getText().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(container, "Saving aborted! File status not clear.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(container, "File saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
		changeTabInfo(openedFilePath);
		setCurrentTabIcon(loadIconFrom("icons/save_green.png"));
		panel.setEdited(false);
	}

	/**
	 * Helper method which changes selected tab info, after it is being saved as.
	 *
	 * @param openedFilePath
	 *            New file path.
	 */
	private void changeTabInfo(Path openedFilePath) {
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		panel.setFile(openedFilePath);

		int index = tabbedPane.getSelectedIndex();
		tabbedPane.setTitleAt(index, openedFilePath.getFileName().toString());
		tabbedPane.setToolTipTextAt(index, openedFilePath.toString());
	}
}
