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
	
	@Column(name = "sequence", nullable = true, length = 200)
	protected String sequence;
	
	@Column(name = "mirna_pk", nullable = false)
	private Integer mirnaPk;
	
	public Hairpin() { }

	public Hairpin(int pk, String name, String sequence, int mirnaPk) {
		this.pk = pk;
		this.name = name;
		this.sequence = sequence;
		this.mirnaPk = mirnaPk;
	}
	
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
		if (this.sequence!=null) {
			if (hairpin.getSequence()==null) res++;
			else if (!this.sequence.equals(hairpin.getSequence())) return -1;
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
		if (hairpin.getSequence()!=null) this.sequence = hairpin.getSequence();
		if (hairpin.getMirnaPk()!=null) this.mirnaPk = hairpin.getMirnaPk();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public Integer getMirnaPk() {
		return mirnaPk;
	}

	public void setMirnaPk(Integer mirnaPk) {
		this.mirnaPk = mirnaPk;
	}

	@Override
	public String toString() {
		return "Hairpin [name=" + name + ", sequence=" + sequence
				+ ", mirnaPk=" + mirnaPk + ", pk=" + pk + "]";
	}

}
