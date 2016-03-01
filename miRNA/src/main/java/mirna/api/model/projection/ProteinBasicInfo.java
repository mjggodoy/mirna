package mirna.api.model.projection;

import org.springframework.data.rest.core.config.Projection;

import mirna.api.model.Protein;

@Projection(name = "basic_info", types = { Protein.class })
public interface ProteinBasicInfo {
	
	public Integer getPk();
	
	public String getId();

}
