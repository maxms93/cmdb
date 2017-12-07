package model;

import java.util.ArrayList;

public class Person extends CI {

	private String vorname;
	private String nachname;
	private ArrayList<CI> listUsage;
	
	public Person(int id, String bezeichnung, String vorname, String nachname, ArrayList<CI> listUsage) {
		super(id, "Person", bezeichnung);
		this.vorname = vorname;
		this.nachname = nachname;
		this.listUsage = listUsage;
	}

	public Person() {
		// TODO Auto-generated constructor stub
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public ArrayList<CI> getListUsage() {
		return listUsage;
	}

	public void setListUsage(ArrayList<CI> listUsage) {
		this.listUsage = listUsage;
	}
	
	@Override
	public String toString() {
		return super.toString() + "|" + getBezeichnung()  + "|" + getVorname()  + "|" + getNachname();
	}
}
