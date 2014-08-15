package tiparire.view;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import tiparire.model.Document;

public class MainTableCellRenderer implements TableCellRenderer {

	DocumentCellComponent documentComponent;

	public MainTableCellRenderer() {
		documentComponent = new DocumentCellComponent();
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Document document = (Document) value;
		documentComponent.displayData(document, isSelected, table);
		
		
		return documentComponent;
	}

}
