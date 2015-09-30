package mirna.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import mirna.exception.ConflictException;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "hairpin")
public class Hairpin extends ModelClass {
	
	@Column(name = "name", nullable = true)
	private String name;
	
	/*@Column(name = "mirna_pk", nullable = false)
	private Integer mirnaPk;*/
	
	public Hairpin() { }
	
	public int checkConflict(Hairpin hairpin) {
		int res = 0;
		if (this.pk!=null) {
			if (hairpin.getPk()==null) res++;
			else if (!this.pk.equals(hairpin.getPk())) return -1;
		}
		if (this.name!=null) {
			if (hairpin.getName()==null) res++;
			else if (!this.name.equals(hairpin.getName())) return -1;
		}
		
		/*if (this.mirnaPk!=null) {
			if (hairpin.getMirnaPk()==null) res++;
			else if (!this.mirnaPk.equals(hairpin.getMirnaPk())) return -1;
		}*/
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
/*		if (hairpin.getMirnaPk()!=null) this.mirnaPk = hairpin.getMirnaPk();
*/		if (hairpin.getName()!=null) this.name = hairpin.getName();

	}
	

	/*public Integer getMirnaPk() {
		return mirnaPk;
	}

	public void setMirnaPk(Integer mirnaPk) {
		this.mirnaPk = mirnaPk;
	}
	*/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Hairpin [name=" + name + 
				", pk=" + pk + "]";
	}

	
	

}
