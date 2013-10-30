package beans;

public class Disease {
	
	private String name;
	private String diseaseSub;
	private String diseaseClass;
	private String phenomicId;

	public Disease(String name, String diseaseSub, String diseaseClass, String phenomicId) {
		super();
		this.name = name;
		this.diseaseSub = diseaseSub;
		this.diseaseClass = diseaseClass;
		this.phenomicId = phenomicId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiseaseSub() {
		return diseaseSub;
	}

	public void setDiseaseSub(String diseaseSub) {
		this.diseaseSub = diseaseSub;
	}

	public String getDiseaseClass() {
		return diseaseClass;
	}

	public void setDiseaseClass(String diseaseClass) {
		this.diseaseClass = diseaseClass;
	}

	public String getPhenomicId() {
		return phenomicId;
	}

	public void setPhenomicId(String phenomicId) {
		this.phenomicId = phenomicId;
	}
	
}
