package tiparire.model;

public class Articol {

	private String documentId;
	private String client;
	private String emitere;
	private String cod;
	private String nume;
	private String cantitate;
	private String um;
	private String pozitie;

	public Articol() {

	}

	public Articol(String documentId, String client, String emitere, String cod, String nume, String cantitate,
			String um, String pozitie) {
		super();
		this.documentId = documentId;
		this.client = client;
		this.emitere = emitere;
		this.cod = cod;
		this.nume = nume;
		this.cantitate = cantitate;
		this.um = um;
		this.pozitie = pozitie;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getEmitere() {
		return emitere;
	}

	public void setEmitere(String emitere) {
		this.emitere = emitere;
	}

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public String getCantitate() {
		return cantitate;
	}

	public void setCantitate(String cantitate) {
		this.cantitate = cantitate;
	}

	public String getUm() {
		return um;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public String getPozitie() {
		return pozitie;
	}

	public void setPozitie(String pozitie) {
		this.pozitie = pozitie;
	}
	
	
	

}
