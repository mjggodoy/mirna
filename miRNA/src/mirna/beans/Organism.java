package mirna.beans;

public class Organism extends ModelClass{

	private String specie;
	private String name;
	private String resource;

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

	
}