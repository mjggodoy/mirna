package mirna.beans.nToM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mirna_has_organism")

public class MirnaHasOrganism implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "mirna_pk", nullable = false, length = 11, unique = false)
	private Integer mirna_pk;

	@Id
	@Column(name = "organism_pk", nullable = false, length = 11, unique = false) 
	private Integer organism_pk; 
	
	public MirnaHasOrganism(){	
		super();
	}
	
	public MirnaHasOrganism(Integer mirna_pk, Integer organism_pk) {
		super();
		this.mirna_pk = mirna_pk;
		this.organism_pk = organism_pk;
	}

	public Integer getMirna_pk() {
		return mirna_pk;
	}

	public void setMirna_pk(Integer mirna_pk) {
		this.mirna_pk = mirna_pk;
	}

	public Integer getOrganism_pk() {
		return organism_pk;
	}

	public void setOrganism_pk(Integer organism_pk) {
		this.organism_pk = organism_pk;
	}

	@Override
	public String toString() {
		return "MirnaHasOrganism [mirna_pk=" + mirna_pk + ", organism_pk="
				+ organism_pk + "]";
	}
	
	

}
