package mirna.api.model;

import java.util.Set;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("hairpin")
public class Hairpin extends MiRna {

	@OneToMany
	@JoinColumn(name="hairpin_pk")
	private Set<MatureFromTo> matures;
	
	@OneToMany
	@JoinColumn(name="mirna_pk")
	private Set<MirbaseMirnaInfo> mirbaseInfo;
	

	@OneToMany
	@JoinColumn(name="mirna_pk")
	private Set<SequenceHairpin> sequence;

	public Set<MatureFromTo> getMatures() {
		return matures;
	}
	
	public Set<MirbaseMirnaInfo> getMirbaseInfo() {
		return mirbaseInfo;
	}

	public Set<SequenceHairpin> getSequence() {
		return sequence;
	}

}
