package tiparire.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

				builder.append("Client:");
				builder.append(addSpace("Client:", 15));
				builder.append(document.get(i).getClient());
				builder.append(System.getProperty("line.separator"));
				builder.append("Document:");
				builder.append(addSpace("Document:", 15));
				builder.append(document.get(i).getId());

				if (!document.get(i).getNumeSofer().trim().isEmpty()) {
					builder.append(System.getProperty("line.separator"));
					builder.append("Sofer:");
					builder.append(addSpace("Sofer:", 15));
					builder.append(document.get(i).getNumeSofer());
				}

				if (!document.get(i).getNrMasina().trim().isEmpty()) {
					builder.append(System.getProperty("line.separator"));
					builder.append("Masina:");
					builder.append(addSpace("Masina:", 15));
					builder.append(document.get(i).getNrMasina());
				}
				
				if (!document.get(i).getInfoStatus().trim().isEmpty()) {
					builder.append(System.getProperty("line.separator"));
					builder.append("Stare:");
					builder.append(addSpace("Stare:", 15));
					builder.append(document.get(i).getInfoStatus());
				}

				builder.append(System.getProperty("line.separator"));
				builder.append(System.getProperty("line.separator"));

				builder.append("Nr.");
				builder.append(addSpace("Nr.", 5));
				builder.append("Nume");
				builder.append(addSpace("Nume", 45));
				builder.append("Cod");
				builder.append(addSpace("Cod", 10));
				builder.append("Cant. initiala");
				builder.append(addSpace("Cant. initiala", 17));
				builder.append("Um");
				builder.append(addSpace("Um", 5));
				builder.append("Depozit");
				builder.append(addSpace("Depozit", 10));
				builder.append("Cant. modificata");
				builder.append(addSpace("Cant. modificata", 18));				
				builder.append("Modificari");
				builder.append(addSpace("Modificari", 10));
				builder.append(System.getProperty("line.separator"));
				builder.append("---------------------------------------------------------------------------------------------------------------------------");
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

						lineBuilder.append(addSpace(Database.articol.get(ii).getCantitate(), 14));
						lineBuilder.append(Database.articol.get(ii).getCantitate());
						lineBuilder.append(addSpace("i", 2));

						lineBuilder.append(Database.articol.get(ii).getUm());
						lineBuilder.append(addSpace(Database.articol.get(ii).getUm(), 7));

						lineBuilder.append(Database.articol.get(ii).getDepozit());
						lineBuilder.append(addSpace(Database.articol.get(ii).getDepozit(), 10));

						lineBuilder.append(addSpace(Database.articol.get(ii).getCantitateModificata(), 16));
						lineBuilder.append(Database.articol.get(ii).getCantitateModificata());
						lineBuilder.append(addSpace("i", 3));						
						
						lineBuilder.append(Database.articol.get(ii).getModificare());
						lineBuilder.append(addSpace(Database.articol.get(ii).getModificare(), 10));
						
						lineBuilder.append(System.getProperty("line.separator"));

						builder.append(lineBuilder);
						lineBuilder.setLength(0);

					}

				}
				builder.append("---------------------------------------------------------------------------------------------------------------------------");
				builder.append(System.getProperty("line.separator"));
				builder.append(System.getProperty("line.separator"));

			}

		}

		documentString = builder.toString();

		sendToMyPrinter();

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

		}

	}

	private void sendToMyPrinter() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setPrintable(new PrintableString());
		if (pj.printDialog()) {
			try {
				pj.print();
				addDocumentTiparitToDb();
			} catch (PrinterException e) {
				System.out.println(e);
			}
		}
	}

	class PrintableString implements Printable {

		int[] pageBreaks;
		List<String> textLines;

		private void loadData() throws IOException {
			String thisLine = null;

			BufferedReader buf = new BufferedReader(new StringReader(documentString));

			textLines = new ArrayList<>();

			while ((thisLine = buf.readLine()) != null) {
				textLines.add(thisLine);
			}
		}

		public int print(Graphics g, PageFormat pf, int pageIndex) {

			Font font = new Font("MONOSPACED", Font.PLAIN, 10);

			Graphics2D g2 = (Graphics2D) g;
			g2.setFont(font);
			g2.setPaint(Color.black);

			if (pageBreaks == null) {
				try {
					loadData();

					int linesPerPage = 75;

					int numBreaks = (textLines.size() - 1) / linesPerPage;
					pageBreaks = new int[numBreaks];
					for (int b = 0; b < numBreaks; b++) {
						pageBreaks[b] = (b + 1) * linesPerPage;
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (pageBreaks != null && pageIndex > pageBreaks.length) {
				return NO_SUCH_PAGE;
			}

			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(pf.getImageableX(), pf.getImageableY());

			int startYAlign = 40;
			int start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];
			int end = (pageIndex == pageBreaks.length) ? textLines.size() : pageBreaks[pageIndex];

			int currentPage = pageIndex;
			g.drawString("Pagina " + String.valueOf(++currentPage), 530, 40);
			for (int line = start; line < end; line++) {

				g.drawString(textLines.get(line), 40, startYAlign);
				startYAlign += 10;
			}

			return PAGE_EXISTS;

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
