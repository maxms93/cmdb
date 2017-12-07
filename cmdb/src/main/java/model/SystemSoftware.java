package model;

public class SystemSoftware extends CI {

	private boolean isOS;
	
	public SystemSoftware(int id, String bezeichnung, boolean isOS) {
		super(id, "SystemSoftware", bezeichnung);
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
}
