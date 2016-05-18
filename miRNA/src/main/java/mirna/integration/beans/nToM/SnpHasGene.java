package mirna.integration.beans.nToM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "snp_has_gene")
public class SnpHasGene  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name = "snp_pk", nullable = false, length = 11, unique = false)
	private Integer snpPk;

	@Id
	@Column(name = "gene_pk", nullable = false, length = 11, unique = false)
	private Integer genePk;
	
	public SnpHasGene(){		
		super();
		
	}

	public SnpHasGene(Integer snpPk, Integer genePk) {
		super();
		this.snpPk = snpPk;
		this.genePk = genePk;
	}

	public Integer getSnpPk() {
		return snpPk;
	}

	public void setSnpPk(Integer snpPk) {
		this.snpPk = snpPk;
	}

	public Integer getGenePk() {
		return genePk;
	}

	public void setGenePk(Integer genePk) {
		this.genePk = genePk;
	}

	@Override
	public String toString() {
		return "SnpHasGene [snpPk=" + snpPk + ", genePk=" + genePk
				+ "]";
	}
	

}
