package model;

import java.util.ArrayList;

public class Server extends CI {

	private boolean isVirtualized;
	private boolean isSharedServer;
	private ArrayList<CI> lComponents;
	
	public Server(int id, String bezeichnung, boolean isVirtualized, boolean isSharedServer, ArrayList<CI> lComponents) {
		super(id, "Server", bezeichnung);
		this.isVirtualized = isVirtualized;
		this.isSharedServer = isSharedServer;
		this.lComponents = lComponents;
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

	public ArrayList<CI> getlComponents() {
		return lComponents;
	}

	public void setlComponents(ArrayList<CI> lComponents) {
		this.lComponents = lComponents;
	}
}
