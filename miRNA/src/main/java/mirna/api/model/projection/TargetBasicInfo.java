package mirna.api.model.projection;

import org.springframework.data.rest.core.config.Projection;

import mirna.api.model.Target;

@Projection(name = "basic_info", types = { Target.class })
public interface TargetBasicInfo {
	
	public Integer getPk();
	
	public TranscriptBasicInfo getTranscript();

}
