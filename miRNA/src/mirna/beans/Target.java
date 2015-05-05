package mirna.beans;

import mirna.exception.ConflictException;

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

	
	public void update(Target target) throws ConflictException {
		this.update(target, true);
	}

	public void update(Target target, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(target)==-1) throw new ConflictException(this, target);
		}
		if (target.getPk()!=null) this.pk = target.getPk();
		if (target.getCdsStart()!=null) this.cdsStart = target.getCdsStart();
		if (target.getCdsEnd()!=null) this.cdsEnd = target.getCdsEnd();
		if (target.getUTR5start()!=null) this.UTR5start = target.getUTR5start();
		if (target.getUTR3end()!=null) this.UTR3end = target.getUTR3end();
		if (target.getStart_strand()!=null) this.start_strand = target.getStart_strand();
		if (target.getEnd_strand()!=null) this.end_strand = target.getEnd_strand();
		if (target.getSequence()!=null) this.sequence = target.getSequence();
		if (target.getName()!=null) this.name = target.getName();
		if (target.getExternalName()!=null) this.ExternalName = target.getExternalName();
		if (target.getChromosome()!=null) this.chromosome = target.getChromosome();
		if (target.getPolarity()!=null) this.polarity = target.getPolarity();
		if (target.getBinding_site_start()!=null) this.binding_site_start = target.getBinding_site_start();
		if (target.getBinding_site_end()!=null) this.binding_site_end = target.getBinding_site_end();
		if (target.getRepeated_motifs() != null) this.repeated_motifs = target.getRepeated_motifs();
		if (target.getUTR3conservation_score() != null) this.UTR3conservation_score = target.getUTR3conservation_score();
		if (target.getRegion() != null) this.region = target.getRegion();
		if (target.getSeed_match() != null) this.seed_match = target.getSeed_match();
		if (target.getCoordinates() != null) this.coordinates = target.getCoordinates();
		if (target.getGC_proportion() != null) this.GC_proportion = target.getGC_proportion();
		if (target.getGU_proportion() != null) this.GU_proportion = target.getGU_proportion();
		if (target.getResource() != null) this.resource = target.getResource();
		if (target.getPubmed_id() != null) this.pubmed_id = target.getPubmed_id();
		if (target.getSite_conservation_score() != null) this.site_conservation_score = target.getSite_conservation_score();
	}
	
	
	public int checkConflict(Target target) {
		int res = 0;
		
		if (this.pk!=null) {
			if (target.getPk()==null) res++; 
			else if (!this.pk.equals(target.getPk())) return -1;
		}
		if (this.cdsStart!=null) {
			if (target.getCdsStart()==null) res++; 
			else if (!this.cdsStart.equals(target.getCdsStart())) return -1;
		}
		if (this.cdsEnd!=null) {
			if (target.getCdsEnd()==null) res++; 
			else if (!this.cdsEnd.equals(target.getCdsEnd())) return -1;
		}
		
		if (this.UTR5start!=null) {
			if (target.getUTR5start()==null) res++; 
			else if (!this.UTR5start.equals(target.getUTR5start())) return -1;
		}
		
		if (this.UTR3end!=null) {
			if (target.getUTR3end()==null) res++; 
			else if (!this.UTR3end.equals(target.getUTR3end())) return -1;
		}
		
		if (this.start_strand!=null) {
			if (target.getStart_strand()==null) res++; 
			else if (!this.start_strand.equals(target.getStart_strand())) return -1;
		}
		
		if (this.end_strand!=null) {
			if (target.getEnd_strand()==null) res++; 
			else if (!this.end_strand.equals(target.getEnd_strand())) return -1;
		}
		
		if (this.sequence!=null) {
			if (target.getSequence()==null) res++; 
			else if (!this.sequence.equals(target.getSequence())) return -1;
		}
		
		if (this.name!=null) {
			if (target.getName()==null) res++; 
			else if (!this.name.equals(target.getName())) return -1;
		}
		
		if (this.ExternalName!=null) {
			if (target.getExternalName()==null) res++; 
			else if (!this.ExternalName.equals(target.getExternalName())) return -1;
		}
		
		if (this.chromosome!=null) {
			if (target.getChromosome()==null) res++; 
			else if (!this.chromosome.equals(target.getChromosome())) return -1;
		}
		
		if (this.polarity!=null) {
			if (target.getPolarity()==null) res++; 
			else if (!this.polarity.equals(target.getPolarity())) return -1;
		}
		
		if (this.binding_site_start!=null) {
			if (target.getBinding_site_start()==null) res++; 
			else if (!this.binding_site_start.equals(target.getBinding_site_start())) return -1;
		}
		
		if (this.binding_site_end!=null) {
			if (target.getBinding_site_end()==null) res++; 
			else if (!this.binding_site_end.equals(target.getBinding_site_end())) return -1;
		}
		
		if (this.repeated_motifs!=null) {
			if (target.getRepeated_motifs()==null) res++; 
			else if (!this.repeated_motifs.equals(target.getRepeated_motifs())) return -1;
		}
		
		if (this.UTR3conservation_score!=null) {
			if (target.getUTR3conservation_score()==null) res++; 
			else if (!this.UTR3conservation_score.equals(target.getUTR3conservation_score())) return -1;
		}
		
		if (this.region!=null) {
			if (target.getRegion()==null) res++; 
			else if (!this.region.equals(target.getRegion())) return -1;
		}
		
		if (this.seed_match!=null) {
			if (target.getSeed_match()==null) res++; 
			else if (!this.seed_match.equals(target.getSeed_match())) return -1;
		}
		
		if (this.coordinates!=null) {
			if (target.getCoordinates()==null) res++; 
			else if (!this.coordinates.equals(target.getCoordinates())) return -1;
		}
		
		if (this.GC_proportion!=null) {
			if (target.getGC_proportion()==null) res++; 
			else if (!this.GC_proportion.equals(target.getGC_proportion())) return -1;
		}
		
		if (this.GU_proportion!=null) {
			if (target.getGU_proportion()==null) res++; 
			else if (!this.GU_proportion.equals(target.getGU_proportion())) return -1;
		}
		
		if (this.resource!=null) {
			if (target.getResource()==null) res++; 
			else if (!this.resource.equals(target.getResource())) return -1;
		}
		
		if (this.pubmed_id!=null) {
			if (target.getPubmed_id()==null) res++; 
			else if (!this.pubmed_id.equals(target.getPubmed_id())) return -1;
		}
		
		if (this.pubmed_id!=null) {
			if (target.getPubmed_id()==null) res++; 
			else if (!this.pubmed_id.equals(target.getPubmed_id())) return -1;
		}
		
		if (this.site_conservation_score!=null) {
			if (target.getSite_conservation_score()==null) res++; 
			else if (!this.site_conservation_score.equals(target.getSite_conservation_score())) return -1;
		}
		
		return res;
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
				+ ", site_conservation_score=" + site_conservation_score + "]";
	}
	
	
	
}
