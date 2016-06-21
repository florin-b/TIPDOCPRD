package tiparire.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;

import tiparire.model.Articol;
import tiparire.model.Document;

@SuppressWarnings("serial")
public class HeaderDocPanel extends JPanel {

	private JLabel nrDocument;
	private JLabel client;
	private JLabel dataEmitere;

	private JPanel headerPanel;
	private JPanel artPanel;
	private JLabel rowNumLabel;

	private JTable artTable;
	private ArticolTableModel artTableModel;

	private JButton marfaPregButton;

	public HeaderDocPanel(Document document) {

		artPanel = new JPanel();
		artPanel.setLayout(new BorderLayout());

		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);

		artTableModel = new ArticolTableModel();

		artTable = new JTable(artTableModel);
		artTable.setRowSelectionAllowed(false);

		artTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		artTable.getColumnModel().getColumn(1).setPreferredWidth(400);
		artTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		artTable.getColumnModel().getColumn(3).setPreferredWidth(70);
		artTable.getColumnModel().getColumn(4).setPreferredWidth(50);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		artTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
		artTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		artTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
		artTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

		artTable.setBorder(BorderFactory.createEtchedBorder());

		artPanel.add(new JScrollPane(artTable), BorderLayout.CENTER);
		artPanel.setPreferredSize(new Dimension(650, 210));

		artTable.setFont(new Font("Courier New", Font.PLAIN, 13));

		artTable.setBackground(new Color(253, 252, 220));
		artTable.setForeground(new Color(0, 100, 0));
		artTable.setRowHeight(20);

		headerPanel = new JPanel();

		headerPanel.setLayout(new GridBagLayout());

		nrDocument = new JLabel();
		nrDocument.setFont(new Font("Arial", Font.PLAIN, 15));

		marfaPregButton = new JButton();
		marfaPregButton.setFont(new Font("Arial", Font.PLAIN, 15));
		marfaPregButton.setText("Marfa pregatita");

		marfaPregButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "123");
			}
		});

		client = new JLabel();
		client.setFont(new Font("Arial", Font.BOLD, 18));
		client.setForeground(new Color(126, 166, 126));

		dataEmitere = new JLabel();
		dataEmitere.setFont(new Font("Helvetica", Font.PLAIN, 14));
		dataEmitere.setForeground(new Color(97, 128, 97));

		rowNumLabel = new JLabel();

		setLayout(new BorderLayout());

		GridBagConstraints gc = new GridBagConstraints();

		// //////////////////////////////////////////

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.1;

		gc.gridy = 1;
		gc.gridx = 1;
		gc.insets = new Insets(0, 20, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		headerPanel.add(client, gc);

		// //////////////////////////////////////////

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 1;
		gc.insets = new Insets(0, 20, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		headerPanel.add(dataEmitere, gc);

		// //////////////////////////////////////////

		gc.gridy++;

		gc.weightx = 1;
		gc.weighty = 0.2;

		gc.gridx = 1;
		gc.insets = new Insets(2, 20, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		headerPanel.add(nrDocument, gc);

		int spaceCount = 5;
		Border spaceCountBorder = BorderFactory.createEmptyBorder(spaceCount, spaceCount, spaceCount, spaceCount);
		headerPanel.setBorder(spaceCountBorder);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(marfaPregButton);
		buttonPanel.setBorder(spaceCountBorder);

		JPanel headerContainer = new JPanel();
		headerContainer.setLayout(new BorderLayout());

		rowNumLabel.setFont(new Font("Courier", Font.BOLD, 30));
		rowNumLabel.setForeground(new Color(162, 181, 205));

		headerContainer.add(rowNumLabel, BorderLayout.WEST);
		headerContainer.add(headerPanel, BorderLayout.CENTER);

		headerContainer.setBorder(spaceBorder);

		add(headerContainer, BorderLayout.NORTH);
		add(artPanel, BorderLayout.CENTER);

	}

	public void setClient(String clientText) {
		client.setText(clientText);
	}

	public void setDocument(String documentText) {
		nrDocument.setText(documentText);

	}

	public void setEmitere(String emitereText) {
		dataEmitere.setText(emitereText);
	}

	public void setRownum(String rownum) {
		rowNumLabel.setText(rownum);
	}

	public void setArticolData(List<Articol> articol) {
		artTableModel.setData(articol);
		artTableModel.fireTableDataChanged();

	}

}
