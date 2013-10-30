package beans;

public class DataExpression {
	
	private String expression;
	private String foldchangeMin;
	private String foldchangeMax;
	private String id;
	private String studyDesign;
	private String method;
	
	public DataExpression(String expression, String foldchangeMin,
			String foldchangeMax, String id, String studyDesign, String method) {
		super();
		this.expression = expression;
		this.foldchangeMin = foldchangeMin;
		this.foldchangeMax = foldchangeMax;
		this.id = id;
		this.studyDesign = studyDesign;
		this.method = method;
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
	
	
}
