package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transcript")
public class Transcript extends ModelClass {

	@Column(name = "id", nullable = true, length = 20, unique = true)
	private String transcriptID;// ok
	
	@Column(name = "name", nullable = true, length = 20, unique = false)
	private String name;// ok
	
	@Column(name = "isoform", nullable = true, length = 45, unique = false)
	private String isoform; // ok
	
	@Column(name = "external_name", nullable = true, length = 20, unique = true)
	private String externalName;

	public Transcript() {
	}

	public Transcript(int pk, String transcriptID, String name, String isoform
			, String externalName) {
		super(pk);
		this.transcriptID = transcriptID;
		this.name = name;
		this.isoform = isoform;
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

	public String getExternalName() {
		return externalName;
	}

	public void setExternalName(String externalName) {
		this.externalName = externalName;
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
		
	}

	@Override
	public String toString() {
		return "Transcript [transcriptID=" + transcriptID + ", name=" + name
				+ ", isoform=" + isoform + ", externalName=" + externalName
				+ " pk=" + pk + "]";
	}

}
