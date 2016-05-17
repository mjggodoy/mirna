package mirna.api.model.projection;

import org.springframework.data.rest.core.config.Projection;

import mirna.api.model.Transcript;

@Projection(name = "basic_info", types = { Transcript.class })
public interface TranscriptBasicInfo {
	
	public Integer getPk();
	
	public String getId();

	public String getName();

	public String getIsoform();

}
