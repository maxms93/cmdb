package model;

import cmdb.CmdbController;

public class ApplicationSoftware extends CI {

	private int linesOfCode;

	public ApplicationSoftware(String bezeichnung, int linesOfCode) {
		super("ApplicationSoftware", bezeichnung);
		this.linesOfCode = linesOfCode;
	}

	public ApplicationSoftware() {
		// TODO Auto-generated constructor stub
	}

	public int getLinesOfCode() {
		return linesOfCode;
	}

	public void setLinesOfCode(int linesOfCode) {
		this.linesOfCode = linesOfCode;
	}

	@Override
	public String toString() {
		return super.toString() + "|" + getBezeichnung() + "|" + getLinesOfCode();
	}

	@Override
	public String insertCI() {
		return CmdbController.propertyPrefix + CmdbController.ontologyPrefix
				+ "INSERT DATA\n{\n<http://artmayr.com/resource/" + getType() +  "/" +  getId()+ "> "
				/* + "prop:type \"" + getType() + "\" ;\n" */
				+ "prop:name \"" + getBezeichnung() + "\" ;\n"
				+ "prop:linesOfCode \"" + getLinesOfCode() + "\" .\n}";
	}

	@Override
	public String appendCItoCI(String type, int id) {
		return CmdbController.propertyPrefix + CmdbController.ontologyPrefix
				+ "INSERT DATA\n{\n<http://artmayr.com/resource/" + type + "/" + id + ">" + "prop:isUsing \""
				+ getType() + "/" + getId() + "\" .\n}";
	}
}
