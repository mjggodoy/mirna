package mirna.beans;

public class SmallMolecule extends EnvironmentalFactor {

	private String FDA;
	private String DB;
	private String CID;
	private String name;

	public SmallMolecule() {
		super();
	}
	
	
	
	

	public SmallMolecule(int pk, String fDA, String dB, String cID, String name) {
		super(pk);
		FDA = fDA;
		DB = dB;
		CID = cID;
		this.name = name;
	}





	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFDA() {
		return FDA;
	}

	public void setFDA(String FDA) {
		this.FDA = FDA;
	}

	public String getDB() {
		return DB;
	}

	public void setDB(String DB) {
		this.DB = DB;
	}

	public String getCID() {
		return CID;
	}

	public void setCID(String CID) {
		this.CID = CID;
	}

}