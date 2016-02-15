package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "gene", schema = "mirna")
public class Gene extends ModelClass {

	@Column(name = "name", nullable = false, length = 45, unique = false)
	private String name;// ok
	
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
	
	public Gene() {
		super();
	}

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
	
}
