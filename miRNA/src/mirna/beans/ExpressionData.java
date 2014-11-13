package mirna.beans;

public class ExpressionData extends ModelClass {

	private String titleReference;//ok
//	private String phenomicId;//ok
//	private String mirenvironmentID;
	private String foldchangeMin; //ok
	private String foldchangeMax; //ok
	private String provenanceId; //ok
	private String provenance;
	private String studyDesign;//ok
	private String method; //ok
	private String treatment; //ok
//	private String support;//ok
	private String evidence;// ok
	private String pubmedId;//ok
	private String year;//ok
	private String description; //ok
	private String cellularLine; //ok
	private String condition; //ok
//	private String journal; //ok
//	private String reference; //ok
//	private String resource; //ok
	
	private Integer mirnaPk;
	private Integer diseasePk;
	private Integer enviromentalFactorPk;
	
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

	public String getPubmedId() {
		return pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
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

	public Integer getEnviromentalFactorPk() {
		return enviromentalFactorPk;
	}

	public void setEnviromentalFactorPk(Integer enviromentalFactorPk) {
		this.enviromentalFactorPk = enviromentalFactorPk;
	}

	@Override
	public String toString() {
		return "ExpressionData [titleReference=" + titleReference
				+ ", foldchangeMin=" + foldchangeMin + ", foldchangeMax="
				+ foldchangeMax + ", provenanceId=" + provenanceId
				+ ", provenance=" + provenance + ", studyDesign=" + studyDesign
				+ ", method=" + method + ", treatment=" + treatment
				+ ", evidence=" + evidence + ", pubmedId=" + pubmedId
				+ ", year=" + year + ", description=" + description
				+ ", cellularLine=" + cellularLine + ", condition=" + condition
				+ ", mirnaPk=" + mirnaPk + ", diseasePk=" + diseasePk
				+ ", enviromentalFactorPk=" + enviromentalFactorPk + ", pk="
				+ pk + "]";
	}

}
