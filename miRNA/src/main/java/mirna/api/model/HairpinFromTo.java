package mirna.api.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "hairpin2mature", schema = "mirna")
public class HairpinFromTo extends MiRnaFromTo implements Serializable {

	@ManyToOne
	@JoinColumn(name="hairpin_pk")
	private HairpinMinified hairpin;

	public HairpinMinified getHairpin() {
		return hairpin;
	}
}
