package mirna.beans;

public class Gene {

	private String name;
	private String chromosome;
	private String geneId;
	private String description;
	private String refseq_name;


	public String getRefseq_name() {
		return refseq_name;
	}

	public void setRefseq_name(String refseq_name) {
		this.refseq_name = refseq_name;
	}

	public Gene() {}

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
	
}
