package mirna.integration.database.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "miRdSNP3_disease_pk", schema="mirna_raw")
public class DiseasePk {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="pk", nullable=false, unique=true, length=11)
	protected Integer pk;
	
	@Column(name = "row_pk")
	private Integer rowPk;
	
	@Column(name = "disease_pk")
	private Integer diseasePk;
	
	@Column(name = "disease")
	private String diseaseName;
	
	public DiseasePk() {}

	public Integer getRowPk() {
		return rowPk;
	}

	public void setRowPk(Integer rowPk) {
		this.rowPk = rowPk;
	}

	public Integer getDiseasePk() {
		return diseasePk;
	}

	public void setDiseasePk(Integer diseasePk) {
		this.diseasePk = diseasePk;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
}
