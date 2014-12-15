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
	private String externalName;

	public Transcript() { }
	
	public Transcript(int pk, String transcriptID, String name, String isoform,
			String id, String externalName) {
		super(pk);
		this.transcriptID = transcriptID;
		this.name = name;
		this.isoform = isoform;
		this.id = id;
		this.externalName = externalName;
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

	public String getIsoform() {
		return isoform;
	}

	public void setIsoform(String isoform) {
		this.isoform = isoform;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}

	@Override
	public String toString() {
		return "Transcript [transcriptID=" + transcriptID + ", name=" + name
				+ ", isoform=" + isoform + ", id=" + id + ", externalName="
				+ externalName + ", pk=" + pk + "]";
	}

	
	
	
}
