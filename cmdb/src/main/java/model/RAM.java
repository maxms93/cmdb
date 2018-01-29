package model;

import cmdb.CmdbController;

public class RAM extends CI {

	private String groesse;
	private String taktung;
	
	public RAM(String bezeichnung, String groesse, String taktung) {
		super("RAM", bezeichnung);
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

	@Override
	public String insertCI() {
		return  CmdbController.propertyPrefix +
	            CmdbController.ontologyPrefix +
	            "INSERT DATA\n{\n<http://artmayr.com/resource/" + getType() +  "/" +  getId()+ "> " +
	            /*"prop:type \"" + getType() + "\" ;\n" +*/
	            "prop:name \"" + getBezeichnung() + "\" ;\n" +
	            "prop:groesse \"" + getGroesse() + "\" ;\n" +
	            "prop:taktung \"" + getTaktung() + "\" .\n}";
	}
	
	@Override
	public String appendCItoCI(String type, int id) {
		return  CmdbController.propertyPrefix +
                CmdbController.ontologyPrefix +
                "INSERT DATA\n{\n<http://artmayr.com/resource/" + type + "/" + id+ ">" +
                "prop:isUsing \"" + getType() + "/" + getId() + "\" .\n}";
	}
}
