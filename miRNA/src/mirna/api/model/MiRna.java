package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mirna", schema = "mirna")
public class MiRna extends ModelClass {

	@Column(name = "name", nullable = false, length = 80, unique = true)
	protected String name;

	@Column(name = "arm", nullable = true, length = 5)
	private String arm;

	@Column(name = "resource", nullable = true, length = 45)
	private String resource;


	public MiRna() {}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public String getArm() {
		return arm;
	}

	public void setArm(String arm) {
		this.arm = arm;
	}

}