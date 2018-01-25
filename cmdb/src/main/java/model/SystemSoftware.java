package model;

import cmdb.CmdbController;

public class SystemSoftware extends CI {

	private boolean isOS;
	
	public SystemSoftware(String bezeichnung, boolean isOS) {
		super("SystemSoftware", bezeichnung);
		this.isOS = isOS;
	}

	public SystemSoftware() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isOS() {
		return isOS;
	}

	public void setOS(boolean isOS) {
		this.isOS = isOS;
	}

	@Override
	public String toString() {
		return super.toString() + "|" + getBezeichnung()  + "|" + isOS();
	}
	
	@Override
	public String insertCI() {
		return  CmdbController.propertyPrefix +
				CmdbController.ontologyPrefix +
				"INSERT DATA\n{\n<http://artmayr.com/resource/" + getType() +  "/" +  getId()+ "> " +
				/* "prop:type \"" + getType() + "\" ;\n" + */
				"prop:name \"" + getBezeichnung() + "\" ;\n" +
				"prop:os \"" + isOS() + "\" .\n}";
	}

	@Override
	public String appendCItoCI(String type, int id) {
		return CmdbController.propertyPrefix + CmdbController.ontologyPrefix
				+ "INSERT DATA\n{\n<http://artmayr.com/resource/" + type + "/" + id + ">"
				+ "prop:isUsing \"" + getType() + "/" + getId()
				+ "\" .\n}";
	}
}
