package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Path;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyAction;

/**
 * <code>MyTextArea</code> represents editable area of {@linkplain JNotepadPP}
 * editor. It provides usefull insights about file opened, text added and other
 * simple methods.
 *
 * @author Ivan Rezic
 */
public class MyTextArea extends JPanel {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** {@linkplain JNotepadPP} tabbed pane. */
	private JTabbedPane tabbedPane;

	/** Was this file edited. */
	private boolean edited = false;

	/** {@linkplain JNotepadPP} editor text area. */
	private JTextArea textArea;

	/** {@linkplain JNotepadPP} status bar. */
	private MyStatusBar statusBar;

	/** Current file opened. */
	private File file;

	/**
	 * Constructor which instantiates new MyTextArea.
	 *
	 * @param file
	 *            Wanted file.
	 * @param container
	 *            {@linkplain JNotepadPP}
	 */
	public MyTextArea(File file, JNotepadPP container) {
		this.file = file;
		this.tabbedPane = container.getTabbedPane();
		this.statusBar = container.getStatusBar();

		statusBar.setDefaultValues();
		setLayout(new BorderLayout());
	}

	/**
	 * Method used for getting property <code>TextArea</code>.
	 *
	 * @return Text area.
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * Method which sets new value as text.
	 *
	 * @param text
	 *            New text for text area.
	 */
	public void setText(String text) {
		initTextArea(text);

		setTextAreaListener(MyAction.loadIconFrom("icons/save_red.png"));
		setCaretListener();
	}

	/**
	 * Initializes text area.
	 *
	 * @param text
	 *            Text to be added in text area.
	 */
	private void initTextArea(String text) {
		this.textArea = new JTextArea(text);
		JScrollPane scroll = new JScrollPane(textArea);
		add(scroll, BorderLayout.CENTER);
	}

	/**
	 * Method which initializes text area listener.
	 *
	 * @param icon
	 *            Icon which tells us that file has changed.
	 */
	public void setTextAreaListener(Icon icon) {
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				changeIcon();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				changeIcon();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				changeIcon();
			}

			private void changeIcon() {
				tabbedPane.setIconAt(tabbedPane.getSelectedIndex(), icon);
				edited = true;
			}
		});
	}

	/**
	 * Method which initializes caret listener.
	 */
	private void setCaretListener() {
		textArea.addCaretListener(new CaretListener() {

			@Override
			public void caretUpdate(CaretEvent e) {
				String text = textArea.getText();
				int caretPosition = textArea.getCaretPosition();
				try {
					int line = textArea.getLineOfOffset(caretPosition);
					int column = caretPosition - textArea.getLineStartOffset(line);
					int selection = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

					statusBar.setCaretPositionText(
							(String.format("Ln: %d   Col: %d   Sel: %d", line + 1, column, selection)));
				} catch (BadLocationException ignorable) {
				}

				statusBar.setLengthText(String.format(" length: %d", text.replaceAll("\\r?\n", "").length()));
			}
		});
	}

	/**
	 * Checks if file is not saved.
	 *
	 * @return True, if it is file unsaved, false otherwise.
	 */
	public boolean isFileUnsaved() {
		return file == null;
	}

	/**
	 * Method used for getting property <code>OpenedFilePath</code>.
	 *
	 * @return Opened file path.
	 */
	public Path getOpenedFilePath() {
		return file.toPath();
	}

	/**
	 * Checks if this text area is connected to given file.
	 *
	 * @param file
	 *            File to be checked.
	 * @return True, if it is successful, false otherwise.
	 */
	public boolean hasFile(File file) {
		if (this.file == null) {
			return false;
		}

		return this.file.equals(file);
	}

	/**
	 * Method which sets its file as given file.
	 *
	 * @param file
	 *            New file to be added.
	 */
	public void setFile(Path file) {
		this.file = file.toFile();
	}

	/**
	 * Checks if textarea is edited.
	 *
	 * @return True, if it is edited, false otherwise.
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Method which sets new value as edited.
	 *
	 * @param edited
	 *            True if edited, false otherwise.
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * Method used for getting property <code>stats</code>.
	 *
	 * @return Caret stats as string.
	 */
	public int[] getStats() {
		int[] stats = new int[] { 0, 0, 0 };
		String text = textArea.getText();

		if ("".equals(text)) {
			stats[2] = 0;
		} else {
			stats[2] = textArea.getLineCount();
		}
		stats[0] = text.replaceAll("\\n", "").length();
		stats[1] = text.replaceAll("\\s*", "").length();

		return stats;
	}
}
