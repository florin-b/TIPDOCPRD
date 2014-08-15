package tiparire.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.border.Border;

import tiparire.model.Database;
import tiparire.model.EnumDepartamente;
import tiparire.model.TipDocumentAfisat;
import tiparire.model.UserInfo;

@SuppressWarnings("serial")
public class DepartamentDialog extends JDialog implements DataListener {

	private JButton okButton;
	private JButton cancelButton;
	private JList<Object> departamentList;
	private DepartamentListener departamentListener;

	Database db;

	public DepartamentDialog(JFrame parent) {
		super(parent, "Selectie departament", false);

		okButton = new JButton("Ok");
		cancelButton = new JButton("Inchide");

		db = new Database(parent);
		db.setDataListener(this);

		departamentList = new JList<Object>(EnumDepartamente.values());
		departamentList.setMinimumSize(new Dimension(100, 100));
		departamentList.setSize(new Dimension(100, 100));

		setMinimumSize(new Dimension(220, 220));
		setSize(220, 220);
		setModal(true);

		pack();
		setLocationRelativeTo(parent);
		this.getRootPane().setDefaultButton(okButton);

		addLayoutComponents();

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				UserInfo.getInstance().setDepart(departamentList.getSelectedValue().toString());
				db.setDataListener(DepartamentDialog.this);
				
				
				if (TipDocumentAfisat.getInstance().isNetiparit())
					db.getDocumenteNetiparite();
				else
					db.getDocumenteTiparite(TipDocumentAfisat.getInstance().getDataTiparire());

			}
		});

	}

	private void addLayoutComponents() {
		JPanel listPanel = new JPanel();
		JPanel buttonsPanel = new JPanel();

		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, 0);

		listPanel.setBorder(spaceBorder);
		buttonsPanel.setBorder(spaceBorder);

		listPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		listPanel.add(new JScrollPane(departamentList));

		buttonsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);

		Dimension btnSize = cancelButton.getPreferredSize();
		okButton.setPreferredSize(btnSize);

		setLayout(new BorderLayout());

		add(listPanel, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.SOUTH);
		pack();

	}

	public void setListener(DepartamentListener listener) {
		this.departamentListener = listener;
	}

	@Override
	public void dataReceived() {
		setVisible(false);
		departamentListener.departamentSelected(departamentList.getSelectedValue().toString(), db.getDocumente());

	}

}
