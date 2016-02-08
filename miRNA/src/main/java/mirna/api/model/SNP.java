package mirna.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "snp", schema = "mirna")

public class SNP extends ModelClass {

	@Column(name = "snp_id", nullable = false, length = 80, unique = true)
	private String snp_id;

	@Column(name = "position", nullable = true)
	private String position;

	@Column(name = "article_date", nullable = true, length = 45)
	private String article_date;

	@Column(name = "mutation_pk", nullable = true, length = 45) //mutation_pk puede ser nulo
	private Integer mutation_pk;
	
	@Column(name = "chromosome", nullable = true, length = 45) //mutation_pk puede ser nulo
	private String chromosome;
	
	@Column(name = "orientation", nullable = true, length = 45) //mutation_pk puede ser nulo
	private String orientation;


	public SNP(){	}


	public String getSnp_id() {
		return snp_id;
	}

	public String getPosition() {
		return position;
	}



	public String getArticle_date() {
		return article_date;
	}

	

	public Integer getMutation_pk() {
		return mutation_pk;
	}

	public String getChromosome() {
		return chromosome;
	}

	

	public String getOrientation() {
		return orientation;
	}
}
