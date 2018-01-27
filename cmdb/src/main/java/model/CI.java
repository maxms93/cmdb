package model;

import java.io.Serializable;

public abstract class CI implements Serializable {

	private int id;
	private String bezeichnung;
	private String type;

	public CI(String type, String bezeichnung) {
		super();
		this.id = IDHelper.getID();
		this.bezeichnung = bezeichnung;
		//this.type = type;
		this.type = this.getClass().getSimpleName();
	}

	public CI() {
	}

	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}
	

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "" + getType() + "|" + getId();
	}

	public abstract String insertCI();

	public abstract String appendCItoCI(String type, int id);
}
