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

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "expression_data", schema = "mirna")
public class ExpressionData extends ModelClass {

	@Column(name = "title_reference", nullable = true, length = 300)
	private String titleReference;
	
	@Column(name = "foldchange_min", nullable = true, length = 20)
	private String foldchangeMin;
	
	@Column(name = "foldchange_max", nullable = true, length = 20)
	private String foldchangeMax;
	
	@Column(name = "provenance_id", nullable = true, length = 10)
	private String provenanceId;
	
	@Column(name = "provenance", nullable = false, length = 40)
	private String provenance;
	
	@Column(name = "study_design", nullable = true, length = 20)
	private String studyDesign;
	
	@Column(name = "method", nullable = true, length = 40)
	private String method;
	
	@Column(name = "treatment", nullable = true, length = 400)
	private String treatment;
	
	@Column(name = "evidence", nullable = true, length = 80)
	private String evidence;
	
	@Column(name = "year", nullable = true, length = 4)
	private String year;
	
	@Column(name = "description", nullable = true, length = 1600)
	private String description;
	
	@Column(name = "cellular_line", nullable = true, length = 400)
	private String cellularLine;
	
	@Column(name = "condition_", nullable = true, length = 200)
	private String condition;
	
	@Column(name = "mirna_pk", nullable = false, length = 20)
	private Integer mirnaPk;
	
	@Column(name = "environmental_factor_pk", nullable = true, length = 20)
	private Integer environmentalFactorPk;
	
	@Column(name = "data_type", nullable = true, length = 20)
	private String dataType;
	
	@Column(name = "different_expression_location", nullable = true, length = 80)
	private String differentExpressionLocation;
	
//	@Column(name = "interaction_data_pk", nullable = true, length = 80)
//	private Integer interactionDataPk;
	
	@ManyToOne
	@JoinColumn(name = "interaction_data_pk")
	private InteractionData interactionData;
	
	@ManyToMany
	@JoinTable(
			name="mirna_has_expression_data2",
			schema="mirna",
			joinColumns={
					@JoinColumn(name="expression_data_pk")
			},
			inverseJoinColumns={
					@JoinColumn(name="mirna_pk")
			})
	private Set<MiRna> mirnas;
	
	@ManyToOne
	@JoinColumn(name = "disease_pk")
	private Disease disease;
	
//	@ManyToOne
//	@JoinColumn(name = "environmental_factor_pk")
//	private EnvironmentalFactor environmentalFactor;
	
	public ExpressionData() {
		super();
	}

	public String getTitleReference() {
		return titleReference;
	}

	public String getFoldchangeMin() {
		return foldchangeMin;
	}

	public String getFoldchangeMax() {
		return foldchangeMax;
	}

	public String getProvenanceId() {
		return provenanceId;
	}

	public String getProvenance() {
		return provenance;
	}

	public String getStudyDesign() {
		return studyDesign;
	}

	public String getMethod() {
		return method;
	}

	public String getTreatment() {
		return treatment;
	}

	public String getEvidence() {
		return evidence;
	}

	public String getYear() {
		return year;
	}

	public String getDescription() {
		return description;
	}

	public String getCellularLine() {
		return cellularLine;
	}

	public String getCondition() {
		return condition;
	}

	public Integer getMirnaPk() {
		return mirnaPk;
	}

	public Integer getEnvironmentalFactorPk() {
		return environmentalFactorPk;
	}

	public String getDataType() {
		return dataType;
	}

	public String getDifferentExpressionLocation() {
		return differentExpressionLocation;
	}

//	public Integer getInteractionDataPk() {
//		return interactionDataPk;
//	}
	
	public Set<MiRna> getMirnas() {
		return mirnas;
	}
	
	public Disease getDisease() {
		return disease;
	}
	
	public InteractionData getInteractionData() {
		return interactionData;
	}
}