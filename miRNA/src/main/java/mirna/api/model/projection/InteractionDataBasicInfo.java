package mirna.api.model.projection;

import org.springframework.data.rest.core.config.Projection;

import mirna.api.model.InteractionData;

@Projection(name = "basic_info", types = { InteractionData.class })
public interface InteractionDataBasicInfo {
	
	public int getPk();
	
	public String getScore();

	public String getPvalueLog();

	public String getMiTGScore();

	public String getMethod();

	public String getFeature();

	public String getPhase();

	public String getRank();

	public String getProvenance();

	public String getReference();

	public String getCellularLine();

	public String getpValueOg();

	public String getType();

	public Integer getMirnaPk();

	public OnlyName getGene();
	
	public TargetBasicInfo getTarget();

}
