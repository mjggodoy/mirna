package mirna.beans;

import mirna.exception.ConflictException;

public class Transcript extends ModelClass {

	private String transcriptID;// ok
	private String name;// ok
	private String isoform; // ok
	private String externalName;
	private Integer targetPk;
	private Integer geneId;

	public Transcript() {
	}

	public Transcript(int pk, String transcriptID, String name, String isoform
			, String externalName, int targetPk, int geneId) {
		super(pk);
		this.transcriptID = transcriptID;
		this.name = name;
		this.isoform = isoform;
		this.externalName = externalName;
		this.targetPk = targetPk;
		this.geneId = geneId;
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

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
	}
	
	public Integer getTargetPk() {
		return targetPk;
	}

	public void setTargetPk(int targetPk) {
		this.targetPk = targetPk;
	}
	
	public Integer getGeneId() {
		return geneId;
	}

	public void setGeneId(Integer geneId) {
		this.geneId = geneId;
	}
	

	public int checkConflict(Transcript transcript) {

		int res = 0;

		if (this.pk != null) {
			if (transcript.getPk() == null)
				res++; // res = 1
			else if (!this.pk.equals(transcript.getPk()))
				return -1;
		}

		if (this.transcriptID != null) {
			if (transcript.transcriptID == null)
				res++;
			else if (!this.transcriptID.equals(transcript.transcriptID))
				return -1;
		}

		if (this.isoform != null) {
			if (transcript.isoform == null)
				res++;
			else if (!this.isoform.equals(transcript.isoform))
				return -1;
		}

		if (this.name != null) {
			if (transcript.name == null)
				res++;
			else if (!this.name.equals(transcript.name))
				return -1;
		}

		if (this.externalName != null) {
			if (transcript.externalName == null)
				res++;
			else if (!this.externalName.equals(transcript.externalName))
				return -1;
		}
		
		if(this.targetPk !=null){
			if (transcript.targetPk == null)res++;	
			
			else if (!this.targetPk.equals(transcript.targetPk)){
				
			return -1;	
			}
		}
		
		

		return res;
	}

	public void update(Transcript transcript) throws ConflictException {
		this.update(transcript, true);
	}

	public void update(Transcript transcript, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(transcript) == -1)
				throw new ConflictException(this, transcript);
		}
		
		if (transcript.getPk() != null)
			this.pk = transcript.getPk();
		if (transcript.getIsoform() != null)
			this.isoform = transcript.getIsoform();
		if (transcript.getName() != null)
			this.name = transcript.getName();
		if (transcript.getExternalName() != null)
			this.externalName = transcript.getExternalName();
		if (transcript.getTargetPk() != null)
			this.targetPk = transcript.getTargetPk();
		if (transcript.getGeneId()!= null)
			this.geneId = transcript.getGeneId();
		
	}

	@Override
	public String toString() {
		return "Transcript [transcriptID=" + transcriptID + ", name=" + name
				+ ", isoform=" + isoform + ", externalName=" + externalName
				+ ", targetPk=" + targetPk + ", geneId=" + geneId
				+ " pk=" + pk + "]";
	}


	

}
