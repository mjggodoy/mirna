package mirna.beans;

public class Mutation extends ModelClass {
	
	
	private String specie;
	private String chromosome;
	private String coordinates;
	private String orientation;
	private String distance;
	
	
	
	public Mutation() {
		super();
		}
	
	
	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
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
	

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getDistance() {
		return distance;
	}


	public void setDistance(String distance) {
		this.distance = distance;
	}


	@Override
	public String toString() {
		return "Mutation [specie=" + specie + ", chromosome=" + chromosome
				+ ", coordinates=" + coordinates + ", orientation="
				+ orientation + ", distance=" + distance + ", pk=" + pk
				+ ", getOrientation()=" + getOrientation()
				+ ", getChromosome()=" + getChromosome() + ", getSpecie()="
				+ getSpecie() + ", getCoordinates()=" + getCoordinates()
				+ ", getDistance()=" + getDistance() + ", getPk()=" + getPk()
				+ ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}