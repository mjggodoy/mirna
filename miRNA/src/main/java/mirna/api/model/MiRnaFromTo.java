package mirna.api.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "hairpin2mature", schema = "mirna")
public class MiRnaFromTo implements Serializable {

	@Id
	@Column(name = "hairpin_pk", nullable=false)
	private int hairpinPk;

	@Id
	@Column(name = "mature_pk", nullable=false)
	private int maturePk;

	@Column(name = "from_idx", nullable=true)
	private int fromIdx;

	@Column(name = "to_idx", nullable=true)
	private int toIdx;

	public int getTo() {
		return toIdx;
	}

	public int getFrom() {
		return fromIdx;
	}

}
