package model;

import java.util.ArrayList;

import cmdb.CmdbController;

public class PC extends CI {

	private boolean isThinclient;
	private ArrayList<CI> listComponents;
	
	public PC(int id){
		super(id);
	}
	
	public PC(String bezeichnung, boolean isThinclient, ArrayList<CI> listComponents) {
		super("PC", bezeichnung);
		this.isThinclient = isThinclient;
		this.listComponents = listComponents;
	}

	public PC() {}
	
	public PC(String bezeichnung, boolean isThinclient) {
		super("PC", bezeichnung);
		this.isThinclient = isThinclient;
		listComponents = new ArrayList<CI>();
	}

	public boolean isThinclient() {
		return isThinclient;
	}

	public void setThinclient(boolean isThinclient) {
		this.isThinclient = isThinclient;
	}

	public ArrayList<CI> getListComponents() {
		return listComponents;
	}

	public void setListComponents(ArrayList<CI> listComponents) {
		this.listComponents = listComponents;
	}
	
	@Override
	public String toString() {
		return super.toString() + "|" + getBezeichnung()  + "|" + isThinclient();
	}

	@Override
	public String insertCI()
	{
		return  CmdbController.propertyPrefix +
                CmdbController.ontologyPrefix +
                "INSERT DATA\n{\n<http://artmayr.com/resource/" + getType() +  "/" +  getId() + "> " +
                /*"prop:type \"" + getType() + "\" ;\n" + */
                "prop:name \"" + getBezeichnung() + "\" ;\n" +
                "prop:isThinclient \"" + isThinclient() + "\" .\n}";
	}

	@Override
	public String appendCItoCI(String type, int id) {
		return CmdbController.propertyPrefix + CmdbController.ontologyPrefix
				+ "INSERT DATA\n{\n<http://artmayr.com/resource/" + type + "/" + id + ">" + "prop:isUsing \""
				+ getType() + "/" + getId() + "\" .\n}";
	}
}
