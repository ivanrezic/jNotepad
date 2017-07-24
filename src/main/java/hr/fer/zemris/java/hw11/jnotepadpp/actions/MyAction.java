package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.MyTextArea;

/**
 * <code>MyAction</code> encapsulates most of actions used in
 * {@linkplain JNotepadPP} editor. It initializes action name, accelerator key,
 * mnemonic keys and short description. Also it provides usefull methods, such
 * as adding new tab, loading icons from path etc.
 *
 * @author Ivan Rezic
 */
public abstract class MyAction extends AbstractAction {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Maximum icon size. */
	private static final int MAX_ICON_SIZE = 3000;

	/** Number of new files since starting this app. */
	private static int newFiles = 0;

	/** {@linkplain JNotepadPP}. */
	protected JNotepadPP container;

	/** {@linkplain JNotepadPP} tabbed pane. */
	protected JTabbedPane tabbedPane;

	/**
	 * Constructor which instantiates new MyAction.
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
	public MyAction(JNotepadPP container, String actionName, String keyStroke, int keyEvent, String shortDescription) {
		this.container = container;
		this.tabbedPane = container.getTabbedPane();

		this.putValue(Action.NAME, container.getFlp().getString(actionName));
		this.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
		this.putValue(Action.MNEMONIC_KEY, keyEvent);
		this.putValue(Action.SHORT_DESCRIPTION, container.getFlp().getString(shortDescription));
	}

	@Override
	public abstract void actionPerformed(ActionEvent e);

	/**
	 * Adds the new tab and sets last added tab as selected.
	 *
	 * @param file
	 *            File to be attached to new tab.
	 * @param tooltip
	 *            Tool tip for added tab.
	 * @return True, if added successfully, false otherwise.
	 */
	protected boolean addTab(File file, String tooltip) {
		if (alreadyOpened(file))
			return false;

		MyTextArea panel = new MyTextArea(file, container);

		String title = "JNote " + ++newFiles;
		if (file != null) {
			title = file.getName();
		}

		tabbedPane.addTab(title, loadIconFrom("/save_green.png"), panel, tooltip);
		tabbedPane.setSelectedComponent(panel);
		return true;
	}

	/**
	 * Loads icon from specified path.
	 *
	 * @param imageLocation
	 *            The image location path.
	 * @return Icon from specified path.
	 */
	public static Icon loadIconFrom(String imageLocation) {
		InputStream is = JNotepadPP.class.getResourceAsStream(imageLocation);
		if (is == null) {
			System.err.println("Missing icon with provided path or icon size > " + MAX_ICON_SIZE + " bytes.");
		}

		byte[] bytes = new byte[MAX_ICON_SIZE];
		try (BufferedInputStream reader = new BufferedInputStream(is)) {
			reader.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ImageIcon(bytes);
	}

	/**
	 * Checks if tab with same name is already opened.
	 *
	 * @param file
	 *            File to be checked.
	 * @return True, if it is already contained in some tab, false otherwise.
	 */
	private boolean alreadyOpened(File file) {
		if (file == null)
			return false;
		boolean flag = false;
		int count = tabbedPane.getTabCount();

		for (int i = 0; i < count; i++) {
			flag = file.getName().equals(tabbedPane.getTitleAt(i));
			if (flag)
				break;
		}

		return flag;
	}

	/**
	 * Method which sets given icon as current tab icon.
	 *
	 * @param icon
	 *            New icon.
	 */
	protected void setCurrentTabIcon(Icon icon) {
		tabbedPane.setIconAt(tabbedPane.getSelectedIndex(), icon);
	}

	/**
	 * Helper method for {@linkplain ToolsAction} which for selected text
	 * performs given action.
	 *
	 * @param action
	 *            Function which will be applied to selected text.
	 */
	protected void toolAction(UnaryOperator<String> action) {
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		if (panel == null)
			return;
		JTextArea textArea = panel.getTextArea();

		int start = textArea.getSelectionStart();
		int end = textArea.getSelectionEnd();
		StringBuilder strBuilder = new StringBuilder(textArea.getText());

		String newText = textArea.getSelectedText();
		if (newText == null)
			return;
		strBuilder.replace(start, end, action.apply(newText));
		textArea.setText(strBuilder.toString());
	}

	/**
	 * Helper method for {@linkplain ToolsAction} which for selected text
	 * performs given function after being transformed to lines.
	 *
	 * @param action
	 *            Function which will be applied list created from selected
	 *            lines.
	 */
	protected void toolAction2(Function<List<String>, List<String>> action) {
		MyTextArea panel = (MyTextArea) tabbedPane.getSelectedComponent();
		if (panel == null)
			return;
		JTextArea textArea = panel.getTextArea();
		Document doc = textArea.getDocument();

		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		int offset = len != 0 ? Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark()) : doc.getLength();

		try {
			offset = textArea.getLineStartOffset(textArea.getLineOfOffset(offset));
			len = textArea.getLineEndOffset(textArea.getLineOfOffset(len + offset));

			String text = doc.getText(offset, len - offset);
			List<String> list = action.apply(Arrays.asList(text.split("\\r?\\n")));

			doc.remove(offset, len - offset);
			for (String string : list) {
				doc.insertString(offset, string + "\n", null);
				offset += string.length() + 1;
			}
		} catch (BadLocationException ignorable) {
		}
	}
}
