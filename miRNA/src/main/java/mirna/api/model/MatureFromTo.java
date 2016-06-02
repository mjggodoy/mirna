package mirna.api.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hairpin2mature", schema = "mirna")
public class MatureFromTo extends MiRnaFromTo implements Serializable {

	@ManyToOne
	@JoinColumn(name="mature_pk")
	private MatureMinified mature;

	public MatureMinified getMature() {
		return mature;
	}
}
