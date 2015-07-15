package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "gene")
public class Gene extends ModelClass {

	//TODO: HACER QUE GENE NO PUEDA TENER NAME NULL
	@Column(name = "name", nullable = true, length = 45, unique = false)
	private String name;// ok
	
	@Column(name = "chromosome", nullable = true, length = 45, unique = false)
	private String chromosome;// ok
	
	@Column(name = "accession_number", nullable = true, length = 45, unique = false)
	private String accessionumber;// ok
	
	@Column(name = "id", nullable = true, length = 100, unique = false)
	private String geneId;// ok
	
	@Column(name = "description", nullable = true, length = 45, unique = false)
	private String description;// ok
	
	@Column(name = "year", nullable = true, length = 4, unique = false)
	private String year;// ok
	
	@Column(name = "resource", nullable = true, length = 45, unique = false)
	private String resource;// ok
	
	@Column(name = "start_strand", nullable = true, length = 45, unique = false)
	private String start_strand;// ok
	
	@Column(name = "end_strand", nullable = true, length = 45, unique = false)
	private String end_strand;// ok
	
	@Column(name = "hgnc_symbol", nullable = true, length = 45, unique = false)
	private String hgnc_symbol;
	
	@Column(name = "location", nullable = true, length = 45, unique = false)
	private String location;// ok
	
	@Column(name = "expression_site", nullable = true, length = 45, unique = false)
	private String expression_site;// ok
	
	@Column(name = "kegg_id", nullable = true, length = 45, unique = false)
	private String kegg_id;// ok
	
	@Column(name = "arm", nullable = true, length = 45, unique = false)
	private String arm;// ok
	
	@Column(name = "distance", nullable = true, length = 45, unique = false)
	private String distance;// OK
	
	@Column(name = "organism_pk")
	private Integer organism;
	
	// XUXA
//	@Column(name = "chromosome", nullable = true, length = 45, unique = false)
//	private Integer transcript_id;

	public Gene() {
		super();
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

	public Integer getOrganism() {
		return organism;
	}

	public void setOrganism(Integer organism) {
		this.organism = organism;
	}

//	public Integer getTranscript_id() {
//		return transcript_id;
//	}
//
//	public void setTranscript_id(Integer transcript_id) {
//		this.transcript_id = transcript_id;
//	}

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
		
		if (this.organism != null) {
			if (gene.getOrganism() == null)
				res++;
			else if (!this.organism.equals(gene.getOrganism()))
				return -1;
		}

//		if (this.transcript_id != null) {
//			if (gene.getTranscript_id()  == null)
//				res++;
//			else if (!this.transcript_id.equals(gene.getTranscript_id()))
//				return -1;
//		}
		

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
			this.geneId = gene.getGeneId();
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
			this.hgnc_symbol = gene.getHgnc_symbol();
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
		if (gene.getOrganism() != null)
			this.organism = gene.getOrganism();
//		if (gene.getTranscript_id() != null)
//			this.transcript_id = gene.getTranscript_id();
	
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
				+ ", distance=" + distance + ", organism=" + organism + ", pk="
				+ pk + "]";
	}

	
}
