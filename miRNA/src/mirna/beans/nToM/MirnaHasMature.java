package mirna.beans.nToM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mirna_has_mature")

public class MirnaHasMature implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "mirna_pk", nullable = false, length = 11, unique = false)
	private Integer mirna_pk;

	@Id
	@Column(name = "mature_pk", nullable = false, length = 11, unique = false) 
	private Integer mature_pk;
	
	

	public MirnaHasMature() {
		super();
	}

	public MirnaHasMature(Integer mirna_pk, Integer mature_pk) {
		super();
		this.mirna_pk = mirna_pk;
		this.mature_pk = mature_pk;
	}

	public Integer getMirna_pk() {
		return mirna_pk;
	}

	public void setMirna_pk(Integer mirna_pk) {
		this.mirna_pk = mirna_pk;
	}

	public Integer getMature_pk() {
		return mature_pk;
	}

	public void setMature_pk(Integer mature_pk) {
		this.mature_pk = mature_pk;
	}

	@Override
	public String toString() {
		return "MirnaHasMature [mirna_pk=" + mirna_pk + ", mature_pk="
				+ mature_pk + "]";
	} 

}
