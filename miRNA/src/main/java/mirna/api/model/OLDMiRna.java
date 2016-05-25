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
public class OLDMiRna extends ModelClass {

	@Column(name = "name", nullable = false, length = 80, unique = true)
	protected String name;

	@Column(name = "arm", nullable = true, length = 5)
	private String arm;

	@Column(name = "resource", nullable = true, length = 45)
	private String resource;
	
	@OneToMany
	@JoinColumn(name = "mirna_pk", referencedColumnName = "pk")
	private List<ExpressionData> expression_data;
	
	@OneToMany
	@JoinColumn(name = "mirna_pk", referencedColumnName = "pk")
	private List<InteractionData> interaction_data;
	
	@ManyToMany
	@JoinTable(name="mirna_has_sequence", schema="mirna",
		joinColumns={@JoinColumn(name="mirna_pk")},
		inverseJoinColumns={@JoinColumn(name="sequence_pk")})
	private List<OLDSequence> sequence;
	
	@ManyToMany
	@JoinTable(name="mirna_has_pubmed_document", schema="mirna",
		joinColumns={@JoinColumn(name="mirna_pk")},
		inverseJoinColumns={@JoinColumn(name="pubmed_document_pk")})
	private List<PubmedDocument> pubmed_document;
	
	@ManyToMany
	@JoinTable(name="mirna_has_hairpin", schema="mirna",
		joinColumns={@JoinColumn(name="mirna_pk")},
		inverseJoinColumns={@JoinColumn(name="hairpin_pk")})
	private List<OLDHairpin> hairpin;
	
	@ManyToMany
	@JoinTable(name="mirna_has_mature", schema="mirna",
		joinColumns={@JoinColumn(name="mirna_pk")},
		inverseJoinColumns={@JoinColumn(name="mature_pk")})
	private List<OLDMature> mature;
	
	@ManyToMany
	@JoinTable(name="mirna_involves_biological_process", schema="mirna",
		joinColumns={@JoinColumn(name="mirna_pk")},
		inverseJoinColumns={@JoinColumn(name="biological_process_pk")})
	private List<BiologicalProcess> biological_process;

	public OLDMiRna() {}

	public String getName() {
		return name;
	}

	public String getResource() {
		return resource;
	}

	public String getArm() {
		return arm;
	}

	public List<ExpressionData> getExpression_data() {
		return expression_data;
	}

	public List<InteractionData> getInteraction_data() {
		return interaction_data;
	}

	public List<OLDSequence> getSequence() {
		return sequence;
	}
	
	public List<PubmedDocument> getPubmed_document() {
		return pubmed_document;
	}

	public List<OLDHairpin> getHairpin() {
		return hairpin;
	}

	public List<OLDMature> getMature() {
		return mature;
	}

	public List<BiologicalProcess> getBiological_process() {
		return biological_process;
	}
	
}