package mirna.beans;

public class ExpressionData extends ModelClass {

	private String title_reference;//ok
	private String phenomicId;//ok
	private String mirenvironmentID;
	private String foldchangeMin; //ok
	private String foldchangeMax; //ok
	private String id; //ok
	private String studyDesign;//ok
	private String method; //ok
	private String treatment; //ok
	private String support;//ok
	private String evidence;// ok
	private String pubmedId;//ok
	private String year;//ok
	private String description; //ok
	private String cellularLine; //ok
	private String condition; //ok
	private String journal; //ok
	private String reference; //ok
	private String resource; //ok

	public ExpressionData(int pk, String phenomicId,
			String foldchangeMin, String foldchangeMax,
			String id, String studyDesign, String method,
			String treatment, String support, String profile, String pubmedId,
			String year, String description, String cellularLine,
			String condition, String journal, String reference, String resource, String title_reference) {
		super(pk);
		this.phenomicId = phenomicId;
		this.foldchangeMin = foldchangeMin;
		this.foldchangeMax = foldchangeMax;
		this.id = id;
		this.studyDesign = studyDesign;
		this.method = method;
		this.treatment = treatment;
		this.support = support;
		this.evidence = profile;
		this.pubmedId = pubmedId;
		this.year = year;
		this.description = description;
		this.cellularLine = cellularLine;
		this.condition = condition;
		this.journal = journal;
		this.reference = reference;
		this.resource = resource;
		this.title_reference = title_reference;
	}
	
	
	
	
	
	public String getTitle_reference() {
		return title_reference;
	}


	public void setTitle_reference(String title_reference) {
		this.title_reference = title_reference;
	}


	public String getResource() {
		return resource;
	}


	public void setResource(String resource) {
		this.resource = resource;
	}


	public String getReference() {
		return reference;
	}


	public void setReference(String reference) {
		this.reference = reference;
	}


	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getCellularLine() {
		return cellularLine;
	}

	public void setCellularLine(String cellularLine) {
		this.cellularLine = cellularLine;
	}

	public void setPhenomicId(String phenomicId) {
		this.phenomicId = phenomicId;
	}

	public ExpressionData() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getPhenomicId() {
		return phenomicId;
	}

	public void setPhenomidId(String phenomicId) {
		this.phenomicId = phenomicId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getmirenvironmentID() {
		return mirenvironmentID;
	}

	public void setmirenvironmentID(String mirenvironmentID) {
		this.mirenvironmentID = mirenvironmentID;
	}
	
	

}
