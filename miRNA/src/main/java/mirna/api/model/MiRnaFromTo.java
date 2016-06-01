package mirna.api.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

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

	@ManyToOne
	@JoinColumn(name="mature_pk")
	private MiRna matureX;

	@ManyToOne
	@JoinColumn(name="hairpin_pk")
	private MiRna hairpinX;

	public int getFromIdx() {
		return fromIdx;
	}

	public int getToIdx() {
		return toIdx;
	}

	public MiRna getMatureX() {
		return matureX;
	}

	public MiRna getHairpinX() {
		return hairpinX;
	}
}
