package mirna.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "mirna2", schema = "mirna")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType=DiscriminatorType.STRING)
public class MiRna extends ModelClass {

	@Column(name = "id", nullable = false, length = 20)
	protected String id;

	@Column(name = "accession_number", nullable = true, length = 20)
	private String accessionNumber;

	@Column(name = "previous_id", nullable = true, length = 90)
	private String previousId;
	
	@Column(name = "type", nullable = true, insertable=false, updatable=false)
	private String type;
	
	@Column(name = "mirbase_pk", nullable = true)
	private Integer mirBasePk;
	
	@ManyToMany
	@JoinTable(
			name="mirna_has_pubmed_document2",
			schema="mirna",
			joinColumns={
					@JoinColumn(name="mirna_pk")
			},
			inverseJoinColumns={
					@JoinColumn(name="pubmed_document_pk")
			})
	private Set<PubmedDocument> pubmedDocuments;
	
	
	@ManyToMany(mappedBy="mirnas")
	private Set<BiologicalProcess> biologicalProcess;

	//@ManyToMany(mappedBy = "mirnas")
	//private Set<ExpressionData> expressionDatas;
	
	//@ManyToMany(mappedBy = "mirnas")
	//private Set<InteractionData> interactionDatas;
	
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

	public String getType() {
		return type;
	}
	
	public Integer getMirBasePk() {
		return mirBasePk;
	}
	
	public Set<PubmedDocument> getPubmedDocuments() {
		return pubmedDocuments;
	}

	public Set<BiologicalProcess> getBiologicalProcess() {
		return biologicalProcess;
	}
	
	

}