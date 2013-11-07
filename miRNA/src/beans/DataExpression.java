package beans;

public class DataExpression {
	
	private String expression;
	private String foldchangeMin;
	private String foldchangeMax;
	private String id;
	private String studyDesign;
	private String method;
	private String treatment;
	private String support;
	private String type;
	private String pubmedId;
	private String year;
	private String description;
	
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPubmedId() {
		return pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	public DataExpression(){
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
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	
}
