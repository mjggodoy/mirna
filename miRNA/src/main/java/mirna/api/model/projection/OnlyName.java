package mirna.api.model.projection;

import org.springframework.data.rest.core.config.Projection;

import mirna.api.model.Gene;

@Projection(name = "onlyName", types = { Gene.class })
public interface OnlyName {
	
	public Integer getPk();
	
	public String getName();

}
