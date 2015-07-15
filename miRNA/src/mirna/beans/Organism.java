package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Table(name = "organism")
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

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public int checkConflict(Organism specie) {
		int res = 0;

		if (this.pk != null) {
			if (specie.getPk() == null)
				res++; // res = 1
			else if (!this.pk.equals(specie.getPk()))
				return -1;
		}

		if (this.name != null) {
			if (specie.getName() == null)
				res++;
			else if (!this.name.equals(specie.getName()))
				return -1;
		}
		
		if (this.shortName != null) {
			if (specie.getShortName() == null)
				res++;
			else if (!this.shortName.equals(specie.getShortName()))
				return -1;
		}
		
		if (this.specie != null) {
			if (specie.getSpecie() == null)
				res++;
			else if (!this.specie.equals(specie.getSpecie()))
				return -1;
		}
		
		if (this.resource != null) {
			if (specie.getResource() == null)
				res++;
			else if (!this.resource.equals(specie.getResource()))
				return -1;
		}

		return res;
	}

	public void update(Organism specie) throws ConflictException {
		this.update(specie, true);
	}

	public void update(Organism specie, boolean checkConflict)
			throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(specie) == -1)
				throw new ConflictException(this, specie);
		}
		if (specie.getPk() != null)
			this.pk = specie.getPk();
		if (specie.getName() != null)
			this.name = specie.getName();
		if (specie.getShortName() != null)
			this.shortName = specie.getShortName();
		if (specie.getSpecie() != null)
			this.specie = specie.getSpecie();
		if (specie.getResource() != null)
			this.resource = specie.getResource();
	}

	@Override
	public String toString() {
		return "Organism [specie=" + specie + ", name=" + name + ", resource="
				+ resource + ", shortName=" + shortName + ", pk=" + pk + "]";
	}
	
}