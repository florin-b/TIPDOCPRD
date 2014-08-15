package tiparire.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import tiparire.model.Database;
import tiparire.model.Document;
import tiparire.model.EnumLogonStatus;
import tiparire.model.TipDocumentAfisat;
import tiparire.model.UserInfo;
import tiparire.model.Utils;

public class MainFrame extends JFrame implements LogonListener, DepartamentListener, DataListener, ToolbarListener,
		PrintListener, TipDocumentListener, SelectAllItemsListener {

	private static final long serialVersionUID = 1L;

	private LogonDialog logonDialog;
	private TablePanel tablePanel;
	private Toolbar toolbar;

	private DepartamentDialog departDialog;
	private TipDocumentDialog tipDocDialog;
	CreateDocument doc;
	Database db;

	public MainFrame() {

		super("Tiparire documente");
		setLayout(new BorderLayout());

		tablePanel = new TablePanel();

		toolbar = new Toolbar();

		db = new Database(MainFrame.this);
		db.setDataListener(this);

		doc = new CreateDocument();
		doc.setPrintListener(this);

		departDialog = new DepartamentDialog(MainFrame.this);
		departDialog.setListener(this);

		tipDocDialog = new TipDocumentDialog(MainFrame.this);
		tipDocDialog.setListener(this);

		toolbar.setToolbarListener(this);

		add(new JScrollPane(tablePanel), BorderLayout.CENTER);

		add(toolbar, BorderLayout.NORTH);

		logonDialog = new LogonDialog(this);
		logonDialog.setLogonListener(this);
		logonDialog.setVisible(true);

		setMinimumSize(new Dimension(900, 600));
		setSize(900, 600);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setVisible(true);
		setJMenuBar(createMenuBar());

	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu actionMenu = new JMenu("Actiuni");
		JMenu displayMenu = new JMenu("Afiseaza");

		JMenuItem departItem = new JMenuItem("Departament");
		JMenuItem exitItem = new JMenuItem("Iesire");

		actionMenu.add(departItem);
		actionMenu.add(exitItem);
		menuBar.add(actionMenu);
		menuBar.add(displayMenu);

		JMenuItem tipDocItem = new JMenuItem("Documente");
		displayMenu.add(tipDocItem);

		displayMenu.setMnemonic(KeyEvent.VK_F);

		departItem.setMnemonic(KeyEvent.VK_D);
		actionMenu.setMnemonic(KeyEvent.VK_A);
		exitItem.setMnemonic(KeyEvent.VK_X);

		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		departItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (UserInfo.getInstance().getInitDepart().equals("TOAT")) {
					departDialog.setVisible(true);
				}
			}
		});

		tipDocItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipDocDialog.setVisible(true);

			}
		});

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});

		return menuBar;
	}

	public void logonSucceeded() {

		if (UserInfo.getInstance().getLogonStatus().equals("Status_3")) {

			logonDialog.setVisible(false);

			TipDocumentAfisat.getInstance().setNetiparit(true);
			if (Utils.getDepartCode(UserInfo.getInstance().getDepart()).equals("00")) {
				departDialog.setVisible(true);
			} else {

				toolbar.setDepartamentString(Utils.getFullDepartName(UserInfo.getInstance().getDepart()));
				db.getDocumenteNetiparite();
			}

			setVisible(true);

			toolbar.setNumeGest(UserInfo.getInstance().getNume());
			toolbar.setTipDocument("Documente netiparite");

		} else {
			setVisible(false);

			String logStatus = EnumLogonStatus.valueOf(UserInfo.getInstance().getLogonStatus()).getStatusName();
			logonDialog.setLogonStatus(logStatus);
			logonDialog.setVisible(true);

		}

	}

	public void departamentSelected(String departament, List<Document> listDocumente) {

		toolbar.setDepartamentString(departament);

		tablePanel.setData(listDocumente);
		tablePanel.autoresizeTableRowHeight();
		db.setDocumente(listDocumente);

	}

	public void dataReceived() {
		tablePanel.setData(db.getDocumente());
		tablePanel.autoresizeTableRowHeight();

	}

	public void refreshEventOccured() {
		if (TipDocumentAfisat.getInstance().isNetiparit())
			db.getDocumenteNetiparite();

	}

	public void printEventOccured() {
		doc.setDocumente(db.getDocumente());
		doc.printDocument();
	}

	public void printFinished() {
		if (TipDocumentAfisat.getInstance().isNetiparit())
			db.getDocumenteNetiparite();
		else
			db.getDocumenteTiparite(TipDocumentAfisat.getInstance().getDataTiparire());

	}

	public void tipDocumentSelected(List<Document> listDocumente) {

		if (TipDocumentAfisat.getInstance().isNetiparit()) {
			toolbar.setTipDocument("Documente netiparite");
		} else {
			toolbar.setTipDocument("Documente tiparite");
		}

		tablePanel.setData(listDocumente);
		tablePanel.autoresizeTableRowHeight();
		db.setDocumente(listDocumente);

	}

	public void selectAllItems(boolean selectAll) {

		List<Document> documente = db.getDocumente();

		for (int i = 0; i < documente.size(); i++) {
			if (selectAll) {
				documente.get(i).setSeTipareste("1");
			} else {
				documente.get(i).setSeTipareste("0");
			}
		}

		dataReceived();

	}

}