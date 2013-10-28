package beans;

public class DataExpression {
	
	private String expression;
	private double foldchangeMin;
	private double foldchangeMax;
	private String studyDesign;
	private String method;
	
	public DataExpression(String expression, double foldchangeMin,
			double foldchangeMax, String studyDesign, String method) {
		super();
		this.expression = expression;
		this.foldchangeMin = foldchangeMin;
		this.foldchangeMax = foldchangeMax;
		this.studyDesign = studyDesign;
		this.method = method;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public double getFoldchangeMin() {
		return foldchangeMin;
	}

	public void setFoldchangeMin(double foldchangeMin) {
		this.foldchangeMin = foldchangeMin;
	}

	public double getFoldchangeMax() {
		return foldchangeMax;
	}

	public void setFoldchangeMax(double foldchangeMax) {
		this.foldchangeMax = foldchangeMax;
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
