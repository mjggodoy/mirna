package mirna.api.model.projection;

import org.springframework.data.rest.core.config.Projection;

import mirna.api.model.MiRna;

@Projection(name = "mirna_basic_info", types = { MiRna.class })
public interface MiRnaBasicInfo {
	
	public String getId();

	public String getAccessionNumber();

	public String getPreviousId();

	public boolean isMature();

	public boolean isDead();
	
	public Integer getMirBasePk();

}
