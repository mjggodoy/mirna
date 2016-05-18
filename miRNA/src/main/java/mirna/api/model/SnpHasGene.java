package mirna.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "snp_has_gene", schema = "mirna")
public class SnpHasGene implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "snp_pk", nullable = false)
	private int snpPk;
	
	@Id
	@Column(name = "gene_pk", nullable = false)
	private int genePk;

	private SnpHasGene() {
		super();
	}

	public int getSnpPk() {
		return snpPk;
	}

	public int getGenePk() {
		return genePk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + genePk;
		result = prime * result + snpPk;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SnpHasGene other = (SnpHasGene) obj;
		if (genePk != other.genePk)
			return false;
		if (snpPk != other.snpPk)
			return false;
		return true;
	}

	
	

}
