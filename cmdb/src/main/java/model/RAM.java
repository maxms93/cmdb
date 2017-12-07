package model;

public class RAM extends CI {

	private String groesse;
	private String taktung;
	
	public RAM(int id, String bezeichnung, String groesse, String taktung) {
		super(id, "RAM", bezeichnung);
		this.groesse = groesse;
		this.taktung = taktung;
	}
	
	public RAM() {}

	public String getGroesse() {
		return groesse;
	}

	public void setGroesse(String groesse) {
		this.groesse = groesse;
	}

	public String getTaktung() {
		return taktung;
	}

	public void setTaktung(String taktung) {
		this.taktung = taktung;
	}
	
	@Override
	public String toString() {
		return super.toString() + "|" + getBezeichnung()  + "|" + getGroesse()  + "|" + getTaktung();
	}
}
