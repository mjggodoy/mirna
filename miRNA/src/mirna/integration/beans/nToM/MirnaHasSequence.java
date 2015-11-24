package mirna.integration.beans.nToM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mirna_has_sequence")
public class MirnaHasSequence implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "mirna_pk", nullable = false, length = 11, unique = false)
	private Integer mirnaPk;

	@Id
	@Column(name = "sequence_pk", nullable = false, length = 11, unique = false)
	private Integer sequencePk;
	
	public MirnaHasSequence() {
		super();
	}

	public MirnaHasSequence(Integer mirnaPk, Integer sequencePk) {
		super();
		this.mirnaPk = mirnaPk;
		this.sequencePk = sequencePk;
	}
	
	public Integer getMirnaPk() {
		return mirnaPk;
	}

	public void setMirnaPk(Integer mirnaPk) {
		this.mirnaPk = mirnaPk;
	}

	public Integer getSequencePk() {
		return sequencePk;
	}

	public void setSequencePk(Integer sequencePk) {
		this.sequencePk = sequencePk;
	}

	@Override
	public String toString() {
		return "MirnaHasSequence [mirnaPk="
				+ mirnaPk + ", sequencePk=" + sequencePk
				+ "]";
	}

}
