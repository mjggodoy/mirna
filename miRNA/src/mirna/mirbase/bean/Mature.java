package mirna.mirbase.bean;

public class Mature extends CommonMirna {
	
	private String evidence;
	private String experiment;
	private String similarity;
	
	public Mature(String acc, String id, String previousId, int mirnaPk,
			String evidence, String experiment, String similarity) {
		super(acc, id, previousId, mirnaPk);
		this.evidence = evidence;
		this.experiment = experiment;
		this.similarity = similarity;
	}

	public String getEvidence() {
		return evidence;
	}

	public String getExperiment() {
		return experiment;
	}
	
	public String getSimilarity() {
		return similarity;
	}

	@Override
	public String toString() {
		return "Mature [acc=" + acc + ", id=" + id + ", previousId="
				+ previousId + ", mirnaPk=" + mirbasePk + ", evidence="
				+ evidence + ", experiment=" + experiment
				+ ", similarity=" + similarity + "]";
	}
	
}
