package mirna.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "environmental_factor", schema = "mirna")
public class EnvironmentalFactor extends ModelClass  {

	@Column(name = "name", nullable = false, length = 300)
	private String name;
	
	@OneToMany
    @JoinColumn(name="environmental_factor_pk") 
	private Set<SmallMolecule> smallMolecules;

	public EnvironmentalFactor() {
		super();
	}

	public String getName() {
		return name.substring(0,1).toUpperCase()+name.substring(1);
	}

	
	public Set<SmallMolecule> getSmallMolecules() {
		return smallMolecules;
	}

}
