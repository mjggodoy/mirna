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
@DiscriminatorValue("dead")
public class DeadMirna extends MiRna {
	
	@OneToMany
	@JoinColumn(name="mirna_pk")
	private Set<MirbaseDeadInfo> mirbaseInfo;
	
	public Set<MirbaseDeadInfo> getMirbaseInfo() {
		return mirbaseInfo;
	}

}
