package mirna.beans;

public class InteractionData {

	private String score;
	private String pvalue_log;
	private String miTG_score;
	private String method;
	private String feature;
	private String phase;
	private String rank;
	private String provenance;
	
	public InteractionData() {
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getPvalue_log() {
		return pvalue_log;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public String getProvenance() {
		return provenance;
	}

	public void setProvenance(String provenance) {
		this.provenance = provenance;
	}

	public void setPvalue_log(String pvalue_log) {
		this.pvalue_log = pvalue_log;
	}

	public String getMiTG_score() {
		return miTG_score;
	}

	public void setMiTG_score(String miTG_score) {
		this.miTG_score = miTG_score;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	

}


