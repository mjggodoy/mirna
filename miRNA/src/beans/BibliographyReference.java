package beans;

public class BibliographyReference {
	
	private String pubmedId;

	public BibliographyReference(String pubmedId) {
		super();
		this.pubmedId = pubmedId;
	}

	public String getPubmedId() {
		return pubmedId;
	}

	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}
	
}
