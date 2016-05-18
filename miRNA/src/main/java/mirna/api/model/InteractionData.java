package mirna.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@ManyToOne
	@JoinColumn(name = "gene_pk")
	private Gene gene;
	
	@ManyToOne
	@JoinColumn(name = "target_pk")
	private Target target;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name="mirna_has_interaction_data2",
			schema="mirna",
			joinColumns={
					@JoinColumn(name="interaction_data_pk")
			},
			inverseJoinColumns={
					@JoinColumn(name="mirna_pk")
			})
	private Set<MiRna> mirnas;
	

	@ManyToMany(mappedBy="interactiondatas")
	private Set<BiologicalProcess> biologicalProcess;
	
	

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

	public Gene getGene() {
		return gene;
	}
	
	public Set<MiRna> getMirnas() {
		if (mirnas.size()==0) return null;
		else return mirnas;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cellularLine == null) ? 0 : cellularLine.hashCode());
		result = prime * result + ((feature == null) ? 0 : feature.hashCode());
		result = prime * result + ((gene == null) ? 0 : gene.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result
				+ ((miTGScore == null) ? 0 : miTGScore.hashCode());
		result = prime * result + ((mirnaPk == null) ? 0 : mirnaPk.hashCode());
		result = prime * result + ((mirnas == null) ? 0 : mirnas.hashCode());
		result = prime * result
				+ ((pValueOg == null) ? 0 : pValueOg.hashCode());
		result = prime * result + ((phase == null) ? 0 : phase.hashCode());
		result = prime * result
				+ ((provenance == null) ? 0 : provenance.hashCode());
		result = prime * result
				+ ((pvalueLog == null) ? 0 : pvalueLog.hashCode());
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result
				+ ((reference == null) ? 0 : reference.hashCode());
		result = prime * result + ((score == null) ? 0 : score.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InteractionData other = (InteractionData) obj;
		if (cellularLine == null) {
			if (other.cellularLine != null)
				return false;
		} else if (!cellularLine.equals(other.cellularLine))
			return false;
		if (feature == null) {
			if (other.feature != null)
				return false;
		} else if (!feature.equals(other.feature))
			return false;
		if (gene == null) {
			if (other.gene != null)
				return false;
		} else if (!gene.equals(other.gene))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (miTGScore == null) {
			if (other.miTGScore != null)
				return false;
		} else if (!miTGScore.equals(other.miTGScore))
			return false;
		if (mirnaPk == null) {
			if (other.mirnaPk != null)
				return false;
		} else if (!mirnaPk.equals(other.mirnaPk))
			return false;
		if (mirnas == null) {
			if (other.mirnas != null)
				return false;
		} else if (!mirnas.equals(other.mirnas))
			return false;
		if (pValueOg == null) {
			if (other.pValueOg != null)
				return false;
		} else if (!pValueOg.equals(other.pValueOg))
			return false;
		if (phase == null) {
			if (other.phase != null)
				return false;
		} else if (!phase.equals(other.phase))
			return false;
		if (provenance == null) {
			if (other.provenance != null)
				return false;
		} else if (!provenance.equals(other.provenance))
			return false;
		if (pvalueLog == null) {
			if (other.pvalueLog != null)
				return false;
		} else if (!pvalueLog.equals(other.pvalueLog))
			return false;
		if (rank == null) {
			if (other.rank != null)
				return false;
		} else if (!rank.equals(other.rank))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	
}
