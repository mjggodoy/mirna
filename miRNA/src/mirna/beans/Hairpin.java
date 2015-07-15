package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Table(name = "hairpin")
public class Hairpin extends ModelClass {
	
	@Column(name = "name", nullable = false, length = 80, unique = true)
	protected String name;
	

	@Column(name = "mirna_pk", nullable = false)
	private Integer mirnaPk;
	
	@Column(name = "sequence_pk", nullable = false)
	private Integer sequence_pk;
	
	public Hairpin() { }

	
	public int checkConflict(Hairpin hairpin) {
		int res = 0;
		if (this.pk!=null) {
			if (hairpin.getPk()==null) res++;
			else if (!this.pk.equals(hairpin.getPk())) return -1;
		}
		if (this.name!=null) {
			if (hairpin.getName()==null) res++;
			else if (!this.name.equals(hairpin.getName())) {
				return -1;
			}
		}
		if (this.sequence_pk!=null) {
			if (hairpin.getSequence_pk()==null) res++;
			else if (!this.sequence_pk.equals(hairpin.getSequence_pk())) return -1;
		}
		if (this.mirnaPk!=null) {
			if (hairpin.getMirnaPk()==null) res++;
			else if (!this.mirnaPk.equals(hairpin.getMirnaPk())) return -1;
		}
		return res;
	}
	
	public void update(Hairpin hairpin) throws ConflictException {
		this.update(hairpin, true);
	}
	
	public void update(Hairpin hairpin, boolean checkConflict) throws ConflictException {
		if (checkConflict) {
			if (this.checkConflict(hairpin)==-1) throw new ConflictException(this, hairpin);
		}
		if (hairpin.getPk()!=null) this.pk = hairpin.getPk();
		if (hairpin.getName()!=null) this.name = hairpin.getName();
		if (hairpin.getSequence_pk()!=null) this.sequence_pk = hairpin.getSequence_pk();
		if (hairpin.getMirnaPk()!=null) this.mirnaPk = hairpin.getMirnaPk();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSequence_pk() {
		return sequence_pk;
	}


	public void setSequence_pk(Integer sequence_pk) {
		this.sequence_pk = sequence_pk;
	}


	public Integer getMirnaPk() {
		return mirnaPk;
	}

	public void setMirnaPk(Integer mirnaPk) {
		this.mirnaPk = mirnaPk;
	}

	@Override
	public String toString() {
		return "Hairpin [name=" + name + ", sequence=" + sequence_pk
				+ ", mirnaPk=" + mirnaPk + ", pk=" + pk + "]";
	}

}
