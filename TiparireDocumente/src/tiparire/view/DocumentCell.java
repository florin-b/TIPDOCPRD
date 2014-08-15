package tiparire.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import tiparire.model.Articol;
import tiparire.model.Database;
import tiparire.model.Document;
import tiparire.model.Utils;

@SuppressWarnings("serial")
public class DocumentCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
	JPanel mainPanel;

	JButton printButton;
	JLabel imageLabel;

	JPanel buttonPanel;
	HeaderDocPanel docPanel;

	Document document;
	JSplitPane splitPanel;

	public DocumentCell() {

		printButton = new JButton("Selecteaza");
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fireEditingStopped();

				if (document.getSeTipareste().equals("1"))
					document.setSeTipareste("0");
				else
					document.setSeTipareste("1");

			}
		});

		Dimension printDim = new Dimension(140, 30);
		printButton.setPreferredSize(printDim);
		printButton.setSize(printDim);
		printButton.setFont(new Font("Helvetica", Font.PLAIN, 14));

		int space = 15;
		Border spaceBorder = BorderFactory.createEmptyBorder(space, space, space, space);

		ClassLoader cl = this.getClass().getClassLoader();
		imageLabel = new JLabel(Utils.createIcon(cl.getResource("images/print_icon.png")));

		buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(printButton, BorderLayout.NORTH);
		buttonPanel.add(imageLabel, BorderLayout.CENTER);

		imageLabel.setVisible(false);

		buttonPanel.setBorder(spaceBorder);

		docPanel = new HeaderDocPanel(document);

		mainPanel = new JPanel(new BorderLayout());

		splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, docPanel, buttonPanel);
		splitPanel.setDividerLocation(0.95);
		splitPanel.setResizeWeight(0.95);
		splitPanel.setEnabled(false);

		// mainPanel.add(docPanel, BorderLayout.WEST);
		// mainPanel.add(buttonPanel, BorderLayout.EAST);

		mainPanel.add(splitPanel, BorderLayout.CENTER);

	}

	private void updateData(Document document, boolean isSelected, JTable table, int row) {
		this.document = document;

		String print = document.getSeTipareste();
		if (print.equals("1")) {
			imageLabel.setVisible(true);
			ClassLoader cl = this.getClass().getClassLoader();
			printButton.setIcon(Utils.createIcon(cl.getResource("images/select_24.png")));
		} else {
			imageLabel.setVisible(false);
			printButton.setIcon(null);
			printButton.revalidate();
		}

		docPanel.setDocument(document.getId());
		docPanel.setClient(document.getClient());
		docPanel.setEmitere(document.getDataEmiterii());
		docPanel.setRownum(String.valueOf(row + 1));

		List<Articol> tempArticol = new LinkedList<Articol>();

		for (int ii = 0; ii < Database.articol.size(); ii++) {
			if (Database.articol.get(ii).getDocumentId().equals(document.getId())) {
				tempArticol.add(Database.articol.get(ii));
			}
		}

		docPanel.setArticolData(tempArticol);

	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		Document document = (Document) value;
		updateData(document, true, table, row);
		return mainPanel;
	}

	public Object getCellEditorValue() {
		return null;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Document document = (Document) value;

		updateData(document, isSelected, table, row);
		return mainPanel;
	}


}
