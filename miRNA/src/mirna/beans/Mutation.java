package mirna.beans;

public class Mutation extends ModelClass {
	
	
	private String specie;
	private String chromosome;
	
	
	
	public Mutation() {
		super();
		}
	
	
	

	public Mutation(int pk, String specie, String chromosome) {
		super(pk);
		this.specie = specie;
		this.chromosome = chromosome;
	}




	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	public String getSpecie() {
		return specie;
	}

	public void setSpecie(String specie) {
		this.specie = specie;
	}

	
}