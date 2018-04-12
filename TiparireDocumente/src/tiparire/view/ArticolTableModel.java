package tiparire.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import tiparire.model.Articol;

@SuppressWarnings("serial")
public class ArticolTableModel extends AbstractTableModel {

	List<Articol> articol;

	
	public ArticolTableModel()
	{
		
	}
	
	private String[] colNames = { "Nr.", "Nume articol", "Cod articol", "Cantitate", "Um", "Depozit" };

	public int getColumnCount() {
		return 6;
	}

	public int getRowCount() {
		return (articol == null) ? 0 : articol.size();
	}

	public void setData(List<Articol> articol)
	{
		this.articol = articol;
	}
	
	
	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	@Override
	public Object getValueAt(int row, int col) {

		Articol art = articol.get(row);

		switch (col) {
		case 0:
			return art.getPozitie();
		case 1:
			return art.getNume();
		case 2:
			return art.getCod();
		case 3:
			return art.getCantitate();
		case 4:
			return art.getUm();
		case 5:
			return art.getDepozit();
		}

		return null;

	}
}
