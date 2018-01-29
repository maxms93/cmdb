package model;

import cmdb.CmdbController;

public class Harddisk extends CI {

	private String groesse;
	private String schnittstelle;
	
	public Harddisk(int id){
		super(id);
	}
	
	public Harddisk(String bezeichnung, String groesse, String schnittstelle) {
		super("Harddisk", bezeichnung);
		this.groesse = groesse;
		this.schnittstelle = schnittstelle;
	}
	
	public Harddisk() {}

	public String getGroesse() {
		return groesse;
	}

	public void setGroesse(String groesse) {
		this.groesse = groesse;
	}

	public String getSchnittstelle() {
		return schnittstelle;
	}

	public void setSchnittstelle(String schnittstelle) {
		this.schnittstelle = schnittstelle;
	}
	
	@Override
	public String toString() {
		return super.toString() + "|" + getBezeichnung()  + "|" + getGroesse()  + "|" + getSchnittstelle();
	}

	@Override
	public String insertCI() {
		return  CmdbController.propertyPrefix +
	            CmdbController.ontologyPrefix +
	            "INSERT DATA\n{\n<http://artmayr.com/resource/" + getType() +  "/" +  getId()+ "> " +
	            /*"prop:type \"" + getType() + "\" ;\n" +*/
	            "prop:name \"" + getBezeichnung() + "\" ;\n" +
	            "prop:groesse \"" + getGroesse() + "\" ;\n" +
	            "prop:schnittstelle \"" + getSchnittstelle() + "\" .\n}";
	}
	
	@Override
	public String appendCItoCI(String type, int id) {
		return  CmdbController.propertyPrefix +
                CmdbController.ontologyPrefix +
                "INSERT DATA\n{\n<http://artmayr.com/resource/" + type + "/" + id+ ">" +
                "prop:isUsing \"" + getType() + "/" + getId() + "\" .\n}";
	}
}
