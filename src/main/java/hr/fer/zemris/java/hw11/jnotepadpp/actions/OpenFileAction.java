package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

/**
 * <code>OpenFileAction</code> opens existing file and sets it as text in new
 * tab.
 *
 * @author Ivan Rezic
 */
public class OpenFileAction extends MyAction {

	/**
	 * Constructor which instantiates new open file action.
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
	public OpenFileAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
	}

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();

		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(container) != JFileChooser.APPROVE_OPTION)
			return;
		File file = fc.getSelectedFile();
		Path filePath = file.toPath();
		if (!addTab(file, file.getPath()))
			return;

		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(container, "File" + filePath + "is not readable!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		byte[] data = null;
		try {
			data = Files.readAllBytes(filePath);
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(container, "Error while reading" + filePath + ".", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String text = new String(data, StandardCharsets.UTF_8);

		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		panel.setText(text);
	}

}
