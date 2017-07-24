package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * <code>ToolsAction</code> encapsulates multiple actions into one. Such as text
 * invert, etc.
 *
 * @author Ivan Rezic
 */
public class ToolsAction extends MyAction {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** tool. */
	private String tool;

	/**
	 * Constructor which instantiates new tools action.
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
	public ToolsAction(String tool, JNotepadPP container, String actionName, String keyStroke, int keyEvent,
			String shortDescription) {
		super(container, actionName, keyStroke, keyEvent, shortDescription);
		this.tool = tool;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (tool) {
		case "uppercase":
			toolAction(String::toUpperCase);
			break;
		case "lowercase":
			toolAction(String::toLowerCase);
			break;
		case "invert":
			toolAction((text) -> invertText(text));
			break;
		case "descending":
			toolAction2((list) -> sort("desc", list));
			break;
		case "ascending":
			toolAction2((list) -> sort("asc", list));
			break;
		case "unique":
			toolAction2((list) -> unique(list));
			break;
		}
	}

	/**
	 * Invert given text.
	 *
	 * @param text
	 *            Text to be inverted.
	 * @return Inverted text as string.
	 */
	private String invertText(String text) {
		StringBuilder sb = new StringBuilder(text.length());
		for (char c : text.toCharArray()) {
			if (Character.isUpperCase(c)) {
				sb.append(Character.toLowerCase(c));
			} else if (Character.isLowerCase(c)) {
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Sort given text.
	 *
	 * @param order
	 *            Sort order.
	 * @param list
	 *            Given text as list.
	 * @return Result as list.
	 */
	private List<String> sort(String order, List<String> list) {
		Locale hrLocale = new Locale("hr");
		Collator hrCollator = Collator.getInstance(hrLocale);

		StringBuilder stringBuilder = new StringBuilder();
		if ("desc".equals(order)) {
			Collections.sort(list, hrCollator.reversed());
		} else {
			Collections.sort(list, hrCollator);
		}
		list.forEach((e) -> stringBuilder.append(e));

		return list;
	}

	/**
	 * Leave only unique elements in text.
	 *
	 * @param list
	 *            Given text as list.
	 * @return Result as list.
	 */
	private List<String> unique(List<String> list) {
		Set<String> set = new LinkedHashSet<>(list);
		return new ArrayList<>(set);
	}
}
