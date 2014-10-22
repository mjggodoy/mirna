package mirna.beans;

public class Disease extends ModelClass {
	
	private String name;//ok
	//private String diseaseSub;
	private String diseaseClass;//ok
	//private String phenomicId;
//	private String description; //ok
//	private String pubmedId;//ok
//	private String tissue; //ok
//	private String id; //ok
	
	public Disease() {}
	
	public Disease(int pk, String name, String diseaseClass) {
		super(pk);
		this.name = name;
		this.diseaseClass = diseaseClass;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiseaseClass() {
		return diseaseClass;
	}

	public void setDiseaseClass(String diseaseClass) {
		this.diseaseClass = diseaseClass;
	}

	public boolean checkConflict(Disease disease) {
		if ((disease.getName() != null)
				&& (this.name != null)
				&& (!this.name.equals(disease.getName()))) {
			return false;
		}
		if ((disease.getDiseaseClass() != null)
				&& (this.diseaseClass != null)
				&& (!this.diseaseClass.equals(disease.getDiseaseClass()))) {
			return false;
		}
		return true;
	}
	
}
