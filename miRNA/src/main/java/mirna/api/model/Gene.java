package mirna.api.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "gene", schema = "mirna")
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
	
	@Column(name = "expression_site", nullable = true, length = 90, unique = false)
	private String expression_site;// ok
	
	@Column(name = "kegg_id", nullable = true, length = 45, unique = false)
	private String kegg_id;// ok
	
	@Column(name = "arm", nullable = true, length = 45, unique = false)
	private String arm;// ok
	
	@Column(name = "distance", nullable = true, length = 45, unique = false)
	private String distance;// OK
	
	@Column(name = "organism_pk")
	private Integer organism_pk;
	
	@Column(name = "ensembl_id", nullable = true, length = 45, unique = false)
	private String ensembl_id;// ok
	
	@Column(name = "hgnc_id", nullable = true, length = 45, unique = false)
	private String hgnc_id;
	
	// XUXA
//	@Column(name = "chromosome", nullable = true, length = 45, unique = false)
//	private Integer transcript_id;

	public Gene() {
		super();
	}
	
	@ManyToMany(mappedBy="genes")
	private Set<Transcript> transcripts;

	public String getDistance() {
		return distance;
	}


	public String getArm() {
		return arm;
	}

	

	public String getKegg_id() {
		return kegg_id;
	}

	
	public String getExpression_site() {
		return expression_site;
	}



	public String getHgnc_symbol() {
		return hgnc_symbol;
	}

	

	public String getLocation() {
		return location;
	}


	public String getAccessionumber() {
		return accessionumber;
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

	public String getResource() {
		return resource;
	}

	public String getYear() {
		return year;
	}

	
	public String getChromosome() {
		return chromosome;
	}

	public String getGeneId() {
		return geneId;
	}

	

	public String getDescription() {
		return description;
	}

	

	public String getName() {
		return name;
	}

	public Integer getOrganism_pk() {
		return organism_pk;
	}

	public String getEnsembl_id() {
		return ensembl_id;
	}

	public String getHgnc_id() {
		return hgnc_id;
	}

	public Set<Transcript> getTranscripts() {
		return transcripts;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessionumber == null) ? 0 : accessionumber.hashCode());
		result = prime * result + ((arm == null) ? 0 : arm.hashCode());
		result = prime * result
				+ ((chromosome == null) ? 0 : chromosome.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((distance == null) ? 0 : distance.hashCode());
		result = prime * result
				+ ((end_strand == null) ? 0 : end_strand.hashCode());
		result = prime * result
				+ ((ensembl_id == null) ? 0 : ensembl_id.hashCode());
		result = prime * result
				+ ((expression_site == null) ? 0 : expression_site.hashCode());
		result = prime * result + ((geneId == null) ? 0 : geneId.hashCode());
		result = prime * result + ((hgnc_id == null) ? 0 : hgnc_id.hashCode());
		result = prime * result
				+ ((hgnc_symbol == null) ? 0 : hgnc_symbol.hashCode());
		result = prime * result + ((kegg_id == null) ? 0 : kegg_id.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((organism_pk == null) ? 0 : organism_pk.hashCode());
		result = prime * result
				+ ((resource == null) ? 0 : resource.hashCode());
		result = prime * result
				+ ((start_strand == null) ? 0 : start_strand.hashCode());
		result = prime * result
				+ ((transcripts == null) ? 0 : transcripts.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Gene other = (Gene) obj;
		if (accessionumber == null) {
			if (other.accessionumber != null)
				return false;
		} else if (!accessionumber.equals(other.accessionumber))
			return false;
		if (arm == null) {
			if (other.arm != null)
				return false;
		} else if (!arm.equals(other.arm))
			return false;
		if (chromosome == null) {
			if (other.chromosome != null)
				return false;
		} else if (!chromosome.equals(other.chromosome))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		if (end_strand == null) {
			if (other.end_strand != null)
				return false;
		} else if (!end_strand.equals(other.end_strand))
			return false;
		if (ensembl_id == null) {
			if (other.ensembl_id != null)
				return false;
		} else if (!ensembl_id.equals(other.ensembl_id))
			return false;
		if (expression_site == null) {
			if (other.expression_site != null)
				return false;
		} else if (!expression_site.equals(other.expression_site))
			return false;
		if (geneId == null) {
			if (other.geneId != null)
				return false;
		} else if (!geneId.equals(other.geneId))
			return false;
		if (hgnc_id == null) {
			if (other.hgnc_id != null)
				return false;
		} else if (!hgnc_id.equals(other.hgnc_id))
			return false;
		if (hgnc_symbol == null) {
			if (other.hgnc_symbol != null)
				return false;
		} else if (!hgnc_symbol.equals(other.hgnc_symbol))
			return false;
		if (kegg_id == null) {
			if (other.kegg_id != null)
				return false;
		} else if (!kegg_id.equals(other.kegg_id))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (organism_pk == null) {
			if (other.organism_pk != null)
				return false;
		} else if (!organism_pk.equals(other.organism_pk))
			return false;
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		if (start_strand == null) {
			if (other.start_strand != null)
				return false;
		} else if (!start_strand.equals(other.start_strand))
			return false;
		if (transcripts == null) {
			if (other.transcripts != null)
				return false;
		} else if (!transcripts.equals(other.transcripts))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}
	
	
	
}
