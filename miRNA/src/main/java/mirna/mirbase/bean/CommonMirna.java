package mirna.mirbase.bean;

public abstract class CommonMirna {
	
	//protected Integer pk;
	protected String acc;
	protected String id;
	protected String previousId;
	protected int mirbasePk;
	
	public CommonMirna(String acc, String id, String previousId, int mirbasePk) {
		super();
		this.acc = acc;
		this.id = id;
		this.previousId = previousId;
		this.mirbasePk = mirbasePk;
	}

	public String getAcc() {
		return acc;
	}

	public String getId() {
		return id;
	}

	public String getPreviousId() {
		return previousId;
	}
	
	public int getMirbasePk() {
		return mirbasePk;
	}

	@Override
	public String toString() {
		return "CommonMirna [acc=" + acc + ", id=" + id + ", previousId="
				+ previousId + ", mirbasePk=" + mirbasePk + "]";
	}
	
}
