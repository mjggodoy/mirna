package mirna.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name = "mirna2", schema = "mirna")
@SecondaryTable(name = "mirna_pk_translation",
	schema = "mirna",
	pkJoinColumns = @PrimaryKeyJoinColumn(name = "new_pk"))
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
	
	@ManyToMany
	@JoinTable(
			name="mirna_has_pubmed_document",
			schema="mirna",
			joinColumns={
					@JoinColumn(name="new_pk", referencedColumnName="old_pk2", table="mirna_pk_translation"),
					@JoinColumn(name="mirna_pk", referencedColumnName="old_pk3", table="mirna_pk_translation")
			},
			inverseJoinColumns={
					@JoinColumn(name="pubmed_document_pk")
			})
	private List<PubmedDocument> pubmedDocument;
	
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
	
	public List<PubmedDocument> getPubmedDocument() {
		return pubmedDocument;
	}

}