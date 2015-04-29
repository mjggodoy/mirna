package mirna.beans;

import mirna.exception.ConflictException;

public class Organism extends ModelClass{

	private String specie;
	private String name;
	private String resource;
	private String short_name;

	public Organism() {super();}

	
	
	
	public Organism(int pk,String specie, String name, String resource) {
		super(pk);
		this.specie = specie;
		this.name = name;
		this.resource = resource;
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


	

	public String getShort_name() {
		return short_name;
	}




	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	
	public int checkConflict(Organism specie) {
		int res = 0;
		
		if (this.pk!=null) {
			if (specie.getPk()==null) res++; // res = 1
			else if (!this.pk.equals(specie.getPk())) return -1;
		}
		
		if (this.name!=null) {
			if (specie.getName()==null) res++; 
			else if (!this.name.equals(specie.getName())) return -1;
		}
		
		return res;
	}
	
	
	public void update(Organism specie) throws ConflictException {
		this.update(specie, true);
	}
	
	
	public void update(Organism specie, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(specie)==-1) throw new ConflictException(this, specie);
		}
		if (specie.getPk()!=null) this.pk = specie.getPk();
		if (specie.getName()!=null) this.name = specie.getName();
		
	}



	@Override
	public String toString() {
		return "Organism [specie=" + specie + ", name=" + name + ", resource="
				+ resource + ", short_name=" + short_name + ", pk=" + pk + "]";
	}
	
	
}