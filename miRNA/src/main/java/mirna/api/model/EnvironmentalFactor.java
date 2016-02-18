package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "environmental_factor", schema = "mirna")
public class EnvironmentalFactor extends ModelClass  {

	@Column(name = "name", nullable = false, length = 300)
	private String name;

	public EnvironmentalFactor() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
