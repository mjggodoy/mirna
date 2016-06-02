package mirna.api.model;

import javax.persistence.*;

@Entity
@Table(name = "mirna2", schema = "mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class HairpinMinified extends MiRnaMinified {

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Sequence sequence;

	public Sequence getSequence() {
		return sequence;
	}

}