package mirna.beans;

public class InteractionData extends ModelClass {

	private String score;//ok
	private String pvalue_log;//ok
	private String miTG_score; //ok
	private String method; //ok
	private String feature; //ok
	private String phase; //ok
	private String rank;//ok
	private String provenance;//ok
	private String reference;//ok
	private String pubmedId;//ok
	private String cellularLine;//ok
	private String pvalue_og;//ok
	private String type;
	
	public InteractionData() {
		super();
	}
	


	public InteractionData(int pk,String score, String pvalue_log, String miTG_score,
			String method, String feature, String phase, String rank,
			String provenance, String reference, String pubmedId,
			String cellularLine, String pvalue_og, String type) {
		super(pk);
		this.score = score;
		this.pvalue_log = pvalue_log;
		this.miTG_score = miTG_score;
		this.method = method;
		this.feature = feature;
		this.phase = phase;
		this.rank = rank;
		this.provenance = provenance;
		this.reference = reference;
		this.pubmedId = pubmedId;
		this.cellularLine = cellularLine;
		this.pvalue_og = pvalue_og;
		this.type = type;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPvalue_og() {
		return pvalue_og;
	}



	public void setPvalue_og(String pvalue_og) {
		this.pvalue_og = pvalue_og;
	}



	public String getCellularLine() {
		return cellularLine;
	}

	public void setCellularLine(String cellularLine) {
		this.cellularLine = cellularLine;
	}

	public String getPubmedId() {
		return pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
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


