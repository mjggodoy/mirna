package mirna.beans;

public class DataExpression extends ModelClass {

	private String expression;
	private String phenomicId;
	private String mirenvironmentID;
	private String foldchangeMin;
	private String foldchangeMax;
	private String id;
	private String studyDesign;
	private String method;
	private String treatment;
	private String support;
	private String profile;
	private String pubmedId;
	private String year;
	private String description;
	private String cellularLine;
	private String condition;
	
	public DataExpression(int pk, String expression, String phenomicId,
			String foldchangeMin, String foldchangeMax,
			String id, String studyDesign, String method,
			String treatment, String support, String profile, String pubmedId,
			String year, String description, String cellularLine,
			String condition) {
		super(pk);
		this.expression = expression;
		this.phenomicId = phenomicId;
		this.foldchangeMin = foldchangeMin;
		this.foldchangeMax = foldchangeMax;
		this.id = id;
		this.studyDesign = studyDesign;
		this.method = method;
		this.treatment = treatment;
		this.support = support;
		this.profile = profile;
		this.pubmedId = pubmedId;
		this.year = year;
		this.description = description;
		this.cellularLine = cellularLine;
		this.condition = condition;
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

	public DataExpression() {
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getPubmedId() {
		return pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
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
