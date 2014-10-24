package mirna.beans;

public class Transcript extends ModelClass {

	//private String cdsStart;
	//private String cdsEnd;
	//private String UTR5start;
	//private String UTR3end;
	//private String biotype;
	//private String sequence;
	private String transcriptID;//ok
	private String name;//ok
	private String isoform; //ok
	//private String evidence;//ok
	private String id;//ok
	

	public Transcript() {
		super();
	}
	
	
	
	public Transcript(int pk,String cdsStart, String cdsEnd, String uTR5start,
			String uTR3end, String biotype, String sequence,
			String transcriptID, String name, String isoform, String id,
			String externalName) {
		super(pk);
		//this.cdsStart = cdsStart;
		//this.cdsEnd = cdsEnd;
		//UTR5start = uTR5start;
		//UTR3end = uTR3end;
		//this.biotype = biotype;
		//this.sequence = sequence;
		this.transcriptID = transcriptID;
		this.name = name;
		this.isoform = isoform;
		this.id = id;
		ExternalName = externalName;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	//public String getEvidence() {
		//return evidence;
	//}



	//public void setEvidence(String evidence) {
		//this.evidence = evidence;
	//}



	public String getIsoform() {
		return isoform;
	}

	public void setIsoform(String isoform) {
		this.isoform = isoform;
	}

	private String ExternalName;
	

	
	public String getExternalName() {
		return ExternalName;
	}

	public void setExternalName(String externalName) {
		ExternalName = externalName;
	}

	//public String getCdsStart() {
		//return cdsStart;
	//}

	//public void setCdsStart(String cdsStart) {
		//this.cdsStart = cdsStart;
	//}

	//public String getCdsEnd() {
		//return cdsEnd;
	//}

	//public void setCdsEnd(String cdsEnd) {
	//	this.cdsEnd = cdsEnd;
	//}

	//public String getUTR5start() {
		//return UTR5start;
	//}

	//public void setUTR5start(String uTR5start) {
		//UTR5start = uTR5start;
	//}

	//public String getUTR3end() {
		//return UTR3end;
	//}

	//public void setUTR3end(String uTR3end) {
		//UTR3end = uTR3end;
	//}

	//public String getBiotype() {
		//return biotype;
	//}

	//public void setBiotype(String biotype) {
		//this.biotype = biotype;
	//}

	//public String getSequence() {
		//return sequence;
	//}

	//public void setSequence(String sequence) {
		//this.sequence = sequence;
	//}

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
