package mirna.beans;

public class Target extends Transcript {
	
	private String cdsStart;//ok
	private String cdsEnd;//ok
	private String UTR5start;//ok
	private String UTR3end;//ok
	private String start_strand;//ok
	private String end_strand;//ok
	//private String biotype;
	private String sequence;//ok
	//private String transcriptID;
	private String name;//ok
	private String ExternalName;
	private String chromosome;//ok
	private String polarity;//ok
	private String binding_site_start;//ok
	private String binding_site_end;//ok
	private String repeated_motifs;//ok
	private String UTR3conservation_score;//ok
	private String region;//ok
	private String seed_match;//ok
	private String coordinates;//ok
	private String GC_proportion;//ok
	private String GU_proportion;
	private String resource;
	private String pubmed_id;
	private String site_conservation_score;
	
	public Target() { }

	
	
	public String getResource() {
		return resource;
	}



	public void setResource(String resource) {
		this.resource = resource;
	}



	public String getPubmed_id() {
		return pubmed_id;
	}



	public void setPubmed_id(String pubmed_id) {
		this.pubmed_id = pubmed_id;
	}



	public String getCdsStart() {
		return cdsStart;
	}

	public void setCdsStart(String cdsStart) {
		this.cdsStart = cdsStart;
	}

	public String getCdsEnd() {
		return cdsEnd;
	}

	public void setCdsEnd(String cdsEnd) {
		this.cdsEnd = cdsEnd;
	}

	public String getUTR5start() {
		return UTR5start;
	}

	public void setUTR5start(String uTR5start) {
		UTR5start = uTR5start;
	}

	public String getUTR3end() {
		return UTR3end;
	}

	public void setUTR3end(String uTR3end) {
		UTR3end = uTR3end;
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

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChromosome() {
		return chromosome;
	}

	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}

	public String getPolarity() {
		return polarity;
	}

	public void setPolarity(String polarity) {
		this.polarity = polarity;
	}

	public String getBinding_site_start() {
		return binding_site_start;
	}

	public void setBinding_site_start(String binding_site_start) {
		this.binding_site_start = binding_site_start;
	}

	public String getBinding_site_end() {
		return binding_site_end;
	}

	public void setBinding_site_end(String binding_site_end) {
		this.binding_site_end = binding_site_end;
	}

	public String getRepeated_motifs() {
		return repeated_motifs;
	}

	public void setRepeated_motifs(String repeated_motifs) {
		this.repeated_motifs = repeated_motifs;
	}

	public String getUTR3conservation_score() {
		return UTR3conservation_score;
	}

	public void setUTR3conservation_score(String uTR3conservation_score) {
		UTR3conservation_score = uTR3conservation_score;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSeed_match() {
		return seed_match;
	}

	public void setSeed_match(String seed_match) {
		this.seed_match = seed_match;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getGC_proportion() {
		return GC_proportion;
	}

	public void setGC_proportion(String gC_proportion) {
		GC_proportion = gC_proportion;
	}




	public String getExternalName() {
		return ExternalName;
	}



	public void setExternalName(String externalName) {
		ExternalName = externalName;
	}
	
	


	public String getGU_proportion() {
		return GU_proportion;
	}



	public void setGU_proportion(String gU_proportion) {
		GU_proportion = gU_proportion;
	}



	public String getSite_conservation_score() {
		return site_conservation_score;
	}



	public void setSite_conservation_score(String site_conservation_score) {
		this.site_conservation_score = site_conservation_score;
	}



	@Override
	public String toString() {
		return "Target [cdsStart=" + cdsStart + ", cdsEnd=" + cdsEnd
				+ ", UTR5start=" + UTR5start + ", UTR3end=" + UTR3end
				+ ", start_strand=" + start_strand + ", end_strand="
				+ end_strand + ", sequence=" + sequence + ", name=" + name
				+ ", ExternalName=" + ExternalName + ", chromosome="
				+ chromosome + ", polarity=" + polarity
				+ ", binding_site_start=" + binding_site_start
				+ ", binding_site_end=" + binding_site_end
				+ ", repeated_motifs=" + repeated_motifs
				+ ", UTR3conservation_score=" + UTR3conservation_score
				+ ", region=" + region + ", seed_match=" + seed_match
				+ ", coordinates=" + coordinates + ", GC_proportion="
				+ GC_proportion + ", GU_proportion=" + GU_proportion
				+ ", resource=" + resource + ", pubmed_id=" + pubmed_id
				+ ", site_conservation_score=" + site_conservation_score
				+ ", pk=" + pk + "]";
	}



	



	



	



	

	





	

	
	
}
