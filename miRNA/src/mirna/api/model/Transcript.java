package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.integration.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transcript", schema = "mirna")
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


	public String getName() {
		return name;
	}

	
	public String getIsoform() {
		return isoform;
	}

	
	public String getExternalName() {
		return externalName;
	}

	
}
