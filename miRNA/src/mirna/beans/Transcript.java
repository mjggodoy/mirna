package mirna.beans;

public class Transcript extends Target {

	private String cdsStart;
	private String cdsEnd;
	private String UTR5start;
	private String UTR3end;
	private String biotype;
	private String sequence;
	private String transcriptID;
	private String name;
	private String ExternalName;
	

	public String getExternalName() {
		return ExternalName;
	}

	public void setExternalName(String externalName) {
		ExternalName = externalName;
	}

	public Transcript() {
	}

	public String getCdsStart() {
		return cdsStart;
	}

	public void setCdsStart(String cdsStart) {
		this.cdsStart = cdsStart;
	}

	public String getCdsEnd() {
		return cdsEnd;
	}

	public void setCdsEnd(String cdsEnd) {
		this.cdsEnd = cdsEnd;
	}

	public String getUTR5start() {
		return UTR5start;
	}

	public void setUTR5start(String uTR5start) {
		UTR5start = uTR5start;
	}

	public String getUTR3end() {
		return UTR3end;
	}

	public void setUTR3end(String uTR3end) {
		UTR3end = uTR3end;
	}

	public String getBiotype() {
		return biotype;
	}

	public void setBiotype(String biotype) {
		this.biotype = biotype;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getTranscriptID() {
		return transcriptID;
	}

	public void setTranscriptID(String transcriptID) {
		this.transcriptID = transcriptID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}