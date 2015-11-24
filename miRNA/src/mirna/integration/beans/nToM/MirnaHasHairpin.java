package mirna.integration.beans.nToM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mirna_has_hairpin")

public class MirnaHasHairpin implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "mirna_pk", nullable = false, length = 11, unique = false)
	private Integer mirna_pk;

	@Id
	@Column(name = "hairpin_pk", nullable = false, length = 11, unique = false) 
	private Integer hairpin_pk;

	
	
	
	public MirnaHasHairpin() {
		super();
	}



	public MirnaHasHairpin(Integer mirna_pk, Integer hairpin_pk) {
		super();
		this.mirna_pk = mirna_pk;
		this.hairpin_pk = hairpin_pk;
	}



	public Integer getMirna_pk() {
		return mirna_pk;
	}



	public void setMirna_pk(Integer mirna_pk) {
		this.mirna_pk = mirna_pk;
	}



	public Integer getHairpin_pk() {
		return hairpin_pk;
	}



	public void setHairpin_pk(Integer hairpin_pk) {
		this.hairpin_pk = hairpin_pk;
	}



	@Override
	public String toString() {
		return "MirnaHasHairpin [mirna_pk=" + mirna_pk + ", hairpin_pk="
				+ hairpin_pk + "]";
	} 

}
