package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.integration.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "disease", schema = "mirna" )
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

}
