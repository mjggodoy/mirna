package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "disease", schema = "mirna" )
public class Disease extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 80, unique = true)
	private String name;

	@Column(name = "disease_class", nullable = true, length = 20)
	private String diseaseClass;
	
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
