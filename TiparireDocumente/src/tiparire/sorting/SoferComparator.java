package tiparire.sorting;

import java.util.Comparator;

import tiparire.model.Document;

public class SoferComparator implements Comparator<Document> {

	@Override
	public int compare(Document d1, Document d2) {

		return d1.getNumeSofer().compareTo(d2.getNumeSofer());
	}

}
