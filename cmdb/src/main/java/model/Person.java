package model;

import java.util.ArrayList;
import java.util.HashMap;

import cmdb.CmdbController;

public class Person extends CI {

	private String vorname;
	private String nachname;
	private ArrayList<CI> listUsage;

	public Person(String bezeichnung, String vorname, String nachname, ArrayList<CI> listUsage) {
		super("Person", bezeichnung);
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
		return super.toString() + "|" + getBezeichnung() + "|" + getVorname() + "|" + getNachname();
	}

	@Override
	public String insertCI() {
		return  CmdbController.propertyPrefix + 
				CmdbController.ontologyPrefix +
				"INSERT DATA\n{\n<http://artmayr.com/resource/" + getType() +  "/" +  getId()+ "> " +
				/* "prop:type \"" + getType() + "\" ;\n" + */
				"prop:bezeichnung \"" + getBezeichnung() + "\" ;\n" +
				"prop:vorname \"" + getVorname() + "\" ;\n" +
				"prop:nachname \"" + getNachname() + "\".\n}";
	}

	@Override
	public String appendCItoCI(String type, int id) {
		return null;
	}

}
