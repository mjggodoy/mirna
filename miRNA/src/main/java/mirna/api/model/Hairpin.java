package mirna.api.model;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("0")
public class Hairpin extends MiRna {
	
	@ManyToMany
	@JoinTable(name="hairpin2mature", schema="mirna",
		joinColumns={@JoinColumn(name="hairpin_pk")},
		inverseJoinColumns={@JoinColumn(name="mature_pk")})
	private List<MiRna> matures;
	
	public List<MiRna> getMatures() {
		return matures;
	}

}
