package mirna.api.model.projection;

import org.springframework.data.rest.core.config.Projection;

import mirna.api.model.Target;

@Projection(name = "basic_info", types = { Target.class })
public interface TargetBasicInfo {
	
	public Integer getPk();
	
	public String getChromosome();
	
	public String getPolarity();
	
	public String getCoordinates();
	
	public String getRegion();
	
	public String getGc_proportion();
	
	public String getGu_proportion();
	
	public String getCds_start();
	
	public String getCds_end();
	
	public String getUtr3_start();
	
	public String getUtr3_end();
	
	public String getStrand_start();
	
	public String getStrand_end();
	
	public String getBinding_site_start();
	
	public String getBinding_site_end();
	
	public String getRepeated_motifs();
	
	public String getUtr3_conservation_score();
	
	public String getSeed_match();
	
	public String getTarget_ref();
	
	public String getSite_conservation_score();
	
	// Organism:
	

	public TranscriptBasicInfo getTranscript();
	
	

}
