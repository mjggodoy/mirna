package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "biological_process", schema="mirna")
public class BiologicalProcess extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 45, unique = true)
	private String name;
	
	public BiologicalProcess() { }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
