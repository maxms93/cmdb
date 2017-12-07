package model;

import java.io.Serializable;

public abstract class CI implements Serializable {

	private int id;
	private String bezeichnung;
	private String type;

	public CI(int id, String type, String bezeichnung) {
		super();
		this.id = id;
		this.bezeichnung = bezeichnung;
		this.type = type;
	}
	
	public CI() {}

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
}
