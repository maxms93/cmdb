package model;

import java.util.ArrayList;

import cmdb.CmdbController;

public class Server extends CI {

	private boolean isVirtualized;
	private boolean isSharedServer;
	private ArrayList<CI> listComponents;
	
	public Server(String bezeichnung, boolean isVirtualized, boolean isSharedServer, ArrayList<CI> listComponents) {
		super("Server", bezeichnung);
		this.isVirtualized = isVirtualized;
		this.isSharedServer = isSharedServer;
		this.listComponents = listComponents;
	}

	public Server() {}
	
	public Server(String bezeichnung, boolean isVirtualized, boolean isSharedServer) {
		super("Server", bezeichnung);
		this.isVirtualized = isVirtualized;
		this.isSharedServer = isSharedServer;
		listComponents = new ArrayList<CI>();
	}

	public boolean isVirtualized() {
		return isVirtualized;
	}

	public void setVirtualized(boolean isVirtualized) {
		this.isVirtualized = isVirtualized;
	}

	public boolean isSharedServer() {
		return isSharedServer;
	}

	public void setSharedServer(boolean isSharedServer) {
		this.isSharedServer = isSharedServer;
	}

	public ArrayList<CI> getListComponents() {
		return listComponents;
	}

	public void setListComponents(ArrayList<CI> listComponents) {
		this.listComponents = listComponents;
	}
	
	@Override
	public String toString() {
		return super.toString() + "|" + getBezeichnung()  + "|" + isVirtualized()  + "|" + isSharedServer();
	}
	
	@Override
	public String insertCI()
	{
		return  CmdbController.propertyPrefix +
                CmdbController.ontologyPrefix +
                "INSERT DATA\n{\n<http://artmayr.com/resource/" + getType() +  "/" +  getId()+ "> " +
                /*"prop:type \"" + getType() + "\" ;\n" + */
                "prop:name \"" + getBezeichnung() + "\" ;\n" +
                "prop:isVirtualized \"" + isVirtualized() + "\" ;\n" +
                "prop:isSharedServer \"" + isSharedServer() + "\".\n}";
	}

	@Override
	public String appendCItoCI(String name, int id) {
		return null;
	}
}
