package mirna.beans;

public class SmallMolecule extends EnvironmentalFactor {

	private String fda;
	private String db;
	private String cid;

	public SmallMolecule() { }

	public SmallMolecule(int pk, String name, String fda, String db, String cid) {
		super(pk, name);
		this.fda = fda;
		this.db = db;
		this.cid = cid;
	}

	public String getFda() {
		return fda;
	}

	public void setFda(String fda) {
		this.fda = fda;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public int checkConflict(SmallMolecule smallMolecule) {
		int res = 0;
		if (this.getName()!=null) {
			if (smallMolecule.getName()==null) res++; 
			else if (!this.getName().equals(smallMolecule.getName())) return -1;
		}
		if (this.getCid()!=null) {
			if (smallMolecule.getCid()==null) res++; 
			else if (!this.getCid().equals(smallMolecule.getCid())) return -1;
		}
		if (this.getDb()!=null) {
			if (smallMolecule.getDb()==null) res++; 
			else if (!this.getDb().equals(smallMolecule.getDb())) return -1;
		}
		if (this.getFda()!=null) {
			if (smallMolecule.getFda()==null) res++; 
			else if (!this.getFda().equals(smallMolecule.getFda())) return -1;
		}
		return res;
	}

	@Override
	public String toString() {
		return "SmallMolecule [fda=" + fda + ", db=" + db + ", cid=" + cid
				+ ", name=" + name + ", pk=" + pk + "]";
	}
	
}