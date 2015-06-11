package mirna.beans;

import mirna.exception.ConflictException;

public class SNP extends Mutation {
	
	protected String SNPid;
	protected String position;
	protected String article_date;
	protected Integer disease_id;
	
	
	
	public SNP(){
		super();
	}

	
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

	public Integer getDisease_id() {
		return disease_id;
	}

	public void setDisease_id(Integer disease_id) {
		this.disease_id = disease_id;
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
		if (snp.getPk() != null){
			this.pk = snp.getPk();
		}
		
		if(snp.getSNPid() != null){
			this.SNPid = snp.getSNPid();
			
		}
		if(snp.getPosition() !=null)
			this.position = snp.getPosition();
			
		if(snp.getArticle_date() !=null){
			this.article_date = snp.getArticle_date();
		}
		
		if(snp.getDisease_id() !=null){
			this.disease_id = snp.getDisease_id();
		}
		
		if(snp.getDisease_id() !=null){
			
			this.disease_id = snp.getDisease_id();
		}

		
		
	}
	
	public int checkConflict(SNP snp) {
		int res = 0;
		if (this.pk != null) {
			if (snp.getPk() == null)
				res++;
			else if (!this.pk.equals(snp.getPk()))
				return -1;
		}
		
		if (this.SNPid != null) {
			if (snp.getSNPid() == null)
				res++;
			else if (!this.SNPid.equals(snp.getSNPid()))
				return -1;
		}
		
		if (this.article_date != null) {
			if (snp.getArticle_date() == null)
				res++;
			else if (!this.article_date.equals(snp.getArticle_date()))
				return -1;
		}
		
		if (this.getDisease_id() != null) {
			if (snp.getDisease_id() == null)
				res++;
			else if (!this.disease_id.equals(snp.getDisease_id()));
				return -1;
		}
		
		
		return res;
	}


	@Override
	public String toString() {
		return "SNP [SNPid=" + SNPid + ", position=" + position
				+ ", article_date=" + article_date + ", disease_id="
				+ disease_id + ", specie=" + specie + ", chromosome="
				+ chromosome + ", coordinates=" + coordinates
				+ ", orientation=" + orientation + ", distance=" + distance
				+ ", journal=" + journal + ", year=" + year + ", description="
				+ description + ", pubmed_id=" + pubmed_id + ", resource="
				+ resource + ", pk=" + pk + "]";
	}
	

	
}
