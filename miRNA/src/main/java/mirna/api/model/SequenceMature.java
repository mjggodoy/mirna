package mirna.api.model;

import javax.persistence.*;

@Entity
@Table(name = "sequence_mature", schema="mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SequenceMature extends ModelClass {

	@Column(name = "sequence", nullable = false)
	private String sequence;

	@Column(name = "gc_proportion", nullable = true, length = 10)
	private String gcProportion;

	public SequenceMature() { }

	public String getSequence() {
		return sequence;
	}

	public String getGcProportion() {
		return gcProportion;
	}
	
}
