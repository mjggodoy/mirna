package mirna.api.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mirna2", schema = "mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class HairpinMinified extends MiRnaMinified {

	@OneToMany
	@JoinColumn(name="mirna_pk")
	private List<SequenceHairpin> sequences;

	public SequenceHairpin getSequence() {
		if (sequences.size()>0) return sequences.get(0);
		else return null;
	}

}