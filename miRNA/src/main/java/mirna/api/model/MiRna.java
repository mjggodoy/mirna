package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "mirna2", schema = "mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "mature", discriminatorType=DiscriminatorType.INTEGER)
public class MiRna extends ModelClass {

	@Column(name = "id", nullable = false, length = 20)
	protected String id;

	@Column(name = "accession_number", nullable = true, length = 20)
	private String accessionNumber;

	@Column(name = "previous_id", nullable = true, length = 90)
	private String previousId;
	
	@Column(name = "mature", nullable = true, length = 1, insertable=false, updatable=false)
	private boolean mature;
	
	@Column(name = "dead", nullable = true, length = 1)
	private boolean dead;
	
	@Column(name = "mirbase_pk", nullable = true)
	private Integer mirBasePk;
	
	public MiRna() {}

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

}