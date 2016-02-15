package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "interaction_data", schema = "mirna")
public class InteractionData extends ModelClass {

	@Column(name = "score", nullable = true, length = 80, unique = true)
	protected String score;
	@Column(name = "pvalue_log", nullable = true, length = 80, unique = true)
	protected String pvalueLog;
	@Column(name = "miTG_score", nullable = true, length = 80, unique = true)
	protected String miTGScore;
	@Column(name = "method", nullable = true, length = 80, unique = true)
	protected String method;
	@Column(name = "feature", nullable = true, length = 80, unique = true)
	protected String feature;
	@Column(name = "phase", nullable = true, length = 80, unique = true)
	protected String phase;
	@Column(name = "rank", nullable = true, length = 80, unique = true)
	protected String rank;
	@Column(name = "provenance", nullable = true, length = 80, unique = true)
	protected String provenance;
	@Column(name = "reference", nullable = true, length = 80, unique = true)
	protected String reference;
	@Column(name = "cellular_line", nullable = true, length = 80, unique = true)
	protected String cellularLine;
	@Column(name = "pvalue_og", nullable = true, length = 80, unique = true)
	protected String pValueOg;
	@Column(name = "type", nullable = true, length = 80, unique = true)
	protected String type;
	@Column(name = "mirna_pk", nullable = false, length = 80, unique = true)
	protected Integer mirnaPk;
	@Column(name = "target_pk", nullable = true, length = 80, unique = true)
	protected Integer targetPk;
	
//	@ManyToMany
//	@JoinTable(
//			name="mirna_pk_translation",
//			schema="mirna",
//			joinColumns={
//					@JoinColumn(name="old_pk", referencedColumnName="mirna_pk")
//			},
//			inverseJoinColumns={
//					@JoinColumn(name="new_pk")
//			})
//	private Set<MiRna> mirna;
	
	@ManyToOne
	@JoinColumn(name = "gene_pk")
	private Gene gene;

	public InteractionData() {
		super();
	}

	public String getScore() {
		return score;
	}

	public String getPvalueLog() {
		return pvalueLog;
	}

	public String getMiTGScore() {
		return miTGScore;
	}

	public String getMethod() {
		return method;
	}

	public String getFeature() {
		return feature;
	}

	public String getPhase() {
		return phase;
	}

	public String getRank() {
		return rank;
	}

	public String getProvenance() {
		return provenance;
	}

	public String getReference() {
		return reference;
	}

	public String getCellularLine() {
		return cellularLine;
	}

	public String getpValueOg() {
		return pValueOg;
	}

	public String getType() {
		return type;
	}

	public Integer getMirnaPk() {
		return mirnaPk;
	}

	public Integer getTargetPk() {
		return targetPk;
	}

	public Gene getGene() {
		return gene;
	}
	
}
