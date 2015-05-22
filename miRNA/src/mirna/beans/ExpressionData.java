package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "expression_data")
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
	
	@Column(name = "disease_pk", nullable = true, length = 20)
	private Integer diseasePk;
	
	@Column(name = "environmental_factor_pk", nullable = true, length = 20)
	private Integer environmentalFactorPk;
	
	@Column(name = "data_type", nullable = true, length = 20)
	private String dataType;
	
	@Column(name = "different_expression_location", nullable = true, length = 80)
	private String differentExpressionLocation;
	
	@Column(name = "smallMolecule_pk", nullable = true, length = 80) // he inlcu’do la smallmolecule_Pk para la db SM2miR2N
	private Integer smallMolecule_pk;
	
	
//	private String journal; //ok
//	private String reference; //ok
//	private String resource; //ok
//	private String phenomicId;//ok
//	private String mirenvironmentID;
//	private String support;//ok
	
	public ExpressionData() {
		super();
	}
	
	public String getTitleReference() {
		return titleReference;
	}

	public void setTitleReference(String titleReference) {
		this.titleReference = titleReference;
	}

	public String getFoldchangeMin() {
		return foldchangeMin;
	}

	public void setFoldchangeMin(String foldchangeMin) {
		this.foldchangeMin = foldchangeMin;
	}

	public String getFoldchangeMax() {
		return foldchangeMax;
	}

	public void setFoldchangeMax(String foldchangeMax) {
		this.foldchangeMax = foldchangeMax;
	}

	public String getProvenanceId() {
		return provenanceId;
	}

	public void setProvenanceId(String provenanceId) {
		this.provenanceId = provenanceId;
	}

	public String getProvenance() {
		return provenance;
	}

	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}

	public String getStudyDesign() {
		return studyDesign;
	}

	public void setStudyDesign(String studyDesign) {
		this.studyDesign = studyDesign;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getEvidence() {
		return evidence;
	}

	public void setEvidence(String evidence) {
		this.evidence = evidence;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCellularLine() {
		return cellularLine;
	}

	public void setCellularLine(String cellularLine) {
		this.cellularLine = cellularLine;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	public Integer getMirnaPk() {
		return mirnaPk;
	}

	public void setMirnaPk(Integer mirnaPk) {
		this.mirnaPk = mirnaPk;
	}

	public Integer getDiseasePk() {
		return diseasePk;
	}

	public void setDiseasePk(Integer diseasePk) {
		this.diseasePk = diseasePk;
	}

	public Integer getEnvironmentalFactorPk() {
		return environmentalFactorPk;
	}

	public void setEnvironmentalFactorPk(Integer environmentalFactorPk) {
		this.environmentalFactorPk = environmentalFactorPk;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public String getDifferentExpressionLocation() {
		return differentExpressionLocation;
	}

	public void setDifferentExpressionLocation(
			String differentExpressionLocation) {
		this.differentExpressionLocation = differentExpressionLocation;
	}

	public Integer getSmallMolecule_pk() {
		return smallMolecule_pk;
	}

	public void setSmallMolecule_pk(Integer smallMolecule_pk) {
		this.smallMolecule_pk = smallMolecule_pk;
	}

	@Override
	public String toString() {
		return "ExpressionData [titleReference=" + titleReference
				+ ", foldchangeMin=" + foldchangeMin + ", foldchangeMax="
				+ foldchangeMax + ", provenanceId=" + provenanceId
				+ ", provenance=" + provenance + ", studyDesign=" + studyDesign
				+ ", method=" + method + ", treatment=" + treatment
				+ ", evidence=" + evidence + ", year=" + year
				+ ", description=" + description + ", cellularLine="
				+ cellularLine + ", condition=" + condition + ", mirnaPk="
				+ mirnaPk + ", diseasePk=" + diseasePk
				+ ", environmentalFactorPk=" + environmentalFactorPk
				+ ", dataType=" + dataType + ", smallMolecule_pk="
				+ smallMolecule_pk + ", differentExpressionLocation="
				+ differentExpressionLocation + ", pk=" + pk + "]";
	}

	

}
