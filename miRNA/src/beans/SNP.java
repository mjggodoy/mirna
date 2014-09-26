package beans;

public class SNP extends Mutation {
	
	private String SNPid;
	private String position;
	private String article_date;
	public String getSNPid() {
		return SNPid;
	}


	public void setSNPid(String sNPid) {
		SNPid = sNPid;
	}


	public String getArticle_date() {
		return article_date;
	}


	public void setArticle_date(String article_date) {
		this.article_date = article_date;
	}


	public String getPubmedId() {
		return pubmedId;
	}


	public void setPubmedId(String pubmedId) {
		this.pubmedId = pubmedId;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getMonth() {
		return month;
	}


	public void setMonth(String month) {
		this.month = month;
	}


	public String getDisease() {
		return disease;
	}


	public void setDisease(String disease) {
		this.disease = disease;
	}


	public String getJournal() {
		return journal;
	}


	public void setJournal(String journal) {
		this.journal = journal;
	}


	public String getLink() {
		return link;
	}


	public void setLink(String link) {
		this.link = link;
	}


	private String pubmedId;
	private String year;
	private String month;
	private String disease;
	private String journal;
	private String link;
	
	public SNP(){}

	
	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	
	
	
	

}
