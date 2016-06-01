package mirna.api.model;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("hairpin")
public class Hairpin extends MiRna {
	
//	@ManyToMany
//	@JoinTable(name="hairpin2mature", schema="mirna",
//		joinColumns={@JoinColumn(name="hairpin_pk")},
//		inverseJoinColumns={@JoinColumn(name="mature_pk")})
//	private List<MiRna> matures;

//	@OneToMany(mappedBy="hairpin")
//	//@JoinColumn(name="hairpin_pk")
//	private Set<MiRnaFromTo> matures;
	
	@OneToMany
	@JoinColumn(name="mirna_pk")
	private Set<MirbaseMirnaInfo> mirbaseInfo;

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Sequence sequence;

//	public List<MiRna> getMatures() {
//		return matures;
//	}

//	public Set<MiRnaFromTo> getMatures() {
//		return matures;
//	}
	
	public Set<MirbaseMirnaInfo> getMirbaseInfo() {
		return mirbaseInfo;
	}

	public Sequence getSequence() {
		return sequence;
	}

}
