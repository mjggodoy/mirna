package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "snp")

public class SNP extends Mutation {
	
	@Column(name = "snp_id", nullable = false, length = 80, unique = true)
	private String snp_id;
	
	@Column(name = "position", nullable = true)
	private Integer position;
	
	@Column(name = "article_date", nullable = true, length = 45)
	private String article_date;
	
	@Column(name = "mutation_pk", nullable = true, length = 45) //mutation_pk puede ser nulo
	private String mutation_pk;
	
	
	public SNP(){
		super();
	}

	
	
	
	public String getSnp_id() {
		return snp_id;
	}




	public void setSnp_id(String snp_id) {
		this.snp_id = snp_id;
	}




	public Integer getPosition() {
		return position;
	}




	public void setPosition(Integer position) {
		this.position = position;
	}




	public String getArticle_date() {
		return article_date;
	}




	public void setArticle_date(String article_date) {
		this.article_date = article_date;
	}




	public String getMutation_pk() {
		return mutation_pk;
	}




	public void setMutation_pk(String mutation_pk) {
		this.mutation_pk = mutation_pk;
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
		
		if(snp.getSnp_id()!=null)
			this.snp_id = snp.getSnp_id();
			
		if(snp.getArticle_date() !=null){
			this.article_date = snp.getArticle_date();
		}
		
		if(snp.mutation_pk !=null){
			this.mutation_pk = snp.getMutation_pk();
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
		
		if (this.snp_id != null) {
			if (snp.getSnp_id() == null)
				res++;
			else if (!this.snp_id.equals(snp.getSnp_id()))
				return -1;
		}
		
		if (this.article_date != null) {
			if (snp.getArticle_date() == null)
				res++;
			else if (!this.article_date.equals(snp.getArticle_date()))
				return -1;
		}
		
		if (this.mutation_pk != null) {
			if (snp.getMutation_pk() == null)
				res++;
			else if (!this.mutation_pk.equals(snp.getMutation_pk()));
				return -1;
		}
		
		
		
		
		return res;
	}




	@Override
	public String toString() {
		return "SNP [snp_id=" + snp_id + ", position=" + position
				+ ", article_date=" + article_date + ", mutation_pk="
				+ mutation_pk + ", specie=" + specie + ", chromosome="
				+ chromosome + ", coordinates=" + coordinates
				+ ", orientation=" + orientation + ", distance=" + distance
				+ ", journal=" + journal + ", year=" + year + ", description="
				+ description + ", pubmed_id=" + pubmed_id + ", resource="
				+ resource + ", gene_id=" + gene_id + ", pk=" + pk + "]";
	}




	




	


	
	

	
}
