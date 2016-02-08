package mirna.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mirna2", schema = "mirna")
public class MiRna2 extends ModelClass {

	@Column(name = "id", nullable = false, length = 20)
	protected String id;

	@Column(name = "accession_number", nullable = true, length = 20)
	private String accessionNumber;

	@Column(name = "previous_id", nullable = true, length = 90)
	private String previousId;
	
	@Column(name = "mature", nullable = true, length = 1)
	private boolean mature;
	
	@Column(name = "dead", nullable = true, length = 1)
	private boolean dead;
	
	@Column(name = "mirbase_pk", nullable = true)
	private Integer mirBasePk;
	
	@ManyToMany
	@JoinTable(name="hairpin2mature", schema="mirna",
		joinColumns={@JoinColumn(name="mature_pk")},
		inverseJoinColumns={@JoinColumn(name="hairpin_pk")})
	private List<MiRna2> hairpins;
	
	@ManyToMany
	@JoinTable(name="hairpin2mature", schema="mirna",
		joinColumns={@JoinColumn(name="hairpin_pk")},
		inverseJoinColumns={@JoinColumn(name="mature_pk")})
	private List<MiRna2> matures;

	public MiRna2() {}

	public String getId() {
		return id;
	}

	public String getAccessionNumber() {
		return accessionNumber;
	}

	public String getPreviousId() {
		return previousId;
	}

	public boolean isMature() {
		return mature;
	}

	public boolean isDead() {
		return dead;
	}
	
	public Integer getMirBasePk() {
		return mirBasePk;
	}

	public List<MiRna2> getHairpins() {
		return hairpins;
	}

	public List<MiRna2> getMatures() {
		return matures;
	}
	
}