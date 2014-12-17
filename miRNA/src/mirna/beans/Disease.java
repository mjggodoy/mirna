package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "disease")
public class Disease extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 80, unique = true)
	private String name;

	@Column(name = "disease_class", nullable = true, length = 20)
	private String diseaseClass;
	
	// TODO : Crear una nueva tabla relacionada con Disease
	//private String type_tumour;
	
//	private String diseaseSub;
//	private String phenomicId;
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
		if (this.pk!=null) {
			if (disease.getPk()==null) res++; 
			else if (!this.pk.equals(disease.getPk())) return -1;
		}
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
	
	public void update(Disease disease) throws ConflictException {
		this.update(disease, true);
	}
	
	public void update(Disease disease, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(disease)==-1) throw new ConflictException(this, disease);
		}
		if (disease.getPk()!=null) this.pk = disease.getPk();
		if (disease.getName()!=null) this.name = disease.getName();
		if (disease.getDiseaseClass()!=null) this.diseaseClass = disease.getDiseaseClass();
	}

	@Override
	public String toString() {
		return "Disease [name=" + name + ", diseaseClass=" + diseaseClass
				+ ", pk=" + pk + "]";
	}

	
}
