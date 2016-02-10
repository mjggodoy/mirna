package mirna.integration.beans.nToM;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "snp_has_disease")
public class SnpHasDisease  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name = "snp_pk", nullable = false, length = 11, unique = false)
	private Integer snpPk;

	@Id
	@Column(name = "disease_pk", nullable = false, length = 11, unique = false) // Y LO TRADUCE POR ESTO
	private Integer diseasePk; // BUSCA ESTO (EN ESTA CASO SON IGUALES :P)
	
	public SnpHasDisease(){		
		super();
		
	}

	public SnpHasDisease(Integer snpPk, Integer diseasePk) {
		super();
		this.snpPk = snpPk;
		this.diseasePk = diseasePk;
	}

	public Integer getSnpPk() {
		return snpPk;
	}

	public void setSnpPk(Integer snpPk) {
		this.snpPk = snpPk;
	}

	public Integer getDiseasePk() {
		return diseasePk;
	}

	public void setDiseasePk(Integer diseasePk) {
		this.diseasePk = diseasePk;
	}

	@Override
	public String toString() {
		return "SnpHasDisease [snpPk=" + snpPk + ", diseasePk=" + diseasePk
				+ "]";
	}
	

}
