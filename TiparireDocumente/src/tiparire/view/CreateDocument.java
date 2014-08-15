package tiparire.view;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import tiparire.model.Articol;
import tiparire.model.Database;
import tiparire.model.Document;
import tiparire.model.UserInfo;
import tiparire.model.WebService;

public class CreateDocument {

	List<Document> document;
	List<Document> documentTiparit;
	List<Articol> articol;

	String documentString;

	PrintListener printListener;

	public CreateDocument() {
	}

	public void setDocumente(List<Document> document) {
		this.document = document;
	}

	public void printDocument() {
		documentString = "";
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = new Date();

		StringBuilder builder = new StringBuilder();
		builder.append("Gestionar:");
		builder.append(addSpace("Gestionar:", 15));
		builder.append(UserInfo.getInstance().getNume());
		builder.append(System.getProperty("line.separator"));

		builder.append("Data:");
		builder.append(addSpace("Data:", 15));
		builder.append(dateFormat.format(date));
		builder.append(System.getProperty("line.separator"));
		builder.append(System.getProperty("line.separator"));

		documentTiparit = new LinkedList<Document>();

		for (int i = 0; i < document.size(); i++) {

			if (document.get(i).getSeTipareste().equals("1")) {

				documentTiparit.add(document.get(i));

				builder.append("Client: " + document.get(i).getClient());

				builder.append(System.getProperty("line.separator"));
				builder.append(System.getProperty("line.separator"));

				builder.append("Nr.");
				builder.append(addSpace("Nr.", 5));
				builder.append("Nume");
				builder.append(addSpace("Nume", 45));
				builder.append("Cod");
				builder.append(addSpace("Cod", 10));
				builder.append("Cantitate");
				builder.append(addSpace("Cantitate", 10));
				builder.append("Um");
				builder.append(addSpace("Um", 5));
				builder.append(System.getProperty("line.separator"));
				builder.append("-------------------------------------------------------------------------");
				builder.append(System.getProperty("line.separator"));

				StringBuilder lineBuilder = new StringBuilder();

				for (int ii = 0; ii < Database.articol.size(); ii++) {

					if (document.get(i).getId().equals(Database.articol.get(ii).getDocumentId())) {

						lineBuilder.append(Database.articol.get(ii).getPozitie() + ".");
						lineBuilder.append(addSpace(Database.articol.get(ii).getPozitie() + ".", 5));

						lineBuilder.append(Database.articol.get(ii).getNume());
						lineBuilder.append(addSpace(Database.articol.get(ii).getNume(), 45));

						lineBuilder.append(Database.articol.get(ii).getCod());
						lineBuilder.append(addSpace(Database.articol.get(ii).getCod(), 10));

						lineBuilder.append(addSpace(Database.articol.get(ii).getCantitate(), 9));
						lineBuilder.append(Database.articol.get(ii).getCantitate());
						lineBuilder.append(addSpace("i", 2));

						lineBuilder.append(Database.articol.get(ii).getUm());
						lineBuilder.append(addSpace(Database.articol.get(ii).getUm(), 5));

						lineBuilder.append(System.getProperty("line.separator"));

						builder.append(lineBuilder);
						lineBuilder.setLength(0);

					}

				}
				builder.append("-------------------------------------------------------------------------");
				builder.append(System.getProperty("line.separator"));
				builder.append(System.getProperty("line.separator"));

			}

		}

		documentString = builder.toString();

		addDocumentTiparitToDb();

		if (printListener != null) {
			printListener.printFinished();
		}

	}

	private void addDocumentTiparitToDb() {

		if (documentTiparit.size() > 0) {
			WebService service = new WebService();

			try {
				try {
					service.addDocumentTiparit(documentTiparit);
				} catch (IOException | XmlPullParserException e) {
					e.printStackTrace();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			sendToPrinter();
		}

	}

	private void sendToPrinter() {
		InputStream in;
		try {

			in = new ByteArrayInputStream(documentString.getBytes("UTF8"));

			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
			AttributeSet attributeSet = new HashAttributeSet();
			attributeSet.add(new PrinterName("NPI8DA48A", null));
			PrintService service = PrintServiceLookup.lookupDefaultPrintService();

			DocPrintJob job = service.createPrintJob();
			Doc doc = new SimpleDoc(in, flavor, null);
			PrintJobWatcher watcher = new PrintJobWatcher(job);
			try {
				job.print(doc, null);
			} catch (PrintException e) {
				e.printStackTrace();
			}
			watcher.waitForDone();

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

	}

	private String addSpace(String text, int spaceLen) {
		String spaceString = "";

		for (int ii = 0; ii < spaceLen - text.length(); ii++) {
			spaceString += " ";
		}

		return spaceString;
	}

	static class PrintJobWatcher {
		boolean done = false;

		PrintJobWatcher(DocPrintJob job) {
			job.addPrintJobListener(new PrintJobAdapter() {
				public void printJobCanceled(PrintJobEvent pje) {
					allDone();
				}

				public void printJobCompleted(PrintJobEvent pje) {
					allDone();
				}

				public void printJobFailed(PrintJobEvent pje) {
					allDone();
				}

				public void printJobNoMoreEvents(PrintJobEvent pje) {
					allDone();
				}

				void allDone() {
					synchronized (PrintJobWatcher.this) {
						done = true;
						PrintJobWatcher.this.notify();
					}
				}
			});
		}

		public synchronized void waitForDone() {
			try {
				while (!done) {
					wait();
				}
			} catch (InterruptedException e) {
			}
		}
	}

	public void setPrintListener(PrintListener printListener) {
		this.printListener = printListener;
	}

}
