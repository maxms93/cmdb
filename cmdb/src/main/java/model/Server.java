package model;

import java.util.ArrayList;

public class Server extends CI {

	private boolean isVirtualized;
	private boolean isSharedServer;
	private ArrayList<CI> listComponents;
	
	public Server(int id, String bezeichnung, boolean isVirtualized, boolean isSharedServer, ArrayList<CI> listComponents) {
		super(id, "Server", bezeichnung);
		this.isVirtualized = isVirtualized;
		this.isSharedServer = isSharedServer;
		this.listComponents = listComponents;
	}

	public Server() {}
	
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
}
