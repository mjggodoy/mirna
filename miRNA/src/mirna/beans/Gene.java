package mirna.beans;

import mirna.exception.ConflictException;

public class Gene extends ModelClass {

	private String name;// ok
	private String chromosome;// ok
	private String accessionumber;// ok
	private String geneId;// ok
	private String description;// ok
	private String year;// ok
	private String resource;// ok
	private String start_strand;// ok
	private String end_strand;// ok
	private String hgnc_symbol;
	private String location;// ok
	private String expression_site;// ok
	private String kegg_id;// ok
	private String arm;// ok
	private String distance;// OK
	private String pubmedId;
	private String journal;
	private Integer genePk;

	public Gene() {

		super();
	}

	public Gene(int pk, String name, String chromosome, String accessionumber,
			String geneId, String description, String year, String resource,
			String start_strand, String end_strand, String hgnc_symbol,
			String location, String expression_site, String kegg_id,
			String arm, String distance, String journal, String isoform) {
		super(pk);
		this.name = name;
		this.chromosome = chromosome;
		this.accessionumber = accessionumber;
		this.geneId = geneId;
		this.description = description;
		this.year = year;
		this.resource = resource;
		this.start_strand = start_strand;
		this.end_strand = end_strand;
		this.hgnc_symbol = hgnc_symbol;
		this.location = location;
		this.expression_site = expression_site;
		this.kegg_id = kegg_id;
		this.arm = arm;
		this.distance = distance;
		this.journal = journal;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getArm() {
		return arm;
	}

	public void setArm(String arm) {
		this.arm = arm;
	}

	public String getKegg_id() {
		return kegg_id;
	}

	public void setKegg_id(String kegg_id) {
		this.kegg_id = kegg_id;
	}

	public String getExpression_site() {
		return expression_site;
	}

	public void setExpression_site(String expression_site) {
		this.expression_site = expression_site;
	}

	public String getHgnc_symbol() {
		return hgnc_symbol;
	}

	public void setHgnc_symbol(String hgnc_symbol) {
		this.hgnc_symbol = hgnc_symbol;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAccessionumber() {
		return accessionumber;
	}

	public void setAccessionumber(String accessionumber) {
		this.accessionumber = accessionumber;
	}

	public String getStart_strand() {
		return start_strand;
	}

	public void setStart_strand(String start_strand) {
		this.start_strand = start_strand;
	}

	public String getEnd_strand() {
		return end_strand;
	}

	public void setEnd_strand(String end_strand) {
		this.end_strand = end_strand;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	public String getGeneId() {
		return geneId;
	}

	public void setGeneId(String geneId) {
		this.geneId = geneId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJournal() {
		return journal;
	}

	public void setJournal(String journal) {
		this.journal = journal;
	}

	public String getPubmedId() {
		return pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}
	
	
	public int checkConflict(Gene gene) {
		int res = 0;
		if (this.pk != null) {
			if (gene.getPk() == null)
				res++;
			else if (!this.pk.equals(gene.getPk()))
				return -1;
		}
		if (this.name != null) {
			if (gene.getName() == null)
				res++;
			else if (!this.name.equals(gene.getName()))
				return -1;
		}

		if (this.chromosome != null) {
			if (gene.getName() == null)
				res++;
			else if (!this.chromosome.equals(gene.getChromosome()))
				return -1;
		}

		if (this.accessionumber != null) {
			if (gene.getName() == null)
				res++;
			else if (!this.accessionumber.equals(gene.getAccessionumber()))
				return -1;
		}

		if (this.geneId != null) {
			if (gene.getGeneId() == null)
				res++;
			else if (!this.geneId.equals(gene.getGeneId()))
				return -1;
		}

		if (this.description != null) {
			if (gene.getDescription() == null)
				res++;
			else if (!this.description.equals(gene.getDescription()))
				return -1;
		}

		if (this.year != null) {
			if (gene.getYear() == null)
				res++;
			else if (!this.year.equals(gene.getYear()))
				return -1;
		}

		if (this.resource != null) {
			if (gene.getResource() == null)
				res++;
			else if (!this.resource.equals(gene.getResource()))
				return -1;
		}

		if (this.start_strand != null) {
			if (gene.getStart_strand() == null)
				res++;
			else if (!this.start_strand.equals(gene.getStart_strand()))
				return -1;
		}

		if (this.end_strand != null) {
			if (gene.getEnd_strand() == null)
				res++;
			else if (!this.end_strand.equals(gene.getEnd_strand()))
				return -1;
		}

		if (this.hgnc_symbol != null) {
			if (gene.getHgnc_symbol() == null)
				res++;
			else if (!this.hgnc_symbol.equals(gene.getHgnc_symbol()))
				return -1;
		}
		if (this.location != null) {
			if (gene.getLocation() == null)
				res++;
			else if (!this.location.equals(gene.getLocation()))
				return -1;
		}
		if (this.expression_site != null) {
			if (gene.getExpression_site() == null)
				res++;
			else if (!this.expression_site.equals(gene.getExpression_site()))
				return -1;
		}

		if (this.kegg_id != null) {
			if (gene.getKegg_id() == null)
				res++;
			else if (!this.kegg_id.equals(gene.getKegg_id()))
				return -1;
		}

		if (this.arm != null) {
			if (gene.getArm() == null)
				res++;
			else if (!this.arm.equals(gene.getArm()))
				return -1;
		}

		if (this.distance != null) {
			if (gene.getDistance() == null)
				res++;
			else if (!this.distance.equals(gene.getDistance()))
				return -1;
		}

		if (this.journal != null) {
			if (gene.getJournal() == null)
				res++;
			else if (!this.journal.equals(gene.getJournal()))
				return -1;
		}
		

		return res;
	}

	public void update(Gene gene) throws ConflictException {
		this.update(gene, true);
	}

	public void update(Gene gene, boolean checkConflict)
			throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(gene) == -1)
				throw new ConflictException(this, gene);
		}
		if (gene.getPk() != null)
			this.pk = gene.getPk();
		if (gene.getName() != null)
			this.name = gene.getName();
		if (gene.getGeneId() != null)
			this.name = gene.getGeneId();
		if (gene.getDescription() != null)
			this.description = gene.getDescription();
		if (gene.getChromosome() != null)
			this.chromosome = gene.getChromosome();
		if (gene.getYear() != null)
			this.year = gene.getYear();
		if (gene.getResource() != null)
			this.resource = gene.getResource();
		if (gene.getStart_strand() != null)
			this.start_strand = gene.getStart_strand();
		if (gene.getEnd_strand() != null)
			this.end_strand = gene.getEnd_strand();
		if (gene.getHgnc_symbol() != null)
			this.end_strand = gene.getEnd_strand();
		if (gene.getLocation() != null)
			this.location = gene.getLocation();
		if (gene.getExpression_site() != null)
			this.expression_site = gene.getExpression_site();
		if (gene.getKegg_id() != null)
			this.kegg_id = gene.getKegg_id();
		if (gene.getArm() != null)
			this.arm = gene.getArm();
		if (gene.getDistance() != null)
			this.distance = gene.getDistance();
		if (gene.getJournal() != null)
			this.journal = gene.getJournal();
	}

	@Override
	public String toString() {
		return "Gene [name=" + name + ", chromosome=" + chromosome
				+ ", accessionumber=" + accessionumber + ", geneId=" + geneId
				+ ", description=" + description + ", year=" + year
				+ ", resource=" + resource + ", start_strand=" + start_strand
				+ ", end_strand=" + end_strand + ", hgnc_symbol=" + hgnc_symbol
				+ ", location=" + location + ", expression_site="
				+ expression_site + ", kegg_id=" + kegg_id + ", arm=" + arm
				+ ", distance=" + distance + ", pubmedId=" + pubmedId
				+ ", journal=" + journal + ", genePk=" + genePk + ", pk=" + pk
				+ "]";
	}
}
