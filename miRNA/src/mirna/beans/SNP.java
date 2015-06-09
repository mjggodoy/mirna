package mirna.beans;

import mirna.exception.ConflictException;

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

	public void update(SNP snp) throws ConflictException {
		this.update(snp, true);
	}
	
	public void update(SNP snp, boolean checkConflict)
			throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(snp) == -1)
				throw new ConflictException(this, snp);
		}
		if (snp.getPk() != null)
			this.pk = snp.getPk();
	}
	
	public int checkConflict(SNP snp) {
		int res = 0;
		if (this.pk != null) {
			if (snp.getPk() == null)
				res++;
			else if (!this.pk.equals(snp.getPk()))
				return -1;
		}
		
		return res;
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
