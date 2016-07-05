package mirna.api.model;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("mature")
public class Mature extends MiRna {

	@OneToMany
	@JoinColumn(name="mature_pk")
	private Set<HairpinFromTo> hairpins;
	
	@OneToMany
	@JoinColumn(name="mirna_pk")
	private Set<MirbaseMatureInfo> mirbaseInfo;
	
	@OneToMany
	@JoinColumn(name="mirna_pk")
	private Set<SequenceMature> sequence;
	
	public Set<MirbaseMatureInfo> getMirbaseInfo() {
		return mirbaseInfo;
	}

	public Set<HairpinFromTo> getHairpins() {
		return hairpins;
	}

	public Set<SequenceMature> getSequence() {
		return sequence;
	}
	
	

}
