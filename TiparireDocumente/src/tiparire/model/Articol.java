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
	private boolean isPregatit;
	private boolean isTiparit;
	private String tip;
	private String depozit;

	public Articol() {

	}

	public Articol(String documentId, String client, String emitere, String cod, String nume, String cantitate,
			String um, String pozitie, boolean isPregatit, boolean isTiparit, String tip, String depozit) {
		super();
		this.documentId = documentId;
		this.client = client;
		this.emitere = emitere;
		this.cod = cod;
		this.nume = nume;
		this.cantitate = cantitate;
		this.um = um;
		this.pozitie = pozitie;
		this.isPregatit = isPregatit;
		this.isTiparit = isTiparit;
		this.tip = tip;
		this.depozit = depozit;
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

	public boolean isPregatit() {
		return isPregatit;
	}

	public void setPregatit(boolean isPregatit) {
		this.isPregatit = isPregatit;
	}

	public boolean isTiparit() {
		return isTiparit;
	}

	public void setTiparit(boolean isTiparit) {
		this.isTiparit = isTiparit;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getDepozit() {
		return depozit;
	}

	public void setDepozit(String depozit) {
		this.depozit = depozit;
	}

	
}
