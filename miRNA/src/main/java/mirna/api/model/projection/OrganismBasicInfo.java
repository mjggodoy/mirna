package mirna.api.model.projection;

import org.springframework.data.rest.core.config.Projection;

import mirna.api.model.Organism;
import mirna.api.model.Transcript;

@Projection(name = "basic_info", types = { Organism.class })
public interface OrganismBasicInfo {
	
	public Integer getPk();
	
	public String getSpecie();
	
	public String getName();
	
	public String getResource();
	
	public String getShortName();

}
