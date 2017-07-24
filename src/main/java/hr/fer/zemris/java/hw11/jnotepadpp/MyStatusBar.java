package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * <code>MyStatusBar</code> represents status bar for {@linkplain JNotepadPP}
 * editor. It provied insight to total characters count, current caret
 * line,column and selection size. Also it show us in its right corner current
 * date and time.
 *
 * @author Ivan Rezic
 */
public class MyStatusBar extends JPanel {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Status bar height. */
	private static final int STATUS_BAR_HEIGHT = 20;

	/** Total number of characters in text area. */
	private JLabel length;

	/** Caret information. */
	private JLabel caretPosition;

	/** Current date and time. */
	private JLabel dateAndTime;

	/**
	 * Constructor which instantiates new my status bar.
	 */
	public MyStatusBar() {
		setLayout(new GridLayout(1, 3));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setPreferredSize(new Dimension(getWidth(), STATUS_BAR_HEIGHT));

		initStatusBar();
	}

	/**
	 * Initializes the status bar.
	 */
	private void initStatusBar() {
		length = new JLabel(" length: 0");
		length.setPreferredSize(new Dimension((int) (getWidth() / 3.0), getHeight()));
		length.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));

		caretPosition = new JLabel("Ln: 0   Col: 0   Sel: 0");
		caretPosition.setPreferredSize(new Dimension((int) (getWidth() / 3.0), getHeight()));
		caretPosition.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.LIGHT_GRAY));
		caretPosition.setHorizontalAlignment(SwingConstants.CENTER);

		add(length);
		add(caretPosition);
		initDate();
	}

	/**
	 * Initializes the date and time.
	 */
	private void initDate() {
		dateAndTime = new JLabel();
		dateAndTime.setPreferredSize(new Dimension((int) (getWidth() / 3.0), getHeight()));
		dateAndTime.setHorizontalAlignment(SwingConstants.RIGHT);

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss ");
		Timer timer = new Timer(500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Calendar calendar = Calendar.getInstance();
				dateAndTime.setText(format.format(calendar.getTime()));
			}
		});

		timer.setRepeats(true);
		timer.setInitialDelay(0);
		timer.start();
		add(dateAndTime);
	}

	/**
	 * Sets the default values for length and caret info in status bar.
	 */
	public void setDefaultValues() {
		length.setText(" length: 0");
		caretPosition.setText("Ln: 0   Col: 0   Sel: 0");
	}

	/**
	 * Sets new value as length text.
	 *
	 * @param text
	 *            The new length text.
	 */
	public void setLengthText(String text) {
		this.length.setText(text);
	}

	/**
	 * Sets new value as caret position text.
	 *
	 * @param text
	 *            The new caret position text.
	 */
	public void setCaretPositionText(String text) {
		this.caretPosition.setText(text);
	}
}
