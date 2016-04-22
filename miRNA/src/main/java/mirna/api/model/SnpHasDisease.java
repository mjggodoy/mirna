package mirna.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "snp_has_disease", schema = "mirna")
public class SnpHasDisease implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "snp_pk", nullable = false)
	private int snpPk;
	
	@Id
	@Column(name = "disease_pk", nullable = false)
	private int diseasePk;

	private SnpHasDisease() {
		super();
	}

	public int getSnpPk() {
		return snpPk;
	}

	public int getDiseasePk() {
		return diseasePk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + diseasePk;
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
		SnpHasDisease other = (SnpHasDisease) obj;
		if (diseasePk != other.diseasePk)
			return false;
		if (snpPk != other.snpPk)
			return false;
		return true;
	}

	
	
}
