package mirna.api.model;

import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorValue("mature")
public class Mature extends MiRna {
	
	@ManyToMany
	@JoinTable(name="hairpin2mature", schema="mirna",
		joinColumns={@JoinColumn(name="mature_pk")},
		inverseJoinColumns={@JoinColumn(name="hairpin_pk")})
	private List<MiRna> hairpins;
	
	@OneToMany
	@JoinColumn(name="mirna_pk")
	private Set<MirbaseMatureInfo> mirbaseInfo;
	
	public List<MiRna> getHairpins() {
		return hairpins;
	}
	
	public Set<MirbaseMatureInfo> getMirbaseInfo() {
		return mirbaseInfo;
	}

}
