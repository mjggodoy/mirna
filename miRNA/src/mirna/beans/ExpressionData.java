package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

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
	
	/* XUXA */
//	@Column(name = "smallMolecule_pk", nullable = true, length = 80) // he inlcu�do la smallmolecule_Pk para la db SM2miR2N
//	private Integer smallMolecule_pk;
	
//	@Column(name = "interactionData_pk", nullable = true, length = 80) // he inclu�do la interactionDataPk para la relaci�n interacionData/ExpressionData
//	private Integer interactionData_pk;
	
	
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

//	public Integer getSmallMolecule_pk() {
//		return smallMolecule_pk;
//	}
//
//	public void setSmallMolecule_pk(Integer smallMolecule_pk) {
//		this.smallMolecule_pk = smallMolecule_pk;
//	}
//	
//
//	public Integer getInteractionData_pk() {
//		return interactionData_pk;
//	}
//
//	public void setInteractionData_pk(Integer interactionData_pk) {
//		this.interactionData_pk = interactionData_pk;
//	}

	public void update(ExpressionData gene) throws ConflictException {
		this.update(gene, true);
	}

	public void update(ExpressionData ed, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(ed) == -1)
				throw new ConflictException(this, ed);
		}
		
		if (ed.getPk()!=null) this.pk = ed.getPk();
		if (ed.getCellularLine()!=null) this.cellularLine = ed.getCellularLine();
		if (ed.getCondition() !=null) this.condition = ed.getCondition();
		if(ed.getDataType() !=null) this.dataType = ed.getDataType() ;
		if(ed.getDescription() !=null) this.description = ed.getDescription();
		if(ed.getDifferentExpressionLocation() !=null) this.differentExpressionLocation = ed.getDifferentExpressionLocation();
		if(ed.getDiseasePk() != null) this.diseasePk = ed.diseasePk;
		if(ed.getEnvironmentalFactorPk() != null) this.environmentalFactorPk = ed.getEnvironmentalFactorPk();
		if(ed.getEvidence() !=null) this.evidence = ed.getEvidence();
		if(ed.getFoldchangeMax() !=null) this.foldchangeMax = ed.getFoldchangeMax();
		if(ed.getFoldchangeMin() != null) this.foldchangeMin = ed.getFoldchangeMin();
		if(ed.getMethod() != null) this.method = ed.getFoldchangeMin();
		if(ed.getMirnaPk() !=null) this.mirnaPk = ed.getMirnaPk(); 
		if(ed.getProvenance() !=null) this.provenance = ed.getProvenance();
		if(ed.getProvenanceId() !=null) this.provenanceId = ed.getProvenanceId();
//		if(ed.getSmallMolecule_pk() !=null) this.smallMolecule_pk = ed.getSmallMolecule_pk();
		if(ed.getStudyDesign() !=null) this.studyDesign = ed.getStudyDesign();
//		if(ed.getInteractionData_pk() != null) this.interactionData_pk = ed.getInteractionData_pk();
		if(ed.getTitleReference() != null) this.titleReference = ed.getTitleReference();
		if(ed.getTreatment() != null) this.treatment = ed.getTreatment();
		if(ed.getYear() !=null) this.year = ed.getYear();	
	}
	
	
	public int checkConflict(ExpressionData ed) {
		int res = 0;
		
		if (this.pk!=null) {
			if (ed.getPk()==null) res++; 
			else if (!this.pk.equals(ed.getPk())) return -1;
		}
		
		if (this.cellularLine!=null) {
			if (ed.getCellularLine()==null) res++; 
			else if (!this.cellularLine.equals(ed.getCellularLine())) return -1;
		}
		
		if (this.condition !=null){
			if(ed.getCondition() == null) res++;
			else if (!this.condition.equals(ed.getCondition())) return -1;		
		}
		
		if (this.dataType !=null){
			if(ed.getDataType() == null) res++;
			else if (!this.dataType.equals(ed.getDataType())) return -1;	
		}
		if (this.description !=null){
			if(ed.getDescription() == null) res++;
			else if (!this.description.equals(ed.getDescription())) return -1;	
		}
		if (this.differentExpressionLocation !=null){
			if(ed.getDifferentExpressionLocation() == null) res++;
			else if (!this.differentExpressionLocation.equals(ed.getDifferentExpressionLocation())) return -1;	
		}
		
		if (this.diseasePk !=null){
			if(ed.getDiseasePk() == null) res++;
			else if (!this.diseasePk.equals(ed.getDiseasePk())) return -1;	
		}
		
		if (this.environmentalFactorPk !=null){
			if(ed.getEnvironmentalFactorPk() == null) res++;
			else if (!this.environmentalFactorPk.equals(ed.getEnvironmentalFactorPk())) return -1;	
		}
		
		if (this.evidence !=null){
			if(ed.getEvidence() == null) res++;
			else if (!this.evidence.equals(ed.getEvidence())) return -1;	
		}
		
		if (this.foldchangeMax !=null){
			if(ed.getFoldchangeMax()== null) res++;
			else if (!this.foldchangeMax.equals(ed.getFoldchangeMax())) return -1;	
		}
		
		if (this.foldchangeMin !=null){
			if(ed.getFoldchangeMin()== null) res++;
			else if (!this.foldchangeMin.equals(ed.getFoldchangeMin())) return -1;	
		}
		
//		if (this.interactionData_pk !=null){
//			if(ed.getInteractionData_pk()== null) res++;
//			else if (!this.interactionData_pk.equals(ed.getInteractionData_pk())) return -1;	
//		}
		
		if (this.method !=null){
			if(ed.getMethod()== null) res++;
			else if (!this.method.equals(ed.getMethod())) return -1;	
		}
		
		if (this.mirnaPk !=null){
			if(ed.getMirnaPk()== null) res++;
			else if (!this.mirnaPk.equals(ed.getMirnaPk())) return -1;	
		}
		
		if (this.provenance !=null){
			if(ed.getProvenance()== null) res++;
			else if (!this.provenance.equals(ed.getProvenance())) return -1;	
		}
		
		if (this.provenanceId !=null){
			if(ed.getProvenanceId()== null) res++;
			else if (!this.provenanceId.equals(ed.getProvenanceId())) return -1;	
		}
		
//		if (this.smallMolecule_pk !=null){
//			if(ed.getSmallMolecule_pk()== null) res++;
//			else if (!this.smallMolecule_pk.equals(ed.getSmallMolecule_pk())) return -1;	
//		}
		
		if (this.studyDesign !=null){
			if(ed.getStudyDesign()== null) res++;
			else if (!this.studyDesign.equals(ed.getStudyDesign())) return -1;	
		}
		
		if (this.titleReference !=null){
			if(ed.getTitleReference()== null) res++;
			else if (!this.titleReference.equals(ed.getTitleReference())) return -1;	
		}
		
		if (this.treatment !=null){
			if(ed.getTreatment()== null) res++;
			else if (!this.treatment.equals(ed.getTreatment())) return -1;	
		}
		
		if (this.year !=null){
			if(ed.getYear()== null) res++;
			else if (!this.year.equals(ed.getYear())) return -1;	
		}
		
	
		return res;
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
				+ ", dataType=" + dataType + ", differentExpressionLocation="
				+ differentExpressionLocation 
//				+ ", smallMolecule_pk="
//				+ smallMolecule_pk + ", interactionData_pk="
//				+ interactionData_pk 
				+ "]";
	}
}
