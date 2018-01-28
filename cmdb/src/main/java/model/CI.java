package model;

import java.io.Serializable;

public abstract class CI implements Serializable {

	private int id;
	private String bezeichnung;
	private String type;
	private String name;

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
	
	public String getName() {
		if (name == null || name == "")
			return bezeichnung;
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		this.bezeichnung = name;
	}

	public String getBezeichnung() {
		if (bezeichnung == null || bezeichnung == "")
			return name;
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
		this.name = bezeichnung;
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
