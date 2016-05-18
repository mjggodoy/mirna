package mirna.mirbase.bean;

public class Mirna extends CommonMirna {
	
	private String description;
	private String sequence;
	private String comment;
	
	public Mirna(String acc, String id, String previousId, int mirnaPk,
			String description, String sequence, String comment) {
		super(acc, id, previousId, mirnaPk);
		this.description = description;
		this.sequence = sequence;
		this.comment = comment;
	}

	public String getDescription() {
		return description;
	}

	public String getSequence() {
		return sequence;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public String toString() {
		return "Mirna [acc=" + acc + ", id=" + id + ", previousId="
				+ previousId + ", mirnaPk=" + mirbasePk + ", description="
				+ description + ", sequence=" + sequence + ", comment="
				+ comment + "]";
	}
	
}
