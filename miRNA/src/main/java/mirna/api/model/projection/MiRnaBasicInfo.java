package mirna.api.model.projection;

import org.springframework.data.rest.core.config.Projection;

import mirna.api.model.MiRna;

@Projection(name = "mirna_basic_info", types = { MiRna.class })
public interface MiRnaBasicInfo {
	
	public int getPk();
	
	public String getId();

	public String getAccessionNumber();

	public String getPreviousId();
	
	public String getType();

	public Integer getMirBasePk();

}
