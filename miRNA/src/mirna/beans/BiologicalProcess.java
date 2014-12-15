package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "biological_process")
public class BiologicalProcess extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 45, unique = true)
	private String name;
	
	public BiologicalProcess() { }

	public BiologicalProcess(int pk, String name) {
		super(pk);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BiologicalProcess [name=" + name + ", pk=" + pk + "]";
	}


}
