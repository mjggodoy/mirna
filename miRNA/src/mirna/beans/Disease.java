package mirna.beans;

public class Disease extends ModelClass {
	
	private String name;//ok
	//private String diseaseSub;
	private String diseaseClass;//ok
	//private String phenomicId;
	private String description; //ok
	private String pubmedId;//ok
	private String tissue; //ok
	private String id; //ok
	
	public Disease() {}
	
	public Disease(int pka, String name, String diseaseClass, String phenomicId,
			String description, String pubmedId, String tissue, String id) {
		super(pka);
		this.name = name;
		//this.diseaseSub = diseaseSub;
		this.diseaseClass = diseaseClass;
		//this.phenomicId = phenomicId;
		this.description = description;
		this.pubmedId = pubmedId;
		this.tissue = tissue;
		this.id = id;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPubmedId() {
		return pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	public String getTissue() {
		return tissue;
	}

	public void setTissue(String tissue) {
		this.tissue = tissue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//public String getDiseaseSub() {
		//return diseaseSub;
	//}

	//public void setDiseaseSub(String diseaseSub) {
		//this.diseaseSub = diseaseSub;
	//}

	public String getDiseaseClass() {
		return diseaseClass;
	}

	public void setDiseaseClass(String diseaseClass) {
		this.diseaseClass = diseaseClass;
	}

	//public String getPhenomicId() {
		//return phenomicId;
	//}

	//public void setPhenomicId(String phenomicId) {
		//this.phenomicId = phenomicId;
	//}
	
	public boolean checkConflict(Disease disease) {
		if ((disease.getName() != null)
				&& (this.name != null)
				&& (!this.name.equals(disease.getName()))) {
			return false;
		}
		//if ((disease.getDiseaseSub() != null)
			//	&& (this.diseaseSub != null)
				//&& (!this.diseaseSub.equals(disease.getDiseaseSub()))) {
			//return false;
		//}
		if ((disease.getDiseaseClass() != null)
				&& (this.diseaseClass != null)
				&& (!this.diseaseClass.equals(disease.getDiseaseClass()))) {
			return false;
		}
		//if ((disease.getPhenomicId() != null)
			//	&& (this.phenomicId != null)
			//	&& (!this.phenomicId.equals(disease.getPhenomicId()))) {
			//return false;
		//}
		if ((disease.getDescription() != null)
				&& (this.description != null)
				&& (!this.description.equals(disease.getDescription()))) {
			return false;
		}
		if ((disease.getPubmedId() != null)
				&& (this.pubmedId != null)
				&& (!this.pubmedId.equals(disease.getPubmedId()))) {
			return false;
		}
		if ((disease.getTissue() != null)
				&& (this.tissue != null)
				&& (!this.tissue.equals(disease.getTissue()))) {
			return false;
		}
		return true;
	}
	
}
