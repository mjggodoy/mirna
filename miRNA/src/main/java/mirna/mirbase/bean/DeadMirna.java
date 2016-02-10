package mirna.mirbase.bean;

public class DeadMirna extends CommonMirna {
	
	private String forwardTo;
	private String description;
	
	public DeadMirna(String acc, String id, String previousId, int mirnaPk,
			String forwardTo, String description) {
		super(acc, id, previousId, mirnaPk);
		this.forwardTo = forwardTo;
		this.description = description;
	}

	public String getForwardTo() {
		return forwardTo;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "DeadMirna [acc=" + acc + ", id=" + id + ", previousId="
				+ previousId + ", mirnaPk=" + mirbasePk + ", forwardTo="
				+ forwardTo + ", description=" + description + "]";
	}
	
}
