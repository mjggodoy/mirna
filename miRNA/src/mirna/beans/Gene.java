package mirna.beans;

public class Gene extends ModelClass {

	private String name;//ok
	private String chromosome;//ok
	private String accessionumber;//ok
	private String geneId;//ok
	private String description;//ok
	//private String refseq_name;
	private String year;//ok
	private String resource;//ok
	private String start_strand;//ok
	private String end_strand;//ok
	private String hgnc_symbol;
	private String location;//ok
	private String expression_site;//ok
	private String kegg_id;//ok
	private String arm;//ok
	private String distance;//OK
	private String pubmedId;
	private String journal;
	
	public Gene() {		
		
		super();
		}



	public Gene(int pk,String name, String chromosome, String accessionumber,
			String geneId, String description, String year, String resource,
			String start_strand, String end_strand, String hgnc_symbol,
			String location, String expression_site, String kegg_id,
			String arm, String distance, String journal) {
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



	//public String getRefseq_name() {
		//return refseq_name;
	//}

	//public void setRefseq_name(String refseq_name) {
		//this.refseq_name = refseq_name;
	//}

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
				+ ", journal=" + journal + "]";
	}



	
	
	
	
}
