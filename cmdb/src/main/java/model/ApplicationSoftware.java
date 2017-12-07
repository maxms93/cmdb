package model;

public class ApplicationSoftware extends CI {

	private int linesOfCode;
	
	public ApplicationSoftware(int id, String bezeichnung, int linesOfCode) {
		super(id, "ApplicationSoftware", bezeichnung);
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
		return super.toString() + "|" + getBezeichnung()  + "|" + getLinesOfCode();
	}
}
