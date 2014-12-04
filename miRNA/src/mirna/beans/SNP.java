package mirna.beans;

public class SNP extends Mutation {
	
	protected String SNPid;
	protected String position;
	protected String article_date;
	
	
	
	public SNP(){}

	
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


	
	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	@Override
	public String toString() {
		return "SNP [SNPid=" + SNPid + ", position=" + position
				+ ", article_date=" + article_date + ", specie=" + specie
				+ ", chromosome=" + chromosome + ", coordinates=" + coordinates
				+ ", orientation=" + orientation + ", distance=" + distance
				+ ", journal=" + journal + ", year=" + year + ", description="
				+ description + ", pubmed_id=" + pubmed_id + ", resource="
				+ resource + ", pk=" + pk + "]";
	}


	


	

	


	
	
	
	
	
	


	

}
