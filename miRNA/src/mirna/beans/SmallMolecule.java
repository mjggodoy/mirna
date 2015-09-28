package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "small_molecule")

public class SmallMolecule extends ModelClass {

	
	@Column(name = "fda", nullable = true, length = 300)
	private String fda;
	
	@Column(name = "db", nullable = true, length = 300)
	private String db;
	
	@Column(name = "cid", nullable = true, length = 300)
	private String cid;
	
	@Column(name = "environmental_factor_pk", nullable = false, length = 20)
	private Integer environmental_factor_pk;
	

	public SmallMolecule() { }

	
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
	
	
	public Integer getEnvironmental_factor_pk() {
		return environmental_factor_pk;
	}


	public void setEnvironmental_factor_pk(Integer environmental_factor_pk) {
		this.environmental_factor_pk = environmental_factor_pk;
	}



	public int checkConflict(SmallMolecule smallmolecule) {
		int res = 0;
		
		if (this.pk!=null) {
			if (smallmolecule.getPk()==null) res++; // res = 1
			else if (!this.pk.equals(smallmolecule.getPk())) return -1;
		}
		
		if (this.fda!=null) {
			if (smallmolecule.getFda()==null) res++; // res = 1
			else if (!this.fda.equals(smallmolecule.getFda())) return -1;
		}
		
		if (this.cid!=null) {
			if (smallmolecule.getCid()==null) res++; // res = 1
			else if (!this.cid.equals(smallmolecule.getCid())) return -1;
		}
		
		if (this.db!=null) {
			if (smallmolecule.getDb()==null) res++; // res = 1
			else if (!this.db.equals(smallmolecule.getDb())) return -1;
		}
		
		if (this.environmental_factor_pk!=null) {
			if (smallmolecule.getEnvironmental_factor_pk()==null) res++; // res = 1
			else if (!this.environmental_factor_pk.equals(smallmolecule.getEnvironmental_factor_pk())) return -1;
		}
		
		return res;
	}
	
	
	public void update(SmallMolecule smallmolecule) throws ConflictException {
		this.update(smallmolecule, true);
	}
	
	
	public void update(SmallMolecule smallmolecule, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(smallmolecule)==-1) throw new ConflictException(this, smallmolecule);
		}
		if (smallmolecule.getPk()!=null) this.pk = smallmolecule.getPk();
		if (smallmolecule.getFda()!=null) this.fda = smallmolecule.getFda();
		if (smallmolecule.getCid()!=null) this.cid = smallmolecule.getCid();
		if (smallmolecule.getDb()!=null) this.db = smallmolecule.getDb();
		if (smallmolecule.getEnvironmental_factor_pk()!=null) this.environmental_factor_pk = smallmolecule.getEnvironmental_factor_pk();


		
	}


	@Override
	public String toString() {
		return "SmallMolecule [fda=" + fda + ", db=" + db + ", cid=" + cid
				+ ", environmental_factor_pk=" + environmental_factor_pk
				+ ", pk=" + pk + "]";
	}

	

	
	
	
}