package mirna.integration.beans.nToM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mature_has_sequence")

public class MatureHasSequence implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "mature_pk", nullable = false, length = 11, unique = false)
	private Integer mature_pk;

	@Id
	@Column(name = "sequence_pk", nullable = false, length = 11, unique = false)
	private Integer sequence_pk;
	
	

	public MatureHasSequence() {
		super();
	}


	public MatureHasSequence(Integer mature_pk, Integer sequence_pk) {
		super();
		this.mature_pk = mature_pk;
		this.sequence_pk = sequence_pk;
	}


	public Integer getMature_pk() {
		return mature_pk;
	}


	public void setMature_pk(Integer mature_pk) {
		this.mature_pk = mature_pk;
	}


	public Integer getSequence_pk() {
		return sequence_pk;
	}


	public void setSequence_pk(Integer sequence_pk) {
		this.sequence_pk = sequence_pk;
	}


	@Override
	public String toString() {
		return "MatureHasSequence [mature_pk=" + mature_pk + ", sequence_pk="
				+ sequence_pk + "]";
	}
	

}
