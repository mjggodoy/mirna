package mirna.beans.nToM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hairpin_has_sequence")

public class HairpinHasSequence implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "hairpin_pk", nullable = false, length = 11, unique = false)
	private Integer hairpin_pk;

	@Id
	@Column(name = "sequence_pk", nullable = false, length = 11, unique = false)
	private Integer sequence_pk;

	public HairpinHasSequence() {
		super();

	}

	public HairpinHasSequence(Integer hairpin_pk, Integer sequence_pk) {
		super();
		this.hairpin_pk = hairpin_pk;
		this.sequence_pk = sequence_pk;
	}

	public Integer getHairpin_pk() {
		return hairpin_pk;
	}

	public void setHairpin_pk(Integer hairpin_pk) {
		this.hairpin_pk = hairpin_pk;
	}

	public Integer getSequence_pk() {
		return sequence_pk;
	}

	public void setSequence_pk(Integer sequence_pk) {
		this.sequence_pk = sequence_pk;
	}

	@Override
	public String toString() {
		return "HairpinHasSequence [hairpin_pk=" + hairpin_pk
				+ ", sequence_pk=" + sequence_pk + "]";
	}

}
