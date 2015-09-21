package mirna.beans;

import javax.persistence.Column;

import mirna.exception.ConflictException;

public class Mutation extends ModelClass {


	@Column(name = "specie", nullable = true, length = 45) //mutation_pk puede ser nulo
	private String specie;

	@Column(name = "chromosome", nullable = true)
	private String chromosome;

	@Column(name = "coordinates", nullable = true, length = 45)
	private String coordinates;

	@Column(name = "orientation", nullable = true, length = 45) //mutation_pk puede ser nulo
	private String orientation;

	@Column(name = "distance", nullable = true, length = 45) //mutation_pk puede ser nulo
	private String distance;

	@Column(name = "gene_pk", nullable = true, length = 45) //mutation_pk puede ser nulo
	private String gene_pk;



	public Mutation() {
		super();
	}


	public Mutation(int pk, String specie, String chromosome, String coordinates, String orientation, String distance, String gene_pk) {
		super(pk);
		this.specie = specie;
		this.chromosome = chromosome;
		this.coordinates = coordinates;
		this.orientation = orientation;
		this.distance = distance;
		this.gene_pk = gene_pk;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
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


	public String getGene_pk() {
		return gene_pk;
	}


	public void setGene_pk(String gene_pk) {
		this.gene_pk = gene_pk;
	}


	public void update(Mutation mutation) throws ConflictException {
		this.update(mutation, true);
	}

	public void update(Mutation mutation, boolean checkConflict) throws ConflictException {
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

		if (mutation.getDistance()  != null){
			this.distance = mutation.getDistance();
		}

		if (mutation.getSpecie()  != null){
			this.specie = mutation.getSpecie();
		}

		if (mutation.getGene_pk()  != null){
			this.gene_pk = mutation.getGene_pk();
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

		if (this.distance != null) {
			if (mutation.getDistance() == null)
				res++;
			else if (!this.distance.equals(mutation.getDistance()))
				return -1;
		}

		if (this.specie != null) {
			if (mutation.getSpecie() == null)
				res++;
			else if (!this.specie.equals(mutation.getSpecie()))
				return -1;
		}


		if (this.gene_pk != null) {
			if (mutation.getGene_pk() == null)
				res++;
			else if (!this.gene_pk.equals(mutation.getGene_pk()))
				return -1;
		}

		if (this.orientation != null) {
			if (mutation.getOrientation() == null)
				res++;
			else if (!this.orientation.equals(mutation.getOrientation()))
				return -1;
		}

		return res;

	}


	@Override
	public String toString() {
		return "Mutation [specie=" + specie + ", chromosome=" + chromosome
				+ ", coordinates=" + coordinates + ", orientation="
				+ orientation + ", distance=" + distance + ", gene_pk="
				+ gene_pk + ", pk=" + pk + "]";
	}

}