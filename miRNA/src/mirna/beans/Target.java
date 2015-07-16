package mirna.beans;

import javax.persistence.Column;

import mirna.exception.ConflictException;

public class Target extends Transcript {
	
	
	@Column(name = "cds_start", nullable = false, length = 80, unique = true)
	protected String cds_start;
	@Column(name = "cds_end", nullable = false, length = 80, unique = true)
	protected String cds_end;
	@Column(name = "utr3_start", nullable = false, length = 80, unique = true)
	protected String utr3_start;
	@Column(name = "utr3_end", nullable = false, length = 80, unique = true)
	protected String utr3_end;
	@Column(name = "strand_start", nullable = false, length = 80, unique = true)
	protected String strand_start;
	@Column(name = "strand_end", nullable = false, length = 80, unique = true)
	protected String strand_end;
	@Column(name = "chromosome", nullable = false, length = 80, unique = true)
	protected String chromosome;
	@Column(name = "polarity", nullable = false, length = 80, unique = true)
	protected String polarity;
	@Column(name = "binding_site_start", nullable = false, length = 80, unique = true)
	protected String binding_site_start;
	@Column(name = "binding_site_end", nullable = false, length = 80, unique = true)
	protected String binding_site_end;
	@Column(name = "repeated_motifs", nullable = false, length = 80, unique = true)
	protected String repeated_motifs;
	@Column(name = "utr3_conservation_score", nullable = false, length = 80, unique = true)
	protected String utr3_conservation_score;
	@Column(name = "region", nullable = false, length = 80, unique = true)
	protected String region;
	@Column(name = "seed_match", nullable = false, length = 80, unique = true)
	protected String seed_match;
	@Column(name = "coordinates", nullable = false, length = 80, unique = true)
	protected String coordinates;
	@Column(name = "gc_proportion", nullable = false, length = 80, unique = true)
	protected String gc_proportion;
	@Column(name = "transcript_pk", nullable = false, length = 80, unique = true)
	protected Integer transcript_pk;
	@Column(name = "organism_pk", nullable = false, length = 80, unique = true)
	protected Integer organism_pk;
	@Column(name = "sequence_pk", nullable = false, length = 80, unique = true)
	protected Integer sequence_pk;
	@Column(name = "target_ref", nullable = false, length = 80, unique = true)
	protected String target_ref;
	
	
	public Target() { }

	

	public String getCds_start() {
		return cds_start;
	}



	public void setCds_start(String cds_start) {
		this.cds_start = cds_start;
	}






	public String getCds_end() {
		return cds_end;
	}






	public void setCds_end(String cds_end) {
		this.cds_end = cds_end;
	}






	public String getUtr3_start() {
		return utr3_start;
	}






	public void setUtr3_start(String utr3_start) {
		this.utr3_start = utr3_start;
	}






	public String getUtr3_end() {
		return utr3_end;
	}






	public void setUtr3_end(String utr3_end) {
		this.utr3_end = utr3_end;
	}






	public String getStrand_start() {
		return strand_start;
	}






	public void setStrand_start(String strand_start) {
		this.strand_start = strand_start;
	}






	public String getStrand_end() {
		return strand_end;
	}






