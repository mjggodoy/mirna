package mirna.beans;

public class Mutation extends ModelClass {
	
	
	protected String specie;
	protected String chromosome;
	protected String coordinates;
	protected String orientation;
	protected String distance;
	protected String journal;
	protected String year;
	protected String description;
	protected String pubmed_id;
	protected String resource;
	
	
	
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


	public String getJournal() {
		return journal;
	}


	public void setJournal(String journal) {
		this.journal = journal;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPubmed_id() {
		return pubmed_id;
	}


	public void setPubmed_id(String pubmed_id) {
		this.pubmed_id = pubmed_id;
	}


	public String getResource() {
		return resource;
	}


	public void setResource(String resource) {
		this.resource = resource;
	}


	@Override
	public String toString() {
		return "Mutation [specie=" + specie + ", chromosome=" + chromosome
				+ ", coordinates=" + coordinates + ", orientation="
				+ orientation + ", distance=" + distance + ", journal="
				+ journal + ", year=" + year + ", description=" + description
				+ ", pubmed_id=" + pubmed_id + ", resource=" + resource
				+ ", pk=" + pk + "]";
	}


	



	
}