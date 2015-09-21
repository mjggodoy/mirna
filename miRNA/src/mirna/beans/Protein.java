package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;



@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "protein")

public class Protein extends ModelClass {
	

	@Column(name = "uniprot_id", nullable = false, length = 400, unique = false)
	private String uniprot_id;
	
	@Column(name = "type", nullable = true, length = 400, unique = false)
	private String type;
	
	
	public Protein() {
		super();	
	}
	

	public Protein(int pk, String uniprot_id, String type) {
		super(pk);
		this.uniprot_id = uniprot_id;
		this.type = type;
		
	}

	

	public String getUniprot_id() {
		return uniprot_id;
	}


	public void setUniprot_id(String uniprot_id) {
		this.uniprot_id = uniprot_id;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	

	public int checkConflict(Protein protein) {
		
		int res = 0;
		
		if (this.pk!=null) {
			if (protein.getPk()==null) res++; // res = 1
			else if (!this.pk.equals(protein.getPk())) return -1;
		}
		
		
		if (this.type!=null) {
			if (protein.type ==null) res++; 
			else if (!this.type.equals(protein.type)) return -1;
		}
		
		if (this.uniprot_id!=null) {
			if (protein.uniprot_id ==null) res++; 
			else if (!this.uniprot_id.equals(protein.uniprot_id)) return -1;
		}
		
	
		return res;
	}
	
	

	public void update(Protein protein) throws ConflictException {
		this.update(protein, true);
	}
	
	
	public void update(Protein protein, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(protein)==-1) throw new ConflictException(this, protein);
		}
		if (protein.getPk() !=null) this.pk = protein.getPk();
		if (protein.getType() !=null) this.type = protein.getType();
		if (protein.getUniprot_id()!=null) this.uniprot_id = protein.getUniprot_id();	
	}


	@Override
	public String toString() {
		return "Protein [uniprot_id=" + uniprot_id + ", type=" + type + ", pk="
				+ pk + "]";
	}

}
