package mirna.beans;

import mirna.exception.ConflictException;

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
	protected Integer gene_id;
	
	
	
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

	

	public Integer getGene_id() {
		return gene_id;
	}


	public void setGene_id(Integer gene_id) {
		this.gene_id = gene_id;
	}

	
	
	public void update(Mutation mutation) throws ConflictException {
		this.update(mutation, true);
	}
	
	public void update(Mutation mutation, boolean checkConflict)
			throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(mutation) == -1)
				throw new ConflictException(this, mutation);
		}
		if (mutation.getPk() != null){
			this.pk = mutation.getPk();
		}
		
		if (mutation.getChromosome() != null){
			this.chromosome = mutation.getChromosome();
		}
		
		if (mutation.getCoordinates() != null){
			this.coordinates = mutation.getCoordinates();
		}
		
		if (mutation.getDescription() != null){
			this.description = mutation.getDescription();
		}
		
		if (mutation.getDistance() != null){
			this.description = mutation.getDistance();
		}
		
		if (mutation.getGene_id() != null){
			this.gene_id = mutation.getGene_id();
		}
		
		if (mutation.getJournal() != null){
			this.journal = mutation.getJournal();
		}
		
		if (mutation.getOrientation() != null){
			this.orientation = mutation.getOrientation();
		}
		
		if (mutation.getPubmed_id() != null){
			this.pubmed_id = mutation.getPubmed_id();
		}
		
		if (mutation.getResource() != null){
			this.resource = mutation.getResource();
		}
		
		if (mutation.getSpecie() != null){
			this.specie = mutation.getSpecie();
		}
		
		if (mutation.getYear() != null){
			this.year = mutation.getYear();
		}
		
		}
	
	public int checkConflict(Mutation mutation) {
		int res = 0;
		if (this.pk != null) {
			if (mutation.getPk() == null)
				res++;
			else if (!this.pk.equals(mutation.getPk()))
				return -1;
		}
		
		
		if (this.chromosome != null) {
			if (mutation.getChromosome() == null)
				res++;
			else if (!this.chromosome.equals(mutation.getChromosome()))
				return -1;
		}
		
		if (this.coordinates != null) {
			if (mutation.getCoordinates() == null)
				res++;
			else if (!this.coordinates.equals(mutation.getCoordinates()))
				return -1;
		}
		
		if (this.description != null) {
			if (mutation.getDescription() == null)
				res++;
			else if (!this.description.equals(mutation.getDescription()))
				return -1;
		}
		
		if (this.distance != null) {
			if (mutation.getDistance() == null)
				res++;
			else if (!this.distance.equals(mutation.getDistance()))
				return -1;
		}
		
		if (this.journal != null) {
			if (mutation.getJournal() == null)
				res++;
			else if (!this.journal.equals(mutation.getJournal()))
				return -1;
		}
		
		if (this.resource != null) {
			if (mutation.getResource() == null)
				res++;
			else if (!this.resource.equals(mutation.getResource()))
				return -1;
		}
		
		if (this.specie != null) {
			if (mutation.getSpecie() == null)
				res++;
			else if (!this.specie.equals(mutation.getSpecie()))
				return -1;
		}
		
		if (this.year != null) {
			if (mutation.getYear() == null)
				res++;
			else if (!this.year.equals(mutation.getYear()))
				return -1;
		}
		
		if (this.gene_id != null) {
			if (mutation.getGene_id() == null)
				res++;
			else if (!this.gene_id.equals(mutation.getGene_id()))
				return -1;
		}
		
		
		return res;
		
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