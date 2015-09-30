package mirna.beans;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import mirna.exception.ConflictException;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "mature")

public class Mature extends ModelClass {
	
	@Column(name = "proportion", nullable = true, length = 80, unique = true)
	protected String proportion;
	
	@Column(name = "mirna_pk", nullable = false)
	private Integer mirnaPk;
	
	@Column(name = "sequence_pk", nullable = false)
	private Integer sequence_pk;
	
	
	public Mature() { }

	
	public String getProportion() {
		return proportion;
	}

	public void setProportion(String proportion) {
		this.proportion = proportion;
	}


	public Integer getMirnaPk() {
		return mirnaPk;
	}


	public void setMirnaPk(Integer mirnaPk) {
		this.mirnaPk = mirnaPk;
	}


	public Integer getSequence_pk() {
		return sequence_pk;
	}


	public void setSequence_pk(Integer sequence_pk) {
		this.sequence_pk = sequence_pk;
	}

	public int checkConflict(Mature mature) {
		int res = 0;
		if (this.pk!=null) {
			if (mature.getPk()==null) res++;
			else if (!this.pk.equals(mature.getPk())) return -1;
		}
		if (this.proportion!=null) {
			if (mature.getProportion()==null) res++;
			else if (!this.proportion.equals(mature.getProportion())) {
				return -1;
			}
		}
		if (this.sequence_pk!=null) {
			if (mature.getSequence_pk()==null) res++;
			else if (!this.sequence_pk.equals(mature.getSequence_pk())) return -1;
		}
		if (this.mirnaPk!=null) {
			if (mature.getMirnaPk()==null) res++;
			else if (!this.mirnaPk.equals(mature.getMirnaPk())) return -1;
		}
		return res;
	}
	
	public void update(Mature mature) throws ConflictException {
		this.update(mature, true);
	}
	
	public void update(Mature mature, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(mature)==-1) throw new ConflictException(this, mature);
		}
		if (mature.getPk()!=null) this.pk = mature.getPk();
		if (mature.getProportion()!=null) this.proportion = mature.getProportion();
		if (mature.getSequence_pk()!=null) this.sequence_pk = mature.getSequence_pk();
		if (mature.getMirnaPk()!=null) this.mirnaPk = mature.getMirnaPk();
	}


	@Override
	public String toString() {
		return "Mature [proportion=" + proportion + ", mirnaPk=" + mirnaPk
				+ ", sequence_pk=" + sequence_pk + ", pk=" + pk + "]";
	}
		
}
