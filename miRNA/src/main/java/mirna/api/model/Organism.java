package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.integration.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "organism", schema = "mirna")

public class Organism extends ModelClass {

	@Column(name = "specie", nullable = true, length = 45, unique = false)
	private String specie;
	
	@Column(name = "name", nullable = false, length = 40, unique = true)
	private String name;
	
	@Column(name = "resource", nullable = true, length = 80, unique = false)
	private String resource;
	
	@Column(name = "short_name", nullable = true, length = 45, unique = false)
	private String shortName;

	public Organism() {
		super();
	}
	
	public String getResource() {
		return resource;
	}

	

	public String getName() {
		return name;
	}

	

	public String getSpecie() {
		return specie;
	}

	

	public String getShortName() {
		return shortName;
	}



	
	
}