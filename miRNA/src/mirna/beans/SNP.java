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

public class SNP extends ModelClass {

	@Column(name = "snp_id", nullable = false, length = 80, unique = true)
	private String snp_id;

	@Column(name = "position", nullable = true)
	private String position;

	@Column(name = "article_date", nullable = true, length = 45)
	private String article_date;

	@Column(name = "mutation_pk", nullable = true, length = 45) //mutation_pk puede ser nulo
	private Integer mutation_pk;

	@Column(name = "gene_pk", nullable = true, length = 45) //mutation_pk puede ser nulo
	private Integer gene_pk;
	
	@Column(name = "chromosome", nullable = true, length = 45) //mutation_pk puede ser nulo
	private String chromosome;
	
	@Column(name = "orientation", nullable = true, length = 45) //mutation_pk puede ser nulo
	private String orientation;


	public SNP(){	}


	public String getSnp_id() {
		return snp_id;
	}

	public void setSnp_id(String snp_id) {
		this.snp_id = snp_id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getArticle_date() {
		return article_date;
	}

	public void setArticle_date(String article_date) {
		this.article_date = article_date;
	}

	public Integer getMutation_pk() {
		return mutation_pk;
	}

	public void setMutation_pk(Integer mutation_pk) {
		this.mutation_pk = mutation_pk;
	}

	public Integer getGene_pk() {
		return gene_pk;
	}

	public void setGene_pk(Integer gene_pk) {
		this.gene_pk = gene_pk;
	}
	

	public String getChromosome() {
		return chromosome;
	}


	public void setChromosome(String chromosome) {
		this.chromosome = chromosome;
	}


	public String getOrientation() {
		return orientation;
	}


	public void setOrientation(String orientation) {
		this.orientation = orientation;
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

		if(snp.getSnp_id()!=null)
			this.snp_id = snp.getSnp_id();

		if(snp.getArticle_date() !=null){
			this.article_date = snp.getArticle_date();
		}

		if(snp.mutation_pk !=null){
			this.mutation_pk = snp.getMutation_pk();
		}

		if(snp.gene_pk !=null){
			this.gene_pk = snp.getGene_pk() ;
		}
		
		if(snp.chromosome !=null){
			this.chromosome = snp.getChromosome();
		}
		
		if(snp.orientation !=null){
			this.orientation = snp.getOrientation();
		}
	}

	public int checkConflict(SNP snp) {
		//System.out.println(snp);
		//System.out.println(this);

		int res = 0;
		if (this.pk != null) {
			if (snp.getPk() == null)
				res++;
			else if (!this.pk.equals(snp.getPk())) {
				System.out.println("PK PUTO");
				return -1;
			}

		}

		if (this.snp_id != null) {
			if (snp.getSnp_id() == null)
				res++;
			else if (!this.snp_id.equals(snp.getSnp_id())) {
				System.out.println("SNP ID PUTO");
				return -1;
			}
		}

		if (this.article_date != null) {
			if (snp.getArticle_date() == null)
				res++;
			else if (!this.article_date.equals(snp.getArticle_date())) {
				//System.out.println("ARTICLE PUTO");
				return -1;
			}
		}

		if (this.mutation_pk != null) {
			if (snp.getMutation_pk() == null)
				res++;
			else if (!this.mutation_pk.equals(snp.getMutation_pk())){
				return -1;
			}
		}

		if (this.gene_pk != null) {
			if (snp.getGene_pk() == null)
				res++;
			else if (!this.gene_pk.equals(snp.getGene_pk())){
				return -1;
			}
		}

		if (this.chromosome != null) {
			if (snp.getChromosome() == null)
				res++;
			else if (!this.chromosome.equals(snp.getChromosome())){
				return -1;
			}
		}
		
		if (this.orientation != null) {
			if (snp.getOrientation()== null)
				res++;
			else if (!this.orientation.equals(snp.getOrientation())){
				return -1;
			}
		}
		
		return res;
	}


	@Override
	public String toString() {
		return "SNP [snp_id=" + snp_id + ", position=" + position
				+ ", article_date=" + article_date + ", mutation_pk="
				+ mutation_pk + ", gene_pk=" + gene_pk + ", chromosome="
				+ chromosome + ", orientation=" + orientation + ", pk=" + pk
				+ "]";
	}

	

}
