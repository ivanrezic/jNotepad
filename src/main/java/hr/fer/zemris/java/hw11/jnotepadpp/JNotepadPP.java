package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultEditorKit.CopyAction;
import javax.swing.text.DefaultEditorKit.CutAction;
import javax.swing.text.DefaultEditorKit.PasteAction;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAppAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.FileStatsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.LanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.MyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ToolsAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * <code>JNotepadPP</code> is a text editor and source code editor for OS
 * independant. It supports tabbed editing, which allows working with multiple
 * open files in a single window.
 *
 * @author Ivan Rezic
 */
public class JNotepadPP extends JFrame {

	/** Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Tabbed pane of this editor. */
	private JTabbedPane tabbedPane;

	/** Menu bar of this editor. */
	private JMenuBar menuBar;

	/** Status bar of this editor. */
	private MyStatusBar statusBar;

	/** Map containing all the actions used in this editor. */
	private HashMap<String, Action> actions;

	/** It provides dynamic editor localization. */
	private FormLocalizationProvider flp;

	/**
	 * Constructor which instantiates new JNotepadPP.
	 */
	public JNotepadPP() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setBounds(20, 20, 1000, 650);
		setTitle("JNotepad++");

		actions = new HashMap<>();
		initGUI();
	}

	/**
	 * Inits the GUI.
	 */
	private void initGUI() {
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new BorderLayout());
		container.add(mainPanel, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane();
		mainPanel.add(tabbedPane, BorderLayout.CENTER);
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		statusBar = new MyStatusBar();
		mainPanel.add(statusBar, BorderLayout.PAGE_END);

		createActions();
		addActions();
		addCloseOperationAction();
		addLocalizationListener();
	}

	/**
	 * Creates the actions.
	 */
	private void createActions() {
		actions.put("openFile", new OpenFileAction(this, "openFile", "control O", KeyEvent.VK_O, "openFileDesc"));
		actions.put("saveFile", new SaveFileAction(this, "saveFile", "control S", KeyEvent.VK_S, "saveFileDesc"));
		actions.put("saveAs", new SaveAsAction(this, "saveAs", "control alt S", KeyEvent.VK_A, "saveAsDesc"));
		actions.put("newFile", new NewFileAction(this, "newFile", "control N", KeyEvent.VK_N, "newFileDesc"));
		actions.put("closeFile", new CloseFileAction(this, "closeFile", "control W", KeyEvent.VK_L, "closeFileDesc"));
		actions.put("stats", new FileStatsAction(this, "stats", "control shift S", KeyEvent.VK_I, "statsDesc"));
		actions.put("exitApp", new ExitAppAction(this, "exitApp", "control alt X", KeyEvent.VK_X, "exitAppDesc"));

		actions.put("cut", editPremadeAction(new CutAction(), "cut", "cutDesc", KeyEvent.VK_T, "control X"));
		actions.put("copy", editPremadeAction(new CopyAction(), "copy", "copyDesc", KeyEvent.VK_Y, "control C"));
		actions.put("paste", editPremadeAction(new PasteAction(), "paste", "pasteDesc", KeyEvent.VK_P, "control V"));

		actions.put("uppercase",
				new ToolsAction("uppercase", this, "uppercase", "control U", KeyEvent.VK_U, "uppercaseDesc"));
		actions.put("lowercase",
				new ToolsAction("lowercase", this, "lowercase", "control L", KeyEvent.VK_R, "lowercaseDesc"));
		actions.put("invert", new ToolsAction("invert", this, "invert", "control I", KeyEvent.VK_I, "invertDesc"));
		actions.put("unique", new ToolsAction("unique", this, "unique", "control Q", KeyEvent.VK_Q, "uniqueDesc"));
		actions.put("descending",
				new ToolsAction("descending", this, "descending", "control D", KeyEvent.VK_D, "descendingDesc"));
		actions.put("ascending",
				new ToolsAction("ascending", this, "ascending", "control G", KeyEvent.VK_G, "ascendingDesc"));

		actions.put("english",
				new LanguageAction("en", this, "english", "control alt H", KeyEvent.VK_H, "englishDesc"));
		actions.put("croatian",
				new LanguageAction("hr", this, "croatian", "control alt C", KeyEvent.VK_C, "croatianDesc"));
		actions.put("german", new LanguageAction("de", this, "german", "control alt D", KeyEvent.VK_E, "germanDesc"));
	}

	/**
	 * Edits the premade actions.
	 *
	 * @param action
	 *            Premade action.
	 * @param name
	 *            New wanted name for premade action.
	 * @param description
	 *            New short description for premade action.
	 * @param mnemonic
	 *            New mnemonic key for premade action.
	 * @param keyStroke
	 *            New key stroke for premade action.
	 * @return Edited premade action.
	 */
	private Action editPremadeAction(Action action, String name, String description, int mnemonic, String keyStroke) {
		action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(keyStroke));
		action.putValue(Action.NAME, flp.getString(name));
		action.putValue(Action.SHORT_DESCRIPTION, flp.getString(description));
		action.putValue(Action.MNEMONIC_KEY, mnemonic);

		return action;
	}

	/**
	 * Adds the actions to toolbar and to menubar.
	 */
	private void addActions() {
		JToolBar toolBar = new JToolBar("Alatna traka");
		getContentPane().add(toolBar, BorderLayout.PAGE_START);

		JMenu fileMenu = new JMenu("File");
		JMenu editMenu = new JMenu("Edit");
		JMenu toolsMenu = new JMenu("Tools");
		JMenu languagesMenu = new JMenu("Languages");
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(toolsMenu);
		menuBar.add(languagesMenu);

		addToolbarAndMenuItem(actions.get("newFile"), fileMenu, toolBar, "icons/new_file.png");
		addToolbarAndMenuItem(actions.get("openFile"), fileMenu, toolBar, "icons/open_file.png");
		addToolbarAndMenuItem(actions.get("saveFile"), fileMenu, toolBar, "icons/save_blue.png");
		addToolbarAndMenuItem(actions.get("saveAs"), fileMenu, toolBar, "icons/saveAs.png");
		addToolbarAndMenuItem(actions.get("closeFile"), fileMenu, toolBar, "icons/close_file.png");
		fileMenu.addSeparator();
		addToolbarAndMenuItem(actions.get("stats"), fileMenu, toolBar, "icons/stats.png");
		addToolbarAndMenuItem(actions.get("exitApp"), fileMenu, toolBar, "icons/exit_app.png");

		toolBar.addSeparator();
		addToolbarAndMenuItem(actions.get("copy"), editMenu, toolBar, "icons/copy.png");
		addToolbarAndMenuItem(actions.get("cut"), editMenu, toolBar, "icons/cut.png");
		addToolbarAndMenuItem(actions.get("paste"), editMenu, toolBar, "icons/paste.png");

		toolBar.addSeparator();
		addToolbarAndMenuItem(actions.get("uppercase"), toolsMenu, toolBar, "icons/uppercase.png");
		addToolbarAndMenuItem(actions.get("lowercase"), toolsMenu, toolBar, "icons/lowercase.png");
		addToolbarAndMenuItem(actions.get("invert"), toolsMenu, toolBar, "icons/invert.png");
		addToolbarAndMenuItem(actions.get("unique"), toolsMenu, toolBar, "icons/unique.png");
		JMenu subMenu = new JMenu("Sort");
		toolsMenu.add(subMenu);
		addToolbarAndMenuItem(actions.get("ascending"), subMenu, toolBar, "icons/descending.png");
		addToolbarAndMenuItem(actions.get("descending"), subMenu, toolBar, "icons/ascending.png");

		toolBar.addSeparator();
		addToolbarAndMenuItem(actions.get("english"), languagesMenu, toolBar, "icons/english.png");
		addToolbarAndMenuItem(actions.get("croatian"), languagesMenu, toolBar, "icons/croatian.png");
		addToolbarAndMenuItem(actions.get("german"), languagesMenu, toolBar, "icons/german.png");
	}

	/**
	 * Helper menu for {@link #addActions()}. It enables easy adding of menu
	 * items and toolbar buttons.
	 *
	 * @param action
	 *            The action.
	 * @param menu
	 *            Menu wanted.
	 * @param toolBar
	 *            Toolbar wanted.
	 * @param imagePath
	 *            Image path for toolbar button image.
	 */
	private void addToolbarAndMenuItem(Action action, JMenu menu, JToolBar toolBar, String imagePath) {
		menu.add(new JMenuItem(action));
		toolBar.add(createToolbarButton(imagePath, action));
	}

	/**
	 * Helper method for
	 * {@link #addToolbarAndMenuItem(Action, JMenu, JToolBar, String)}. It
	 * enables easy editing and adding button.
	 *
	 * @param imagePath
	 *            Image path with icon for toolbar button.
	 * @param action
	 *            Action associated to out button.
	 * @return Edited jButton.
	 */
	private JButton createToolbarButton(String imagePath, Action action) {
		JButton button = new JButton();

		button.setAction(action);
		button.setHideActionText(true);
		button.setIcon(MyAction.loadIconFrom(imagePath));

		return button;
	}

	/**
	 * Adds the close operation action upon editor closing.
	 */
	private void addCloseOperationAction() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				ExitAppAction action = (ExitAppAction) actions.get("exitApp");
				action.exitIfPossible();
			}
		});
	}

	/**
	 * Adds the localization listener.
	 */
	private void addLocalizationListener() {
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				for (Map.Entry<String, Action> entry : actions.entrySet()) {
					String name = entry.getKey();
					Action value = entry.getValue();

					value.putValue(Action.NAME, flp.getString(name));
					value.putValue(Action.SHORT_DESCRIPTION, flp.getString(name + "Desc"));
				}

				JMenu menu1 = (JMenu) menuBar.getComponent(0);
				menu1.setText(flp.getString("file"));
				JMenu menu2 = (JMenu) menuBar.getComponent(1);
				menu2.setText(flp.getString("edit"));
				JMenu menu3 = (JMenu) menuBar.getComponent(2);
				menu3.setText(flp.getString("tools"));
				JMenu menu4 = (JMenu) menuBar.getComponent(3);
				menu4.setText(flp.getString("languages"));
			}
		});
	}

	/**
	 * Method used for getting property <code>TabbedPane</code>.
	 *
	 * @return tabbed pane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * Method used for getting property <code>StatusBar</code>.
	 *
	 * @return status bar
	 */
	public MyStatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * Method used for getting property <code>Actions</code>.
	 *
	 * @return actions
	 */
	public HashMap<String, Action> getActions() {
		return actions;
	}

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            The arguments from command line, not used here.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

	/**
	 * Method used for getting property {@link #flp}.
	 *
	 * @return {@link #flp}
	 */
	public FormLocalizationProvider getFlp() {
		return flp;
	}
}