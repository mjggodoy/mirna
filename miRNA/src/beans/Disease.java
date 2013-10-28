package beans;

public class Disease {
	
	private String name;
	private String diseaseSub;
	private String diseaseClass;
	private int phenomicId;

	public Disease(String name, String diseaseSub, String diseaseClass, int phenomicId) {
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

	public int getPhenomicId() {
		return phenomicId;
	}

	public void setPhenomicId(int phenomicId) {
		this.phenomicId = phenomicId;
	}
	
}