	public void setStrand_end(String strand_end) {
		this.strand_end = strand_end;
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








	public String getUtr3_conservation_score() {
		return utr3_conservation_score;
	}






	public void setUtr3_conservation_score(String utr3_conservation_score) {
		this.utr3_conservation_score = utr3_conservation_score;
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






	public String getGc_proportion() {
		return gc_proportion;
	}






	public void setGc_proportion(String gc_proportion) {
		this.gc_proportion = gc_proportion;
	}
	
	


	public String getCoordinates() {
		return coordinates;
	}






	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}






	public Integer getTranscript_pk() {
		return transcript_pk;
	}






	public void setTranscript_pk(Integer transcript_pk) {
		this.transcript_pk = transcript_pk;
	}






	public Integer getOrganism_pk() {
		return organism_pk;
	}


	public void setOrganism_pk(Integer organism_pk) {
		this.organism_pk = organism_pk;
	}



	public Integer getSequence_pk() {
		return sequence_pk;
	}


	public void setSequence_pk(Integer sequence_pk) {
		this.sequence_pk = sequence_pk;
	}


	public String getTarget_ref() {
		return target_ref;
	}



	public void setTarget_ref(String target_ref) {
		this.target_ref = target_ref;
	}

	public void update(Target target, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(target)==-1) throw new ConflictException(this, target);
		}
		if (target.getPk()!=null) this.pk = target.getPk();
		if (target.getCds_start()!=null) this.cds_start = target.getCds_start();
		if (target.getCds_end()!=null) this.cds_end = target.getCds_end();
		if (target.getUtr3_start()!=null) this.utr3_start = target.getUtr3_start();
		if (target.getUtr3_end()!=null) this.utr3_end = target.getUtr3_end();
		if (target.getStrand_start() !=null) this.strand_start = target.getStrand_start();
		if (target.getStrand_end()  !=null) this.strand_end = target.getStrand_end();
		if (target.getSequence_pk() !=null) this.sequence_pk = target.getSequence_pk();
		if (target.getChromosome()!=null) this.chromosome = target.getChromosome();
		if (target.getPolarity()!=null) this.polarity = target.getPolarity();
		if (target.getBinding_site_start()!=null) this.binding_site_start = target.getBinding_site_start();
		if (target.getBinding_site_end()!=null) this.binding_site_end = target.getBinding_site_end();
		if (target.getRepeated_motifs() != null) this.repeated_motifs = target.getRepeated_motifs();
		if (target.getUtr3_conservation_score() != null) this.utr3_conservation_score = target.getUtr3_conservation_score();
		if (target.getRegion() != null) this.region = target.getRegion();
		if (target.getSeed_match() != null) this.seed_match = target.getSeed_match();
		if (target.getGc_proportion()   != null) this.gc_proportion = target.getGc_proportion();
		if (target.getOrganism_pk()  != null) this.organism_pk = target.getOrganism_pk();		
		if(target.getTranscript_pk() !=null) this.transcript_pk = target.getTranscript_pk();
		if(target.getSequence_pk() !=null) this.sequence_pk = target.getSequence_pk();
		if(target.getCoordinates() !=null) this.coordinates = target.getCoordinates();
		if(target.getTarget_ref() !=null) this.target_ref = target.getTarget_ref();


	}
	
	
	public int checkConflict(Target target) {
		int res = 0;
		
		if (this.pk!=null) {
			if (target.getPk()==null) res++; 
			else if (!this.pk.equals(target.getPk())) return -1;
		}
		if (this.cds_start!=null) {
			if (target.getCds_start()==null) res++; 
			else if (!this.cds_start.equals(target.getCds_start())) return -1;
		}
		if (this.cds_end!=null) {
			if (target.getCds_end()==null) res++; 
			else if (!this.cds_end.equals(target.getCds_end())) return -1;
		}
		
		if (this.utr3_start!=null) {
			if (target.getUtr3_start() ==null) res++; 
			else if (!this.utr3_start.equals(target.getUtr3_start())) return -1;
		}
		
		if (this.utr3_end!=null) {
			if (target.getUtr3_end()==null) res++; 
			else if (!this.utr3_end.equals(target.getUtr3_end())) return -1;
		}
		
		if (this.strand_start!=null) {
			if (target.getStrand_start() ==null) res++; 
			else if (!this.strand_start.equals(target.getStrand_start())) return -1;
		}
		
		if (this.strand_end!=null) {
			if (target.getStrand_end()==null) res++; 
			else if (!this.strand_end.equals(target.getStrand_end())) return -1;
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
		
		if (this.utr3_conservation_score!=null) {
			if (target.getUtr3_conservation_score()==null) res++; 
			else if (!this.utr3_conservation_score.equals(target.getUtr3_conservation_score())) return -1;
		}
		
		if (this.region!=null) {
			if (target.getRegion()==null) res++; 
			else if (!this.region.equals(target.getRegion())) return -1;
		}
		
		if (this.seed_match!=null) {
			if (target.getSeed_match()==null) res++; 
			else if (!this.seed_match.equals(target.getSeed_match())) return -1;
		}
		
		if (this.gc_proportion!=null) {
			if (target.getGc_proportion()==null) res++; 
			else if (!this.gc_proportion.equals(target.getGc_proportion())) return -1;
		}
		
		if (this.organism_pk!=null) {
			if (target.getOrganism_pk()  ==null) res++; 
			else if (!this.organism_pk.equals(target.getOrganism_pk())) return -1;
		}
		
		if (this.transcript_pk!=null) {
			if (target.getTranscript_pk()  ==null) res++; 
			else if (!this.transcript_pk.equals(target.getTranscript_pk())) return -1;
		}
		
		if (this.sequence_pk!=null) {
			if (target.getSequence_pk()  ==null) res++; 
			else if (!this.sequence_pk.equals(target.getSequence_pk())) return -1;
		}
		
		if (this.coordinates!=null) {
			if (target.getCoordinates()  ==null) res++; 
			else if (!this.coordinates.equals(target.getCoordinates())) return -1;
		}
		
		if (this.target_ref!=null) {
			if (target.getTarget_ref()  ==null) res++; 
			else if (!this.target_ref.equals(target.getCoordinates())) return -1;
		}
		
		return res;
	}



	@Override
	public String toString() {
		return "Target [cds_start=" + cds_start + ", cds_end=" + cds_end
				+ ", utr3_start=" + utr3_start + ", utr3_end=" + utr3_end
				+ ", strand_start=" + strand_start + ", strand_end="
				+ strand_end + ", chromosome=" + chromosome + ", polarity="
				+ polarity + ", binding_site_start=" + binding_site_start
				+ ", binding_site_end=" + binding_site_end
				+ ", repeated_motifs=" + repeated_motifs
				+ ", utr3_conservation_score=" + utr3_conservation_score
				+ ", region=" + region + ", seed_match=" + seed_match
				+ ", coordinates=" + coordinates + ", gc_proportion="
				+ gc_proportion + ", transcript_pk=" + transcript_pk
				+ ", organism_pk=" + organism_pk + ", sequence_pk="
				+ sequence_pk + ", target_ref=" + target_ref + ", pk=" + pk
				+ "]";
	}

	
}
