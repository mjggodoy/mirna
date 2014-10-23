package mirna.beans;

public class Target extends Transcript {
	
	private String cdsStart;//ok
	private String cdsEnd;//ok
	private String UTR5start;//ok
	private String UTR3end;//ok
	private String start_strand;//ok
	private String end_strand;//ok
	private String biotype;
	private String sequence;//ok
	private String transcriptID;
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
	
	public Target() { super();
	}



	
	
	


	public Target(int pk,String cdsStart, String cdsEnd, String uTR5start,
			String uTR3end, String start_strand, String end_strand,
			String biotype, String sequence, String transcriptID, String name,
			String externalName, String chromosome, String polarity,
			String binding_site_start, String binding_site_end,
			String repeated_motifs, String uTR3conservation_score,
			String region, String seed_match, String coordinates,
			String gC_proportion) {
		super(pk);
		this.cdsStart = cdsStart;
		this.cdsEnd = cdsEnd;
		UTR5start = uTR5start;
		UTR3end = uTR3end;
		this.start_strand = start_strand;
		this.end_strand = end_strand;
		this.biotype = biotype;
		this.sequence = sequence;
		this.transcriptID = transcriptID;
		this.name = name;
		ExternalName = externalName;
		this.chromosome = chromosome;
		this.polarity = polarity;
		this.binding_site_start = binding_site_start;
		this.binding_site_end = binding_site_end;
		this.repeated_motifs = repeated_motifs;
		UTR3conservation_score = uTR3conservation_score;
		this.region = region;
		this.seed_match = seed_match;
		this.coordinates = coordinates;
		GC_proportion = gC_proportion;
	}








	public String getGC_proportion() {
		return GC_proportion;
	}





	public void setGC_proportion(String gC_proportion) {
		GC_proportion = gC_proportion;
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
	
	
	public String getPolarity() {
		return polarity;
	}


	public void setPolarity(String polarity) {
		this.polarity = polarity;
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





	public String getChromosome() {
		return chromosome;
	}



	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}




	public String getExternalName() {
		return ExternalName;
	}

	public void setExternalName(String externalName) {
		ExternalName = externalName;
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

	public String getBiotype() {
		return biotype;
	}

	public void setBiotype(String biotype) {
		this.biotype = biotype;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getTranscriptID() {
		return transcriptID;
	}

	public void setTranscriptID(String transcriptID) {
		this.transcriptID = transcriptID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}




	
	
}
