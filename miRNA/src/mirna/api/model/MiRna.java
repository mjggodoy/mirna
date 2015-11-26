package mirna.api.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mirna", schema = "mirna")
public class MiRna extends ModelClass {

	@Column(name = "name", nullable = false, length = 80, unique = true)
	protected String name;

	@Column(name = "arm", nullable = true, length = 5)
	private String arm;

	@Column(name = "resource", nullable = true, length = 45)
	private String resource;
	
	@OneToMany
	@JoinColumn(name = "mirna_pk", referencedColumnName = "pk")
	private List<ExpressionData> expressionData;
	
	@OneToMany
	@JoinColumn(name = "mirna_pk", referencedColumnName = "pk")
	private List<InteractionData> interactionData;
	
	@ManyToMany
	@JoinTable(name="mirna_has_sequence", schema="mirna",
		joinColumns={@JoinColumn(name="mirna_pk")},
		inverseJoinColumns={@JoinColumn(name="sequence_pk")})
	private List<Sequence> sequences;
	
	@ManyToMany
	@JoinTable(name="mirna_has_pubmed_document", schema="mirna",
		joinColumns={@JoinColumn(name="mirna_pk")},
		inverseJoinColumns={@JoinColumn(name="pubmed_document_pk")})
	private List<PubmedDocument> pubmedDocuments;
	
	@ManyToMany
	@JoinTable(name="mirna_has_hairpin", schema="mirna",
		joinColumns={@JoinColumn(name="mirna_pk")},
		inverseJoinColumns={@JoinColumn(name="hairpin_pk")})
	private List<Hairpin> hairpin;
	
	@ManyToMany
	@JoinTable(name="mirna_has_mature", schema="mirna",
		joinColumns={@JoinColumn(name="mirna_pk")},
		inverseJoinColumns={@JoinColumn(name="mature_pk")})
	private List<Mature> mature;
	
	@ManyToMany
	@JoinTable(name="mirna_involves_biological_process", schema="mirna",
		joinColumns={@JoinColumn(name="mirna_pk")},
		inverseJoinColumns={@JoinColumn(name="biological_process_pk")})
	private List<BiologicalProcess> biologicalProcess;

	public MiRna() {}

	public String getName() {
		return name;
	}

	public String getResource() {
		return resource;
	}

	public String getArm() {
		return arm;
	}

	public List<ExpressionData> getExpressionData() {
		return expressionData;
	}

	public List<InteractionData> getInteractionData() {
		return interactionData;
	}

	public List<Sequence> getSequences() {
		return sequences;
	}
	
	public List<PubmedDocument> getPubmedDocuments() {
		return pubmedDocuments;
	}

	public List<Hairpin> getHairpin() {
		return hairpin;
	}

	public List<Mature> getMature() {
		return mature;
	}

	public List<BiologicalProcess> getBiologicalProcess() {
		return biologicalProcess;
	}
	
}