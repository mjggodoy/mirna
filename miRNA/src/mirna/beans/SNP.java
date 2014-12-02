package mirna.beans;

public class SNP extends Mutation {
	
	private String SNPid;
	private String position;
	private String article_date;
	
	
	
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
				+ ", article_date=" + article_date + ", pk=" + pk
				+ ", getSNPid()=" + getSNPid() + ", getArticle_date()="
				+ getArticle_date() + ", getPosition()=" + getPosition()
				+ ", getChromosome()=" + getChromosome() + ", getSpecie()="
				+ getSpecie() + ", getCoordinates()=" + getCoordinates()
				+ ", toString()=" + super.toString() + ", getPk()=" + getPk()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}
	
	
	


	

}
