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

	public int checkConflict(Disease disease) {
		int res = 0;
		if (this.name!=null) {
			if (disease.getName()==null) res++; 
			else if (!this.name.equals(disease.getName())) return -1;
		}
		if (this.diseaseClass != null){
			if (disease.getDiseaseClass()==null) res++;
			else if (!this.diseaseClass.equals(disease.getDiseaseClass())) return -1;
		}
		return res;
	}

	@Override
	public String toString() {
		return "Disease [name=" + name + ", diseaseClass=" + diseaseClass
				+ ", pk=" + pk + "]";
	}
	
}
